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

public class ArtentPotionsRecipeProvider extends FabricRecipeProvider {
  public ArtentPotionsRecipeProvider(
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

        createShaped(RecipeCategory.BREWING, ModBlocks.BrewingCauldronItem)
            .pattern("i i")
            .pattern("iii")
            .pattern("n n")
            .input('i', Items.IRON_INGOT)
            .input('n', Items.IRON_NUGGET)
            .group("artent:cauldron")
            .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModBlocks.BrewingCauldronCopperItem)
            .pattern("c c")
            .pattern("ccc")
            .pattern("c c")
            .input('c', Items.COPPER_INGOT)
            .group("artent:cauldron")
            .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModBlocks.FermentingBarrelItem)
            .pattern("pp")
            .pattern("pp")
            .pattern("ss")
            .input('p', ItemTags.PLANKS)
            .input('s', Items.STICK)
            .criterion(hasItem(ModItems.PotionPhial), conditionsFromItem(ModItems.PotionPhial))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModItems.EmptyPhial, 3)
            .pattern("gcg")
            .pattern("g g")
            .pattern(" g ")
            .input('g', Items.GLASS_PANE)
            .input('c', Items.CLAY_BALL)
            .group("artent:phial")
            .criterion(hasItem(Items.GLASS), conditionsFromItem(Items.GLASS))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModItems.EmptyPhialExplosive, 3)
            .pattern(" gc")
            .pattern("gpg")
            .pattern(" g ")
            .input('g', Items.GLASS_PANE)
            .input('c', Items.CLAY_BALL)
            .input('p', Items.GUNPOWDER)
            .group("artent:phial")
            .criterion(hasItem(Items.GLASS), conditionsFromItem(Items.GLASS))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModItems.GoldenBucket)
            .pattern("g g")
            .pattern(" g ")
            .input('g', Items.GOLD_INGOT)
            .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModItems.SmallConcentratePhial, 9)
            .pattern("c")
            .pattern("g")
            .input('g', Items.GLASS)
            .input('c', Items.CLAY_BALL)
            .group("artent:concentrate_phial")
            .criterion(hasItem(ModBlocks.FermentingBarrelItem), conditionsFromItem(ModBlocks.FermentingBarrelItem))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModItems.MediumConcentratePhial, 1)
            .pattern(" c ")
            .pattern("g g")
            .pattern(" g ")
            .input('g', Items.GLASS)
            .input('c', Items.CLAY_BALL)
            .group("artent:concentrate_phial")
            .criterion(hasItem(ModBlocks.FermentingBarrelItem), conditionsFromItem(ModBlocks.FermentingBarrelItem))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModItems.BigConcentratePhial, 1)
            .pattern("ggg")
            .pattern("gcg")
            .pattern("ggg")
            .input('g', Items.GLASS)
            .input('c', Items.CLAY_BALL)
            .group("artent:concentrate_phial")
            .criterion(hasItem(ModBlocks.FermentingBarrelItem), conditionsFromItem(ModBlocks.FermentingBarrelItem))
            .offerTo(recipeExporter);

        // CRIMSON TREE

        createShapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CrimsonwoodPlanksItem, 4)
            .input(ModBlocks.CrimsonwoodLogItem)
            .criterion(hasItem(ModBlocks.CrimsonwoodLogItem), conditionsFromItem(ModBlocks.CrimsonwoodLogItem))
            .offerTo(recipeExporter);
      }
    };
  }

  @Override
  public String getName() {
    return "ArtentPotionsRecipeProvider ";
  }
}
