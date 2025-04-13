package faceless.artent.potions.objects;

import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.ArtentPotions;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles implements IRegistry {
  public static final SimpleParticleType BUBBLE = FabricParticleTypes.simple();
  public static final SimpleParticleType SPLASH = FabricParticleTypes.simple();


  @Override
  public void register() {
    Registry.register(Registries.PARTICLE_TYPE, Identifier.of(ArtentPotions.MODID, "bubble"), BUBBLE);
    Registry.register(Registries.PARTICLE_TYPE, Identifier.of(ArtentPotions.MODID, "splash"), SPLASH);
  }
}
