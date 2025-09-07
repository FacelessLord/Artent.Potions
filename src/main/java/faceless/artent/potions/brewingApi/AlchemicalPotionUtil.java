package faceless.artent.potions.brewingApi;

import faceless.artent.core.item.group.ArtentItemGroupBuilder;
import faceless.artent.core.math.Color;
import faceless.artent.potions.api.IPotionContainerItem;
import faceless.artent.potions.api.ListUtils;
import faceless.artent.potions.registry.AlchemicalPotionRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class AlchemicalPotionUtil {

  public static boolean comparePotions(List<AlchemicalPotion> a, List<AlchemicalPotion> b) {
    if (a.size() != b.size())
      return false;
    for (int i = 0; i < a.size(); i++) {
      if (!Objects.equals(a.get(i).id, b.get(i).id))
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
    PotionDataUtil.setPotionKeys(stack, List.of(potion.id));
  }

  public static void setPotions(ItemStack stack, List<AlchemicalPotion> potions) {
    PotionDataUtil.setPotionKeys(stack, potions.stream().map(potion -> potion.id).toList());
  }

  public static boolean hasPotion(ItemStack stack) {
    var item = stack.getItem();
    if (!(item instanceof IPotionContainerItem potionContainer))
      return false;
    return potionContainer.hasPotion(stack);
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

  public static void createEffectListTooltip(ItemStack stack, List<Text> tooltip) {
    if (!hasPotion(stack)) {
      tooltip.add(Text.translatable("text.artent_potions.potion.unidentified"));
      return;
    }

    getPotionEffects(stack)
        .stream()
        .map(sei -> Text
            .translatable(sei.getTranslationKey())
            .formatted(sei.getEffectType().value().getCategory().getFormatting()))
        .forEach(tooltip::add);
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

  public static void appendPotionStacks(Item base, int size, ArtentItemGroupBuilder group) {
    List<ItemStack> stacks = new ArrayList<>();

    var phialStack = new ItemStack(base);
    PotionDataUtil.setConcentrateAmount(phialStack, 0);
    stacks.add(phialStack);

    for (var key : AlchemicalPotionRegistry.getRegisteredPotions()) {
      var stack = new ItemStack(base);
      PotionDataUtil.setPotionKeys(stack, List.of(key));
      PotionDataUtil.setConcentrateAmount(stack, size);
      stacks.add(stack);
    }
    { // multipotion
      var stack = new ItemStack(base);
      PotionDataUtil.setPotionKeys(stack, AlchemicalPotionRegistry.getRegisteredPotions());
      PotionDataUtil.setConcentrateAmount(stack, size);
      stacks.add(stack);
    }
    group.Items.addAll(stacks);
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