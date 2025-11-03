package faceless.artent.potions.item;

import faceless.artent.potions.api.IDebuggableItem;
import faceless.artent.potions.api.IPotionContainerItem;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import faceless.artent.potions.brewingApi.PotionDataUtil;
import faceless.artent.potions.registry.AlchemicalPotionRegistry;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.consume.UseAction;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;
import java.util.stream.Stream;

public class PotionBottleItem extends Item implements IPotionContainerItem, IDebuggableItem {
  private static final Text NONE_TEXT = Text.translatable("effect.none").formatted(Formatting.GRAY);
  public final String type;
  public final int size;

  public PotionBottleItem(Settings settings, String type, int size) {
    super(settings);
    this.type = type;
    this.size = size;
  }

  @Override
  public List<AlchemicalPotion> getPotions(ItemStack stack) {
    var keys = PotionDataUtil.getPotionsKeys(stack);
    if(keys == null)
      keys = List.of();
    return keys.stream().map(AlchemicalPotionRegistry::getPotion).toList();
  }

  @Override
  public void setPotions(ItemStack stack, List<AlchemicalPotion> potions) {
    PotionDataUtil.setPotionKeys(stack, potions.stream().map(potion -> potion.id).toList());
  }

  @Override
  public int getMaxPotionAmount(ItemStack stack) {
    return this.size;
  }

  @Override
  public int getPotionAmount(ItemStack stack) {
    return PotionDataUtil.getConcentrateAmount(stack);
  }

  @Override
  public void setPotionAmount(ItemStack stack, int amount) {
    PotionDataUtil.setConcentrateAmount(stack, amount);
  }

  protected String getPhialBaseNameTranslationId() {
    return "artent.item.potion_bottle." + this.type;
  }

  @Override
  public Text getName(ItemStack stack) {
    var effects = AlchemicalPotionUtil.getPotionEffects(stack);
    var translationId = getPhialBaseNameTranslationId();

    if (effects.isEmpty()) return Text.translatable(translationId + ".empty");
    if (!hasPotion(stack))
      return Text.translatable(translationId).append(Text.translatable("text.artent_potions.potion.unidentified"));

    var text = Text.translatable(translationId);
    effects
        .stream()
        .map(effect -> Text.translatable(effect.getTranslationKey()))
        .flatMap(t -> Stream.of(Text.literal(", "), t))
        .skip(1)
        .forEach(text::append);
    return text;
  }

  @Override
  public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
    if (!hasPotion(stack)) {
      var concentrateAmount = PotionDataUtil.getConcentrateAmount(stack);
      if (concentrateAmount > 0) {
        tooltip.add(Text.translatable("text.artent_potions.potion.unidentified"));
      }
      return;
    }
    if (stack.getItem() instanceof IPotionContainerItem potionContainer) {
      var maxAmount = potionContainer.getMaxPotionAmount(stack);
      if (maxAmount != 1) {
        tooltip.add(Text.literal(potionContainer.getPotionAmount(stack)
                                 + "/"
                                 + maxAmount));
      }
    }

    float tickRate = context.getUpdateTickRate();
    if (!hasPotion(stack)) {
      tooltip.add(Text.translatable("text.artent_potions.potion.unidentified"));
      return;
    }
    List<StatusEffectInstance> effects = AlchemicalPotionUtil.getPotionEffects(stack);
    if (effects.isEmpty()) {
      tooltip.add(NONE_TEXT);
    } else {
      PotionContentsComponent.buildTooltip(effects, tooltip::add, 1.0f, tickRate);
    }
  }

  @Override
  public int getMaxUseTime(ItemStack stack, LivingEntity user) {
    return 32;
  }

  @Override
  public UseAction getUseAction(ItemStack stack) {
    if (hasPotion(stack)) return UseAction.DRINK;
    return UseAction.NONE;
  }

  @Override
  public ActionResult use(World world, PlayerEntity user, Hand hand) {
    var stack = user.getStackInHand(hand);
    if (hasPotion(stack)) return ItemUsage.consumeHeldItem(world, user, hand);
    return ActionResult.PASS;
  }

  @Override
  public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
    PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity) user : null;
    if (playerEntity == null) return stack;

    if (playerEntity instanceof ServerPlayerEntity serverPlayer) {
      Criteria.CONSUME_ITEM.trigger(serverPlayer, stack);
    }
    if (!world.isClient && world instanceof ServerWorld serverWorld) {
      if (hasPotion(stack)) {
        AlchemicalPotionUtil.applyPotionEffects(
            serverWorld,
            playerEntity,
            playerEntity,
            AlchemicalPotionUtil.getPotionEffects(stack));
      }
    }
    world.emitGameEvent(playerEntity, GameEvent.DRINK, playerEntity.getPos());
    playerEntity.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
    if (!playerEntity.getAbilities().creativeMode) {
      var item = stack.getItem();
      if (item.getMaxCount() == 1 || stack.getCount() == 1) {
        var amount = getPotionAmount(stack);
        if (amount != 0) {
          if (amount - 1 == 0) {
            overridePotion(stack, List.of(), 0);
          } else {
            setPotionAmount(stack, amount - 1);
          }
        }
      } else {
        var newStack = stack.copyWithCount(1);
        stack.decrement(1);
        var amount = getPotionAmount(newStack);
        if (amount != 0) {
          if (amount - 1 == 0) {
            overridePotion(newStack, List.of(), 0);
          } else {
            setPotionAmount(newStack, amount - 1);
          }
        }
        playerEntity.giveOrDropStack(newStack);
      }
    }
    return stack;
  }

  @Override
  public void fillDebugInfo(ItemStack stack, List<String> debugInfo) {
    if (hasPotion(stack)) {
      debugInfo.add("Potions: " + String.join(", ", getPotions(stack).stream().map((i) -> i.id).toList()));
    } else {
      debugInfo.add("No potions");
    }
    debugInfo.add("Potion amount: " + getPotionAmount(stack) + "/" + getMaxPotionAmount(stack));
  }
}