package faceless.artent.potions.brewingApi;

import faceless.artent.core.item.group.ArtentItemGroupBuilder;
import faceless.artent.core.math.Color;
import faceless.artent.potions.api.IConcentrateContainerItem;
import faceless.artent.potions.registry.AlchemicalPotionRegistry;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.ArrayList;
import java.util.List;

import static faceless.artent.potions.registry.DataComponentRegistry.CONCENTRATE_AMOUNT;
import static faceless.artent.potions.registry.DataComponentRegistry.POTION_KEY;

public class AlchemicalPotionUtil {
  private static final Text NONE_TEXT = Text.translatable("effect.none").formatted(Formatting.GRAY);

  public static void buildTooltip(ItemStack stack, List<Text> list, float durationMultiplier, float tickRate) {
    var potion = getPotion(stack);
    if (potion == null) {
      list.add(Text.translatable("text.artent_potions.potion.unidentified"));
      return;
    }
    List<StatusEffectInstance> effects = potion.getEffects();
    if (effects.isEmpty()) {
      list.add(NONE_TEXT);
    } else {
      PotionContentsComponent.buildTooltip(effects, list::add, durationMultiplier, tickRate);
    }
  }

  public static AlchemicalPotion getPotion(ItemStack stack) {
    var key = stack.get(POTION_KEY);
    if (key == null) return null;
    return AlchemicalPotionRegistry.getRegisteredPotions().getOrDefault(key, null);
  }

  public static String getPotionKey(ItemStack stack) {
    var potion = getPotion(stack);
    return potion == null ? "unidentified" : potion.id;
  }

  public static AlchemicalPotion getFermentedPotion(ItemStack stack) {
    var key = stack.get(POTION_KEY);
    if (key == null) return null;
    return AlchemicalPotionRegistry.getFermentedPotions().getOrDefault(key, null);
  }

  public static int getColor(ItemStack itemStack) {
    var potion = getPotion(itemStack);
    return potion != null ? potion.color.addNoMixing(new Color(40, 40, 40)).toHex() : 0;
  }

  public static List<StatusEffectInstance> getPotionEffects(ItemStack itemStack) {
    var potion = getPotion(itemStack);
    if (potion == null) return new ArrayList<>();
    return potion.getEffects();
  }

  public static void setPotion(ItemStack stack, AlchemicalPotion potion) {
    stack.set(POTION_KEY, potion.id);
  }

  public static void setConcentrateAmount(ItemStack stack, int amount) {
    stack.set(CONCENTRATE_AMOUNT, amount);
  }

  public static int getConcentrateAmount(ItemStack stack) {
    var integer = stack.get(CONCENTRATE_AMOUNT);
    return integer == null ? 0 : integer;
  }

  public static ItemStack setFermentedPotionByKey(ItemStack stack, String potionKey, int amount) {
    setFermentedPotionByKey(stack, potionKey);
    setConcentrateAmount(stack, amount);
    return stack;
  }

  public static ItemStack setFermentedPotionByKey(ItemStack stack, String potionKey) {
    stack.set(POTION_KEY, potionKey);
    return stack;
  }

  public static void createEffectTooltip(ItemStack stack, List<Text> tooltip) {
    var potion = AlchemicalPotionUtil.getPotion(stack);
    if (potion != null) {
      potion
          .getEffects()
          .stream()
          .map(sei -> Text
              .translatable(sei.getTranslationKey())
              .formatted(sei.getEffectType().value().getCategory().getFormatting()))
          .forEach(tooltip::add);
    } else tooltip.add(Text.translatable("text.artent_potions.potion.unidentified"));
  }

