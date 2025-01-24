package faceless.artent.potions.registry;

import faceless.artent.core.math.Color;
import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.BrewingAutomata;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.objects.BrewingRecipes;

import java.util.Hashtable;
import java.util.List;

import static faceless.artent.potions.objects.AlchemicalPotions.*;

public class BrewingRegistry implements IRegistry {
  public static final Hashtable<BrewingIngredient, Color> Ingredients = new Hashtable<>();

  @Override
  public void register() {
    registerIngredients();
    registerRecipes();
    registerAdditionalEdges();
  }

  private void registerIngredients() {
    Ingredients.put(BrewingRecipes.Shroom, Color.Cobalt);
    Ingredients.put(BrewingRecipes.Shadowveil, Color.Black);
    Ingredients.put(BrewingRecipes.CrimsonwoodBerry, Color.Red);
    Ingredients.put(BrewingRecipes.CrimsonLeaf, Color.Red);
    Ingredients.put(BrewingRecipes.Blackberry, Color.Purple);
    Ingredients.put(BrewingRecipes.Blueberry, Color.Blue);
    Ingredients.put(BrewingRecipes.Cloudberry, Color.Gold);
    Ingredients.put(BrewingRecipes.Raspberry, Color.Red);
    Ingredients.put(BrewingRecipes.Bone, Color.White);
    Ingredients.put(BrewingRecipes.GlowstoneDust, Color.Yellow);
    Ingredients.put(BrewingRecipes.Redstone, Color.Red);
    Ingredients.put(BrewingRecipes.Apple, Color.Red);
    Ingredients.put(BrewingRecipes.BrownMushroom, Color.Brown);
    Ingredients.put(BrewingRecipes.RedMushroom, Color.Red);
    Ingredients.put(BrewingRecipes.ChorusPlant, Color.Purple);
    Ingredients.put(BrewingRecipes.BlazePowder, Color.Gold);
    Ingredients.put(BrewingRecipes.EnderPearl, Color.Cyan);
    Ingredients.put(BrewingRecipes.GlisteningMelon, Color.Gold.add(Color.Red));
    Ingredients.put(BrewingRecipes.Kelp, Color.Green);
    Ingredients.put(BrewingRecipes.Slime, Color.Green);
    Ingredients.put(BrewingRecipes.Snowball, Color.White);
    Ingredients.put(BrewingRecipes.RabbitsFoot, Color.Brown);
    Ingredients.put(BrewingRecipes.PufferFish, Color.Yellow);
    Ingredients.put(BrewingRecipes.Map, Color.Orange);
    Ingredients.put(BrewingRecipes.DragonBreath, Color.Pink.add(Color.Purple));
    Ingredients.put(BrewingRecipes.NetherWart, Color.Red.add(Color.Purple));
    Ingredients.put(BrewingRecipes.SpiderEye, Color.Red);
    Ingredients.put(BrewingRecipes.FermentedSpiderEye, Color.Red.add(Color.Brown));
    Ingredients.put(BrewingRecipes.MagmaCream, Color.Red.add(Color.Gold));
    Ingredients.put(BrewingRecipes.PhantomMembrane, Color.Cobalt);
  }

