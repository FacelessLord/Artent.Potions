package faceless.artent.potions.recipes;

import faceless.artent.potions.brewingApi.AlchemicalPotion;

public record FermentationRecipe(AlchemicalPotion source, AlchemicalPotion result, int seconds) {
}