  public static void createFermentedEffectTooltip(ItemStack stack, List<Text> tooltip) {
    var potion = AlchemicalPotionUtil.getFermentedPotion(stack);
    if (potion != null) {
      potion
          .getEffects()
          .stream()
          .map(sei -> Text
              .translatable(sei.getTranslationKey())
              .formatted(sei.getEffectType().value().getCategory().getFormatting()))
          .forEach(tooltip::add);
    } else {
      var concentrateAmount = AlchemicalPotionUtil.getConcentrateAmount(stack);
      if (concentrateAmount > 0) {
        tooltip.add(Text.translatable("text.artent_potions.potion.unidentified"));
      }
    }
  }

  public static void applyPotionEffects(
      ServerWorld serverWorld, LivingEntity user, PlayerEntity playerEntity, AlchemicalPotion potion) {
    List<StatusEffectInstance> list = potion.getEffects();
    for (StatusEffectInstance statusEffectInstance : list) {
      if (statusEffectInstance.getEffectType().value().isInstant()) {
        statusEffectInstance
            .getEffectType()
            .value()
            .applyInstantEffect(
                serverWorld,
                playerEntity,
                playerEntity,
                user,
                statusEffectInstance.getAmplifier(),
                1.0);
        continue;
      }
      user.addStatusEffect(new StatusEffectInstance(statusEffectInstance));
    }
  }

  public static ItemStack drinkFermentedPotion(ItemStack stack, World world, PlayerEntity playerEntity) {
    if (playerEntity instanceof ServerPlayerEntity serverPlayer) {
      Criteria.CONSUME_ITEM.trigger(serverPlayer, stack);
    }
    if (!world.isClient && world instanceof ServerWorld serverWorld) {
      var potion = AlchemicalPotionUtil.getFermentedPotion(stack);
      if (potion != null) AlchemicalPotionUtil.applyPotionEffects(serverWorld, playerEntity, playerEntity, potion);
    }
    world.emitGameEvent(playerEntity, GameEvent.DRINK, playerEntity.getPos());
    playerEntity.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
    if (!playerEntity.getAbilities().creativeMode) {
      var item = stack.getItem();
      if (item.getMaxCount() == 1 || stack.getCount() == 1) {
        if (item instanceof IConcentrateContainerItem container) {
          var amount = container.getConcentrateAmount(stack);
          if (amount != 0) {
            if (amount - 1 == 0) {
              container.setConcentrateAmount(stack, null, 0);
            } else {
              container.setConcentrateAmount(stack, amount - 1);
            }
          }
        }
      } else if (item instanceof IConcentrateContainerItem container) {
        var newStack = stack.copyWithCount(1);
        stack.decrement(1);
        var amount = container.getConcentrateAmount(newStack);
        if (amount != 0) {
          if (amount - 1 == 0) {
            container.setConcentrateAmount(newStack, null, 0);
          } else {
            container.setConcentrateAmount(newStack, amount - 1);
          }
        }
        playerEntity.giveOrDropStack(newStack);
      }
    }
    return stack;
  }

  public static void appendFermentedPotionStacks(Item base, int size, ArtentItemGroupBuilder group) {
    List<ItemStack> stacks = new ArrayList<>();

    var phialStack = new ItemStack(base);
    AlchemicalPotionUtil.setConcentrateAmount(phialStack, 0);
    stacks.add(phialStack);

    for (var key : AlchemicalPotionRegistry.getRegisteredPotions().keySet()) {
      if (AlchemicalPotionRegistry.fermentedPotionIsRegistered(key)) {
        var stack = new ItemStack(base);
        stack.set(POTION_KEY, key);
        stack.set(CONCENTRATE_AMOUNT, size);
        stacks.add(stack);
      }
    }
    group.Items.addAll(stacks);
  }

  public static void appendPotionStacks(Item base, ArtentItemGroupBuilder group) {
    List<ItemStack> stacks = new ArrayList<>();
    for (var key : AlchemicalPotionRegistry.getRegisteredPotions().keySet()) {
      var stack = new ItemStack(base);
      stack.set(POTION_KEY, key);
      stacks.add(stack);
    }
    group.Items.addAll(stacks);
  }
}