  private void registerRecipes() {
    BrewingRecipes.RecipeAutomata = new BrewingAutomata();
    BrewingRecipes.RecipeAutomata.addRecipe(POISON,
                                            BrewingRecipes.Blackberry,
                                            BrewingRecipes.NetherWart,
                                            BrewingRecipes.SpiderEye,
                                            BrewingRecipes.Cloudberry,
                                            BrewingRecipes.Kelp);
    BrewingRecipes.RecipeAutomata.addRecipe(STRENGTH,
                                            BrewingRecipes.Blackberry,
                                            BrewingRecipes.NetherWart,
                                            BrewingRecipes.Shadowveil,
                                            BrewingRecipes.Raspberry,
                                            BrewingRecipes.BlazePowder);
    BrewingRecipes.RecipeAutomata.addRecipe(VAMPIRISM,
                                            BrewingRecipes.Blackberry,
                                            BrewingRecipes.Redstone,
                                            BrewingRecipes.Shroom,
                                            BrewingRecipes.Blueberry,
                                            BrewingRecipes.CrimsonwoodBerry);
    BrewingRecipes.RecipeAutomata.addRecipe(HOLY_WATER,
                                            BrewingRecipes.Blackberry,
                                            BrewingRecipes.Redstone,
                                            BrewingRecipes.Bone,
                                            BrewingRecipes.Raspberry,
                                            BrewingRecipes.Kelp,
                                            BrewingRecipes.PufferFish);
    BrewingRecipes.RecipeAutomata.addRecipe(BERSERK,
                                            BrewingRecipes.Blackberry,
                                            BrewingRecipes.Redstone,
                                            BrewingRecipes.Shadowveil,
                                            BrewingRecipes.Cloudberry,
                                            BrewingRecipes.GlowstoneDust,
                                            BrewingRecipes.FermentedSpiderEye);

    BrewingRecipes.RecipeAutomata.addRecipe(STONE_SKIN,
                                            BrewingRecipes.Raspberry,
                                            BrewingRecipes.CrimsonLeaf,
                                            BrewingRecipes.Cloudberry,
                                            BrewingRecipes.Blackberry,
                                            BrewingRecipes.PufferFish);
    BrewingRecipes.RecipeAutomata.addRecipe(FIRE_RESISTANCE,
                                            BrewingRecipes.Raspberry,
                                            BrewingRecipes.CrimsonLeaf,
                                            BrewingRecipes.Kelp,
                                            BrewingRecipes.Redstone,
                                            BrewingRecipes.MagmaCream);
    BrewingRecipes.RecipeAutomata.addRecipe(FREEZING,
                                            BrewingRecipes.Raspberry,
                                            BrewingRecipes.CrimsonLeaf,
                                            BrewingRecipes.Slime,
                                            BrewingRecipes.GlowstoneDust,
                                            BrewingRecipes.Snowball);
    BrewingRecipes.RecipeAutomata.addRecipe(LIQUID_FLAME,
                                            BrewingRecipes.Raspberry,
                                            BrewingRecipes.CrimsonLeaf,
                                            BrewingRecipes.Shadowveil,
                                            BrewingRecipes.Blackberry,
                                            BrewingRecipes.BlazePowder);
    BrewingRecipes.RecipeAutomata.addRecipe(HEALING,
                                            BrewingRecipes.Raspberry,
                                            BrewingRecipes.Apple,
                                            BrewingRecipes.Shroom,
                                            BrewingRecipes.Raspberry,
                                            BrewingRecipes.FermentedSpiderEye);
    BrewingRecipes.RecipeAutomata.addRecipe(ANTIDOTE,
                                            BrewingRecipes.Raspberry,
                                            BrewingRecipes.Apple,
                                            BrewingRecipes.SpiderEye,
                                            BrewingRecipes.Cloudberry,
                                            BrewingRecipes.Kelp);

    BrewingRecipes.RecipeAutomata.addRecipe(FAST_SWIMMING,
                                            BrewingRecipes.Cloudberry,
                                            BrewingRecipes.Shadowveil,
                                            BrewingRecipes.Blueberry,
                                            BrewingRecipes.Kelp,
                                            BrewingRecipes.Snowball,
                                            BrewingRecipes.Shroom);
    BrewingRecipes.RecipeAutomata.addRecipe(WATER_BREATHING,
                                            BrewingRecipes.Cloudberry,
                                            BrewingRecipes.Shadowveil,
                                            BrewingRecipes.Blueberry,
                                            BrewingRecipes.Slime,
                                            BrewingRecipes.Kelp,
                                            BrewingRecipes.Shroom);
    BrewingRecipes.RecipeAutomata.addRecipe(JUMP_BOOST,
                                            BrewingRecipes.Cloudberry,
                                            BrewingRecipes.Shadowveil,
                                            BrewingRecipes.Blueberry,
                                            BrewingRecipes.NetherWart,
                                            BrewingRecipes.CrimsonLeaf,
                                            BrewingRecipes.Cloudberry);
    BrewingRecipes.RecipeAutomata.addRecipe(FEATHER_FALLING,
                                            BrewingRecipes.Cloudberry,
                                            BrewingRecipes.Redstone,
                                            BrewingRecipes.PufferFish,
                                            BrewingRecipes.BrownMushroom,
                                            BrewingRecipes.Shadowveil,
                                            BrewingRecipes.Bone);
    BrewingRecipes.RecipeAutomata.addRecipe(NIGHT_VISION,
                                            BrewingRecipes.Cloudberry,
                                            BrewingRecipes.Redstone,
                                            BrewingRecipes.PufferFish,
                                            BrewingRecipes.NetherWart,
                                            BrewingRecipes.CrimsonLeaf,
                                            BrewingRecipes.GlowstoneDust);

    BrewingRecipes.RecipeAutomata.addRecipe(FLIGHT,
                                            BrewingRecipes.Blueberry,
                                            BrewingRecipes.Shadowveil,
                                            BrewingRecipes.Shroom,
                                            BrewingRecipes.EnderPearl,
                                            BrewingRecipes.NetherWart,
                                            BrewingRecipes.ChorusPlant);
    BrewingRecipes.RecipeAutomata.addRecipe(LUCK,
                                            BrewingRecipes.Blueberry,
                                            BrewingRecipes.Shadowveil,
                                            BrewingRecipes.Shroom,
                                            BrewingRecipes.ChorusPlant,
                                            BrewingRecipes.CrimsonwoodBerry,
                                            BrewingRecipes.EnderPearl);
    BrewingRecipes.RecipeAutomata.addRecipe(SATURATION,
                                            BrewingRecipes.Blueberry,
                                            BrewingRecipes.Shadowveil,
                                            BrewingRecipes.CrimsonLeaf,
                                            BrewingRecipes.BlazePowder,
                                            BrewingRecipes.Slime,
                                            BrewingRecipes.PufferFish);
    BrewingRecipes.RecipeAutomata.addRecipe(LUMBERJACK,
                                            BrewingRecipes.Blueberry,
                                            BrewingRecipes.BrownMushroom,
                                            BrewingRecipes.Kelp,
                                            BrewingRecipes.Bone,
                                            BrewingRecipes.Cloudberry,
                                            BrewingRecipes.CrimsonLeaf);
    BrewingRecipes.RecipeAutomata.addRecipe(HASTE,
                                            BrewingRecipes.Blueberry,
                                            BrewingRecipes.BrownMushroom,
                                            BrewingRecipes.Kelp,
                                            BrewingRecipes.Bone,
                                            BrewingRecipes.Cloudberry,
                                            BrewingRecipes.CrimsonLeaf,
                                            BrewingRecipes.FermentedSpiderEye,
                                            BrewingRecipes.Shadowveil,
                                            BrewingRecipes.NetherWart,
                                            BrewingRecipes.GlowstoneDust);
    BrewingRecipes.RecipeAutomata.addRecipe(LEVITATION,
                                            BrewingRecipes.Blackberry,
                                            BrewingRecipes.Shroom,
                                            BrewingRecipes.Kelp,
                                            BrewingRecipes.Bone,
                                            BrewingRecipes.PhantomMembrane);
  }

