package faceless.artent.potions.mixin;

import faceless.artent.potions.registry.StatusEffectsRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

  @Inject(method = "getEquipmentLevel", at = @At("TAIL"), cancellable = true)
  private static void getEquipmentLevel(
      RegistryEntry<Enchantment> enchantment, LivingEntity living, CallbackInfoReturnable<Integer> cir) {
    var keyOptional = enchantment.getKey();
    if (keyOptional.isEmpty()) return;
    var key = keyOptional.orElseThrow();
    if (key == Enchantments.FORTUNE || key == Enchantments.LOOTING) {
      var luckEffect = living.getStatusEffect(StatusEffectsRegistry.FORTUNE);
      if (luckEffect == null) return;
      var luckLevel = luckEffect.getAmplifier() + 1000;
      cir.setReturnValue(cir.getReturnValueI() + luckLevel);
    }
  }
//  @Inject(method = "getLevel", at = @At("TAIL"), cancellable = true)
//  private static void getLevel(
//      RegistryEntry<Enchantment> enchantment, LivingEntity living, CallbackInfoReturnable<Integer> cir) {
//    var keyOptional = enchantment.getKey();
//    if (keyOptional.isEmpty()) return;
//    var key = keyOptional.orElseThrow();
//    if (key == Enchantments.FORTUNE || key == Enchantments.LOOTING) {
//      var luckEffect = living.getStatusEffect(StatusEffectsRegistry.FORTUNE);
//      if (luckEffect == null) return;
//      var luckLevel = luckEffect.getAmplifier() + 1000;
//      cir.setReturnValue(cir.getReturnValueI() + luckLevel);
//    }
//  }
}
