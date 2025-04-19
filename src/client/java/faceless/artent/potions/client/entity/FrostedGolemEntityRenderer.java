package faceless.artent.potions.client.entity;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.entity.FrostedGolem;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FrostedGolemEntityRenderer extends GeoEntityRenderer<FrostedGolem> {
  public FrostedGolemEntityRenderer(
      EntityRendererFactory.Context context) {
    super(context, new DefaultedEntityGeoModel<>(Identifier.of(ArtentPotions.MODID, "frosted_golem")));
  }
}
