package faceless.artent.potions.client.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class SnowStormParticle extends SpriteBillboardParticle {
  SnowStormParticle(
      ClientWorld clientWorld,
      double x,
      double y,
      double z,
      double velX,
      double velY,
      double velZ) {
    super(clientWorld, x, y, z);
    this.scale(6.0F);
    this.setBoundingBoxSpacing(0.25F, 0.25F);
    this.maxAge = this.random.nextInt(50) + 80;

    this.gravityStrength = 0;
    this.velocityX = velX;
    this.velocityY = velY;
    this.velocityZ = velZ;
    this.maxAge = (int) ((double) 20.0F / (Math.random() * 0.8 + 0.2));
  }

  public void tick() {
    super.tick();
  }

  public ParticleTextureSheet getType() {
    return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
  }

  public static class Factory implements ParticleFactory<SimpleParticleType> {
    private final SpriteProvider spriteProvider;

    public Factory(SpriteProvider spriteProvider) {
      this.spriteProvider = spriteProvider;
    }

    @Override
    public @Nullable Particle createParticle(
        SimpleParticleType parameters,
        ClientWorld world,
        double x,
        double y,
        double z,
        double velocityX,
        double velocityY,
        double velocityZ) {
      var splashParticle = new SnowStormParticle(world, x, y, z, velocityX, velocityY + 0.1, velocityZ);
      splashParticle.setAlpha(0.9F);
      splashParticle.setSprite(this.spriteProvider);
      return splashParticle;
    }
  }

}
