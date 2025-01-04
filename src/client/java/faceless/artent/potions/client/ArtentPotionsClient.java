package faceless.artent.potions.client;

import faceless.artent.potions.client.network.ArtentClientHook;
import faceless.artent.potions.client.registry.BlockEntityRenderersRegistry;
import faceless.artent.potions.client.registry.BlockRenderLayerMapRegistry;
import faceless.artent.potions.client.registry.ColorProvidersRegistry;
import faceless.artent.potions.client.registry.EntityRenderersRegistry;
import faceless.artent.potions.objects.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;

public class ArtentPotionsClient implements ClientModInitializer {
    public EntityRenderersRegistry EntityRenderers = new EntityRenderersRegistry();
    public BlockEntityRenderersRegistry BlockEntityRenderers = new BlockEntityRenderersRegistry();
    public BlockRenderLayerMapRegistry BlockRenderLayerMaps = new BlockRenderLayerMapRegistry();
    public ColorProvidersRegistry ColorProviders = new ColorProvidersRegistry();
    public ArtentClientHook ClientHook = new ArtentClientHook();

    @Override
    public void onInitializeClient() {
        EntityRenderers.register();
        BlockEntityRenderers.register();
        BlockRenderLayerMaps.register();
        ColorProviders.register();
        ClientHook.loadClient();
//
//        ModelPredicateProviderRegistry.register(ModItems.MediumConcentrate, Identifier.of("amount"),
//                                                (stack, world, entity, seed) -> stack
//                                                                                    .getOrCreateNbt()
//                                                                                    .getInt("amount") / 4.0f);
//        ModelPredicateProviderRegistry.register(ModItems.BigConcentrate, Identifier.of("amount"),
//                                                (stack, world, entity, seed) -> stack.get() / 10.0f);

    }
}
