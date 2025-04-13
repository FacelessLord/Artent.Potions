package faceless.artent.potions.client.particles;

import faceless.artent.potions.objects.ModParticles;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class BubbleParticle extends SpriteBillboardParticle {
  BubbleParticle(
      ClientWorld clientWorld,
      double x,
      double y,
      double z,
      double velX,
      double velY,
      double velZ) {
    super(clientWorld, x, y, z);
    this.gravityStrength = 0.125F;
    this.velocityMultiplier = 0.85F;
    this.setBoundingBoxSpacing(0.02F, 0.02F);
    this.scale *= this.random.nextFloat() * 0.3F + 0.2F;
    this.velocityX = velX;
    this.velocityY = velY;
    this.velocityZ = velZ;
    this.maxAge = (int) ((double) 20.0F / (Math.random() * 0.8 + 0.2));
  }

  public void tick() {
    super.tick();
  }

  public ParticleTextureSheet getType() {
    return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
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
      var bubbleParticle = new BubbleParticle(world, x, y, z, velocityX, velocityY + 0.01, velocityZ);
      bubbleParticle.setSprite(this.spriteProvider);
      return bubbleParticle;
    }
  }

}
