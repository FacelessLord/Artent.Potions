package faceless.artent.potions.client.datagen;

import faceless.artent.potions.objects.ModBlocks;
import faceless.artent.potions.objects.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class CraftingRecipeProvider extends FabricRecipeProvider {
  public CraftingRecipeProvider(
      FabricDataOutput output,
      CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected RecipeGenerator getRecipeGenerator(
      RegistryWrapper.WrapperLookup wrapperLookup,
      RecipeExporter recipeExporter) {
    return new RecipeGenerator(wrapperLookup, recipeExporter) {
      @Override
      public void generate() {
        RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);

        createShaped(RecipeCategory.BREWING, ModBlocks.BREWING_CAULDRON.item())
            .pattern("i i")
            .pattern("iii")
            .pattern("n n")
            .input('i', Items.IRON_INGOT)
            .input('n', Items.IRON_NUGGET)
            .group("artent:cauldron")
            .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModBlocks.BREWING_CAULDRON_COPPER.item())
            .pattern("c c")
            .pattern("ccc")
            .pattern("c c")
            .input('c', Items.COPPER_INGOT)
            .group("artent:cauldron")
            .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModBlocks.FEMENTING_BARREL.item())
            .pattern("pp")
            .pattern("pp")
            .pattern("ss")
            .input('p', ItemTags.PLANKS)
            .input('s', Items.STICK)
            .criterion(hasItem(ModItems.SMALL_BOTTLE), conditionsFromItem(ModItems.SMALL_BOTTLE))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModItems.SMALL_BOTTLE, 3)
            .pattern("c")
            .pattern("g")
            .input('g', Items.GLASS)
            .input('c', Items.CLAY_BALL)
            .group("artent:concentrate_phial")
            .criterion(
                hasItem(ModBlocks.BREWING_CAULDRON.block()),
                conditionsFromItem(ModBlocks.BREWING_CAULDRON.block()))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModItems.MEDIUM_BOTTLE, 1)
            .pattern(" c ")
            .pattern("g g")
            .pattern(" g ")
            .input('g', Items.GLASS)
            .input('c', Items.CLAY_BALL)
            .group("artent:concentrate_phial")
            .criterion(
                hasItem(ModBlocks.BREWING_CAULDRON.block()),
                conditionsFromItem(ModBlocks.BREWING_CAULDRON.block()))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModItems.BIG_BOTTLE, 1)
            .pattern("ggg")
            .pattern("gcg")
            .pattern("ggg")
            .input('g', Items.GLASS)
            .input('c', Items.CLAY_BALL)
            .group("artent:concentrate_phial")
            .criterion(
                hasItem(ModBlocks.BREWING_CAULDRON.block()),
                conditionsFromItem(ModBlocks.BREWING_CAULDRON.block()))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModItems.SMALL_BOTTLE_EXPLOSIVE, 3)
            .pattern("c")
            .pattern("p")
            .pattern("g")
            .input('c', Items.CLAY_BALL)
            .input('p', Items.GUNPOWDER)
            .input('g', Items.GLASS)
            .group("artent:concentrate_phial_explosive")
            .criterion(
                hasItem(ModBlocks.BREWING_CAULDRON.block()),
                conditionsFromItem(ModBlocks.BREWING_CAULDRON.block()))
            .offerTo(recipeExporter);
        createShapeless(RecipeCategory.BREWING, ModItems.BROWN_MUSHROOM_SPORES, 1)
            .input(ModItems.DRIED_BROWN_MUSHROOM)
            .group("artent:brown_mushroom_spores")
            .criterion(
                hasItem(ModItems.DRIED_BROWN_MUSHROOM),
                conditionsFromItem(ModItems.DRIED_BROWN_MUSHROOM))
            .offerTo(recipeExporter);
        createShapeless(RecipeCategory.BREWING, ModItems.RED_MUSHROOM_SPORES, 1)
            .input(ModItems.DRIED_RED_MUSHROOM)
            .group("artent:red_mushroom_spores")
            .criterion(
                hasItem(ModItems.DRIED_RED_MUSHROOM),
                conditionsFromItem(ModItems.DRIED_RED_MUSHROOM))
            .offerTo(recipeExporter);
        createShapeless(RecipeCategory.BREWING, ModItems.SHROOM_SPORES, 1)
            .input(ModItems.DRIED_SHROOM)
            .group("artent:shroom_spores")
            .criterion(
                hasItem(ModItems.DRIED_SHROOM),
                conditionsFromItem(ModItems.DRIED_SHROOM))
            .offerTo(recipeExporter);
        createShapeless(RecipeCategory.BREWING, ModItems.FROST_PUMPKIN_SEEDS, 4)
            .input(ModBlocks.FROST_PUMPKIN.item())
            .group("artent:frost_pumpkin_seeds")
            .criterion(
                hasItem(ModBlocks.FROST_PUMPKIN.item()),
                conditionsFromItem(ModBlocks.FROST_PUMPKIN.item()))
            .offerTo(recipeExporter);
        createShapeless(RecipeCategory.BREWING, ModItems.BLAZING_MARIGOLD_SEEDS, 1)
            .input(ModBlocks.BLAZING_MARIGOLD.item())
            .group("artent:blazing_marigold_seeds")
            .criterion(
                hasItem(ModBlocks.BLAZING_MARIGOLD.item()),
                conditionsFromItem(ModBlocks.BLAZING_MARIGOLD.item()))
            .offerTo(recipeExporter);
        createShapeless(RecipeCategory.BREWING, ModItems.SHADOWVEIL_SEEDS, 1)
            .input(ModBlocks.SHADOWVEIL.item())
            .group("artent:shadowveil_seeds")
            .criterion(
                hasItem(ModBlocks.SHADOWVEIL.item()),
                conditionsFromItem(ModBlocks.SHADOWVEIL.item()))
            .offerTo(recipeExporter);
        createShapeless(RecipeCategory.BREWING, ModItems.SLIME_BERRY_SEEDS, 1)
            .input(ModBlocks.SLIME_BERRY.item())
            .group("artent:slime_berry_seeds")
            .criterion(
                hasItem(ModBlocks.SLIME_BERRY.item()),
                conditionsFromItem(ModBlocks.SLIME_BERRY.item()))
            .offerTo(recipeExporter);

        createShaped(RecipeCategory.BREWING, ModBlocks.DRYING_RACK.item(), 1)
            .pattern("ppp")
            .pattern("   ")
            .pattern("ppp")
            .input('p', Items.OAK_PLANKS)
            .group("artent:drying_rack")
            .criterion(
                hasItem(Items.OAK_PLANKS),
                conditionsFromItem(Items.OAK_PLANKS))
            .offerTo(recipeExporter);
        // CRIMSON TREE

        createShapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRIMSONWOOD_PLANKS.item(), 4)
            .input(ModBlocks.CRIMSONWOOD_LOG.item())
            .criterion(hasItem(ModBlocks.CRIMSONWOOD_LOG.item()), conditionsFromItem(ModBlocks.CRIMSONWOOD_LOG.item()))
            .offerTo(recipeExporter);
      }
    };
  }

  @Override
  public String getName() {
    return "ArtentPotionsRecipeProvider";
  }
}