  private void registerAdditionalEdges() {
    BrewingRecipes.RecipeAutomata.addEdge(List.of(
                                              BrewingRecipes.Blackberry,
                                              BrewingRecipes.NetherWart,
                                              BrewingRecipes.SpiderEye),
                                          List.of(BrewingRecipes.Raspberry,
                                                  BrewingRecipes.Apple,
                                                  BrewingRecipes.SpiderEye,
                                                  BrewingRecipes.Cloudberry),
                                          BrewingRecipes.Shroom);
    BrewingRecipes.RecipeAutomata.addEdge(
        List.of(BrewingRecipes.Blackberry,
                BrewingRecipes.NetherWart,
                BrewingRecipes.Shadowveil,
                BrewingRecipes.Raspberry),
        List.of(BrewingRecipes.Blackberry, BrewingRecipes.Redstone, BrewingRecipes.Shroom, BrewingRecipes.Blueberry),
        BrewingRecipes.Redstone);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(
                                              BrewingRecipes.Blackberry,
                                              BrewingRecipes.Redstone,
                                              BrewingRecipes.Bone,
                                              BrewingRecipes.Raspberry),
                                          List.of(BrewingRecipes.Blackberry,
                                                  BrewingRecipes.Redstone,
                                                  BrewingRecipes.Shroom),
                                          BrewingRecipes.CrimsonwoodBerry);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(BrewingRecipes.Blackberry,
                                                  BrewingRecipes.Redstone,
                                                  BrewingRecipes.Bone),
                                          List.of(BrewingRecipes.Raspberry),
                                          BrewingRecipes.Kelp);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(BrewingRecipes.Blackberry,
                                                  BrewingRecipes.Redstone,
                                                  BrewingRecipes.Shadowveil),
                                          List.of(BrewingRecipes.Raspberry),
                                          BrewingRecipes.Raspberry);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(
                                              BrewingRecipes.Raspberry,
                                              BrewingRecipes.CrimsonLeaf,
                                              BrewingRecipes.Cloudberry),
                                          List.of(BrewingRecipes.Blackberry,
                                                  BrewingRecipes.Redstone,
                                                  BrewingRecipes.Shadowveil,
                                                  BrewingRecipes.Cloudberry),
                                          BrewingRecipes.Allium);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(
                                              BrewingRecipes.Raspberry,
                                              BrewingRecipes.CrimsonLeaf,
                                              BrewingRecipes.Kelp),
                                          List.of(BrewingRecipes.Raspberry,
                                                  BrewingRecipes.CrimsonLeaf,
                                                  BrewingRecipes.Slime,
                                                  BrewingRecipes.GlowstoneDust),
                                          BrewingRecipes.GlowstoneDust);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(
                                              BrewingRecipes.Raspberry,
                                              BrewingRecipes.Apple,
                                              BrewingRecipes.Shroom,
                                              BrewingRecipes.Raspberry),
                                          List.of(BrewingRecipes.Raspberry,
                                                  BrewingRecipes.CrimsonLeaf,
                                                  BrewingRecipes.Cloudberry,
                                                  BrewingRecipes.Blackberry),
                                          BrewingRecipes.Blackberry);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(
                                              BrewingRecipes.Raspberry,
                                              BrewingRecipes.Apple,
                                              BrewingRecipes.SpiderEye),
                                          List.of(BrewingRecipes.Raspberry,
                                                  BrewingRecipes.CrimsonLeaf,
                                                  BrewingRecipes.Shadowveil),
                                          BrewingRecipes.Blueberry);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(BrewingRecipes.Raspberry,
                                                  BrewingRecipes.Apple,
                                                  BrewingRecipes.Shroom),
                                          List.of(BrewingRecipes.Cloudberry, BrewingRecipes.Shadowveil),
                                          BrewingRecipes.Blackberry);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(BrewingRecipes.Raspberry, BrewingRecipes.CrimsonLeaf),
                                          List.of(BrewingRecipes.Cloudberry,
                                                  BrewingRecipes.Shadowveil,
                                                  BrewingRecipes.Blueberry),
                                          BrewingRecipes.Blackberry);
    BrewingRecipes.RecipeAutomata.addEdge(
        List.of(BrewingRecipes.Cloudberry, BrewingRecipes.Shadowveil, BrewingRecipes.Blueberry, BrewingRecipes.Kelp),
        List.of(BrewingRecipes.Raspberry, BrewingRecipes.Apple, BrewingRecipes.SpiderEye),
        BrewingRecipes.FermentedSpiderEye);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(BrewingRecipes.Cloudberry),
                                          List.of(BrewingRecipes.Raspberry, BrewingRecipes.Apple),
                                          BrewingRecipes.Apple);
    BrewingRecipes.RecipeAutomata.addEdge(
        List.of(BrewingRecipes.Cloudberry, BrewingRecipes.Shadowveil, BrewingRecipes.Blueberry, BrewingRecipes.Kelp),
        List.of(BrewingRecipes.Cloudberry, BrewingRecipes.Shadowveil, BrewingRecipes.Blueberry, BrewingRecipes.Slime),
        BrewingRecipes.Raspberry);
    BrewingRecipes.RecipeAutomata.addEdge(
        List.of(BrewingRecipes.Cloudberry,
                BrewingRecipes.Shadowveil,
                BrewingRecipes.Blueberry,
                BrewingRecipes.NetherWart),
        List.of(BrewingRecipes.Cloudberry, BrewingRecipes.Redstone),
        BrewingRecipes.BlazePowder);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(BrewingRecipes.Cloudberry, BrewingRecipes.Shadowveil),
                                          List.of(BrewingRecipes.Cloudberry,
                                                  BrewingRecipes.Redstone,
                                                  BrewingRecipes.PufferFish),
                                          BrewingRecipes.Bone);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(BrewingRecipes.Cloudberry,
                                                  BrewingRecipes.Redstone,
                                                  BrewingRecipes.PufferFish,
                                                  BrewingRecipes.BrownMushroom),
                                          List.of(BrewingRecipes.Cloudberry,
                                                  BrewingRecipes.Shadowveil,
                                                  BrewingRecipes.Blueberry),
                                          BrewingRecipes.Allium);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(BrewingRecipes.Blueberry, BrewingRecipes.Shadowveil),
                                          List.of(BrewingRecipes.Cloudberry, BrewingRecipes.Redstone),
                                          BrewingRecipes.Cloudberry);
    BrewingRecipes.RecipeAutomata.addEdge(
        List.of(BrewingRecipes.Blueberry, BrewingRecipes.Shadowveil, BrewingRecipes.Shroom, BrewingRecipes.ChorusPlant),
        List.of(BrewingRecipes.Cloudberry, BrewingRecipes.Redstone, BrewingRecipes.PufferFish),
        BrewingRecipes.BrownMushroom);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(BrewingRecipes.Cloudberry,
                                                  BrewingRecipes.Redstone,
                                                  BrewingRecipes.PufferFish,
                                                  BrewingRecipes.NetherWart,
                                                  BrewingRecipes.CrimsonLeaf),
                                          List.of(BrewingRecipes.Blueberry,
                                                  BrewingRecipes.Shadowveil,
                                                  BrewingRecipes.CrimsonLeaf,
                                                  BrewingRecipes.BlazePowder,
                                                  BrewingRecipes.Slime),
                                          BrewingRecipes.BlazePowder);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(BrewingRecipes.Blueberry,
                                                  BrewingRecipes.Shadowveil,
                                                  BrewingRecipes.Shroom,
                                                  BrewingRecipes.EnderPearl,
                                                  BrewingRecipes.NetherWart),
                                          List.of(BrewingRecipes.Blueberry,
                                                  BrewingRecipes.Shadowveil,
                                                  BrewingRecipes.Shroom,
                                                  BrewingRecipes.ChorusPlant,
                                                  BrewingRecipes.CrimsonwoodBerry),
                                          BrewingRecipes.CrimsonwoodBerry);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(BrewingRecipes.Blueberry,
                                                  BrewingRecipes.Shadowveil,
                                                  BrewingRecipes.CrimsonLeaf,
                                                  BrewingRecipes.BlazePowder),
                                          List.of(BrewingRecipes.Blueberry,
                                                  BrewingRecipes.Shadowveil,
                                                  BrewingRecipes.Shroom,
                                                  BrewingRecipes.ChorusPlant),
                                          BrewingRecipes.ChorusPlant);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(BrewingRecipes.Blueberry,
                                                  BrewingRecipes.Shadowveil,
                                                  BrewingRecipes.CrimsonLeaf,
                                                  BrewingRecipes.BlazePowder),
                                          List.of(BrewingRecipes.Blueberry,
                                                  BrewingRecipes.BrownMushroom,
                                                  BrewingRecipes.GlowstoneDust,
                                                  BrewingRecipes.Cloudberry),
                                          BrewingRecipes.GlowstoneDust);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(
                                              BrewingRecipes.Blueberry,
                                              BrewingRecipes.BrownMushroom,
                                              BrewingRecipes.Kelp,
                                              BrewingRecipes.Bone),
                                          List.of(BrewingRecipes.Blueberry,
                                                  BrewingRecipes.BrownMushroom,
                                                  BrewingRecipes.GlowstoneDust,
                                                  BrewingRecipes.Cloudberry,
                                                  BrewingRecipes.BlazePowder),
                                          BrewingRecipes.GlowstoneDust);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(BrewingRecipes.Blueberry,
                                                  BrewingRecipes.BrownMushroom,
                                                  BrewingRecipes.Kelp),
                                          List.of(BrewingRecipes.Blackberry, BrewingRecipes.NetherWart),
                                          BrewingRecipes.GlowstoneDust);
    BrewingRecipes.RecipeAutomata.addEdge(List.of(
                                              BrewingRecipes.Blackberry,
                                              BrewingRecipes.NetherWart,
                                              BrewingRecipes.SpiderEye),
                                          List.of(BrewingRecipes.Blueberry,
                                                  BrewingRecipes.NetherWart,
                                                  BrewingRecipes.Kelp,
                                                  BrewingRecipes.Bone),
                                          BrewingRecipes.Shadowveil);
  }

}
