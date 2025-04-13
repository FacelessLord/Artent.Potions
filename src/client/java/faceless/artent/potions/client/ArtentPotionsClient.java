package faceless.artent.potions.client;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.client.network.ArtentClientHook;
import faceless.artent.potions.client.properties.ConcentrateAmountProperty;
import faceless.artent.potions.client.registry.*;
import faceless.artent.potions.client.tint.ArtentPotionTintSource;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.item.property.numeric.NumericProperties;
import net.minecraft.client.render.item.tint.TintSourceTypes;
import net.minecraft.util.Identifier;

public class ArtentPotionsClient implements ClientModInitializer {
  public EntityRenderersRegistry EntityRenderers = new EntityRenderersRegistry();
  public BlockEntityRenderersRegistry BlockEntityRenderers = new BlockEntityRenderersRegistry();
  public BlockRenderLayerMapRegistry BlockRenderLayerMaps = new BlockRenderLayerMapRegistry();
  public ColorProvidersRegistry ColorProviders = new ColorProvidersRegistry();
  public ArtentClientHook ClientHook = new ArtentClientHook();
  public ClientModParticles Particles = new ClientModParticles();

  @Override
  public void onInitializeClient() {
    EntityRenderers.register();
    BlockEntityRenderers.register();
    BlockRenderLayerMaps.register();
    ColorProviders.register();
    ClientHook.loadClient();
    Particles.register();

    NumericProperties.ID_MAPPER.put(
        Identifier.of(ArtentPotions.MODID, "concentrate_amount"),
        ConcentrateAmountProperty.CODEC);
    TintSourceTypes.ID_MAPPER.put(Identifier.of(ArtentPotions.MODID, "potion_color"), ArtentPotionTintSource.CODEC);
  }
}
