package faceless.artent.potions.client.datagen;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.objects.ModBlocks;
import faceless.artent.potions.registry.DamageSourceRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.DamageTypeTags;

import java.util.concurrent.CompletableFuture;

public class DamageTagsProvider extends FabricTagProvider<DamageType> {
  public DamageTagsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup registries) {
    getOrCreateTagBuilder(DamageTypeTags.NO_KNOCKBACK)
        .add(DamageSourceRegistry.BleedingDamageKey);
  }

  @Override
  public String getName() {
    return ArtentPotions.MODID + "_damage_type_tags";
  }
}
