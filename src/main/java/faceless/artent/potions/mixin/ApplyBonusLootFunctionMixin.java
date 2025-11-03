package faceless.artent.potions.mixin;

import faceless.artent.potions.registry.StatusEffectsRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.context.ContextParameter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.Set;

@Mixin(ApplyBonusLootFunction.class)
public class ApplyBonusLootFunctionMixin {

  @Inject(method = "getAllowedParameters", at = @At("TAIL"), cancellable = true)
  public void getAllowedParameters(CallbackInfoReturnable<Set<ContextParameter<?>>> cir) {
    var returnValue = cir.getReturnValue();
    var result = new HashSet<>(returnValue);
    result.add(LootContextParameters.THIS_ENTITY);
    cir.setReturnValue(result);
  }

  @Redirect(method = "process", at = @At(value = "INVOKE", target = "net/minecraft/enchantment/EnchantmentHelper.getLevel (Lnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/item/ItemStack;)I"))
  public int process(
      RegistryEntry<Enchantment> enchantment,
      ItemStack stack,
      ItemStack stackSrc,
      LootContext context) {
    var entity = context.get(LootContextParameters.THIS_ENTITY);

    var keyOptional = enchantment.getKey();
    if (keyOptional.isEmpty() || !(entity instanceof LivingEntity living))
      return EnchantmentHelper.getLevel(enchantment, stack);
    var key = keyOptional.orElseThrow();

    if (key == Enchantments.FORTUNE || key == Enchantments.LOOTING) {
      var luckEffect = living.getStatusEffect(StatusEffectsRegistry.FORTUNE);
      if (luckEffect == null) return EnchantmentHelper.getLevel(enchantment, stack);
      var luckLevel = luckEffect.getAmplifier() + 1000;
      return EnchantmentHelper.getLevel(enchantment, stack) + luckLevel;
    }
    return EnchantmentHelper.getLevel(enchantment, stack);
  }
}
