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
            .criterion(hasItem(ModItems.SmallBottle), conditionsFromItem(ModItems.SmallBottle))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModItems.GoldenBucket)
            .pattern("g g")
            .pattern(" g ")
            .input('g', Items.GOLD_INGOT)
            .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModItems.SmallBottle, 3)
            .pattern("c")
            .pattern("g")
            .input('g', Items.GLASS)
            .input('c', Items.CLAY_BALL)
            .group("artent:concentrate_phial")
            .criterion(hasItem(ModBlocks.BrewingCauldron), conditionsFromItem(ModBlocks.BrewingCauldron))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModItems.MediumBottle, 1)
            .pattern(" c ")
            .pattern("g g")
            .pattern(" g ")
            .input('g', Items.GLASS)
            .input('c', Items.CLAY_BALL)
            .group("artent:concentrate_phial")
            .criterion(hasItem(ModBlocks.BrewingCauldron), conditionsFromItem(ModBlocks.BrewingCauldron))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModItems.BigBottle, 1)
            .pattern("ggg")
            .pattern("gcg")
            .pattern("ggg")
            .input('g', Items.GLASS)
            .input('c', Items.CLAY_BALL)
            .group("artent:concentrate_phial")
            .criterion(hasItem(ModBlocks.BrewingCauldron), conditionsFromItem(ModBlocks.BrewingCauldron))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModItems.SmallBottleExplosive, 3)
            .pattern("c")
            .pattern("p")
            .pattern("g")
            .input('c', Items.CLAY_BALL)
            .input('p', Items.GUNPOWDER)
            .input('g', Items.GLASS)
            .group("artent:concentrate_phial_explosive")
            .criterion(hasItem(ModBlocks.BrewingCauldron), conditionsFromItem(ModBlocks.BrewingCauldron))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModItems.MediumBottleExplosive, 1)
            .pattern(" c ")
            .pattern("gpg")
            .pattern(" g ")
            .input('c', Items.CLAY_BALL)
            .input('p', Items.GUNPOWDER)
            .input('g', Items.GLASS)
            .group("artent:concentrate_phial_explosive")
            .criterion(hasItem(ModBlocks.BrewingCauldron), conditionsFromItem(ModBlocks.BrewingCauldron))
            .offerTo(recipeExporter);
        createShaped(RecipeCategory.BREWING, ModItems.BigBottleExplosive, 1)
            .pattern("gpg")
            .pattern("gcg")
            .pattern("ggg")
            .input('c', Items.CLAY_BALL)
            .input('p', Items.GUNPOWDER)
            .input('g', Items.GLASS)
            .group("artent:concentrate_phial_explosive")
            .criterion(hasItem(ModBlocks.BrewingCauldron), conditionsFromItem(ModBlocks.BrewingCauldron))
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
    return "ArtentPotionsRecipeProvider";
  }
}
