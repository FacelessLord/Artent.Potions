package faceless.artent.potions.client.datagen;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.registry.DamageSourceRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class DamageProvider extends FabricDynamicRegistryProvider {
  public DamageProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
    final RegistryWrapper.Impl<DamageType> damageTypeRegistry = registries.getOrThrow(RegistryKeys.DAMAGE_TYPE);

    entries.add(damageTypeRegistry, DamageSourceRegistry.BleedingDamageKey);
  }

  @Override
  public String getName() {
    return ArtentPotions.MODID + "_damage_types";
  }
}
