package faceless.artent.potions.client.entity;

import faceless.artent.potions.ArtentPotions;
import net.minecraft.client.render.entity.ChickenEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.ChickenEntityRenderState;
import net.minecraft.util.Identifier;

public class ManaChickenEntityRenderer extends ChickenEntityRenderer {
  private static final Identifier TEXTURE = Identifier.of(ArtentPotions.MODID, "textures/entity/mana_chicken.png");

  public ManaChickenEntityRenderer(EntityRendererFactory.Context context) {
    super(context);
  }
  public Identifier getTexture(ChickenEntityRenderState chickenEntityRenderState) {
    return TEXTURE;
  }

}
