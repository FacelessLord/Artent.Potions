package faceless.artent.potions.client.registry;

import faceless.artent.potions.client.particles.BubbleParticle;
import faceless.artent.potions.client.particles.SnowStormParticle;
import faceless.artent.potions.client.particles.SplashParticle;
import faceless.artent.potions.objects.ModParticles;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class ClientModParticles {

  public void register() {
    ParticleFactoryRegistry.getInstance().register(ModParticles.BUBBLE, BubbleParticle.Factory::new);
    ParticleFactoryRegistry.getInstance().register(ModParticles.SPLASH, SplashParticle.Factory::new);
    ParticleFactoryRegistry.getInstance().register(ModParticles.SNOW_STORM, SnowStormParticle.Factory::new);
  }
}
