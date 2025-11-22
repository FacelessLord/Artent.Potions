package faceless.artent.potions.brewingApi;

import faceless.artent.core.item.group.ArtentItemGroupBuilder;
import faceless.artent.core.math.Color;
import faceless.artent.potions.api.IPotionContainerItem;
import faceless.artent.potions.api.ListUtils;
import faceless.artent.potions.objects.ModItems;
import faceless.artent.potions.objects.ModRegistries;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

public class AlchemicalPotionUtil {
  public static Hashtable<String, AlchemicalPotion> PotionsMap = new Hashtable<>();

  public static boolean comparePotions(List<AlchemicalPotion> a, List<AlchemicalPotion> b) {
    if (a.size() != b.size())
      return false;
    for (int i = 0; i < a.size(); i++) {
      if (!Objects.equals(a.get(i), b.get(i)))
        return false;
    }
    return true;
  }

  public static List<AlchemicalPotion> getPotions(ItemStack stack) {
    var item = stack.getItem();
    if (item instanceof IPotionContainerItem potionContainer)
      return potionContainer.getPotions(stack);
    return List.of();
  }

  public static void setPotion(ItemStack stack, AlchemicalPotion potion) {
    PotionDataUtil.setPotionKeys(stack, List.of(potion.getId()));
  }

  public static void setPotions(ItemStack stack, List<AlchemicalPotion> potions) {
    PotionDataUtil.setPotionKeys(stack, potions.stream().map(AlchemicalPotion::getId).toList());
  }

  public static int getColor(ItemStack itemStack) {
    var potions = getPotions(itemStack);
    if (ListUtils.isNullOrEmpty(potions)) return 0;

    return potions
        .stream()
        .map(potion -> potion.color.addNoMixing(new Color(40, 40, 40)))
        .reduce(Color::add)
        .map(Color::toHex)
        .orElse(0);
  }

  public static List<StatusEffectInstance> getPotionEffects(ItemStack itemStack) {
    var potions = getPotions(itemStack);
    if (ListUtils.isNullOrEmpty(potions)) return new ArrayList<>();
    return potions.stream().filter(Objects::nonNull).flatMap(potion -> potion.getEffects().stream()).toList();
  }

  public static void applyPotionEffects(
      ServerWorld serverWorld, LivingEntity user, PlayerEntity playerEntity, List<StatusEffectInstance> effects) {
    for (StatusEffectInstance statusEffectInstance : effects) {
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

  public static void appendPotionStacks(ArtentItemGroupBuilder group) {
    group.setCustomEntryCollector((ctx, entries) -> {
      AlchemicalPotionUtil.appendPotionStacks(ModItems.SMALL_BOTTLE, 1, ctx, entries);
      AlchemicalPotionUtil.appendPotionStacks(ModItems.MEDIUM_BOTTLE, 3, ctx, entries);
      AlchemicalPotionUtil.appendPotionStacks(ModItems.BIG_BOTTLE, 9, ctx, entries);
      AlchemicalPotionUtil.appendPotionStacks(ModItems.SMALL_BOTTLE_EXPLOSIVE, 1, ctx, entries);
    });
  }

  public static void appendPotionStacks(Item base, int size, ItemGroup.DisplayContext ctx, ItemGroup.Entries entries) {
    var registry = ctx.lookup().getOrThrow(ModRegistries.POTION_EFFECTS_REGISTRY_KEY);

    var phialStack = new ItemStack(base);
    PotionDataUtil.setConcentrateAmount(phialStack, 0);
    entries.add(phialStack);

    registry.streamKeys().forEach(key -> {
      var stack = new ItemStack(base);
      PotionDataUtil.setPotionKeys(stack, List.of(key.toString()));
      PotionDataUtil.setConcentrateAmount(stack, size);
      entries.add(stack);
    });
//    { // multipotion
//      var stack = new ItemStack(base);
//      PotionDataUtil.setPotionKeys(stack, AlchemicalPotionRegistry.getRegisteredPotions());
//      PotionDataUtil.setConcentrateAmount(stack, size);
//      stacks.add(stack);
//    }
  }

  public static Text getPotionNames(List<StatusEffectInstance> effects) {
    if (effects.isEmpty()) return Text.translatable("text.artent_potions.potion.unidentified");
    var baseText = Text.empty();
    effects
        .stream()
        .map(effect -> Text.translatable(effect.getTranslationKey()))
        .flatMap(t -> Stream.of(Text.literal(", "), t))
        .skip(1)
        .forEach(baseText::append);
    return baseText;
  }

  public static @NotNull Optional<Integer> getPotionListColor(List<AlchemicalPotion> potions) {
    return potions
        .stream()
        .map((potion) -> potion.color)
        .reduce(Color::add)
        .map(Color::asInt)
        .map(ColorHelper::fullAlpha);
  }
}