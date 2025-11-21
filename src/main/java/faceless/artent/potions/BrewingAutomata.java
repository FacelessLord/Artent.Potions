package faceless.artent.potions;

import faceless.artent.core.api.DefaultedDict;
import faceless.artent.core.api.MiscUtils;
import faceless.artent.core.math.Color;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.recipes.PotionEnhancementRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class BrewingAutomata {
  public final DefaultedDict<State, List<Edge>> Edges = new DefaultedDict<>(ArrayList::new);

  public final State zeroState = new State(0, false, null);
  public final State invalidState = new State(-1, false, null);
  public int nextId = 1;

  public final Hashtable<AlchemicalPotion, PotionEnhancementRecipe> potionEnhancements = new Hashtable<>();

  public State getStateFromIngredients(List<BrewingIngredient> ingredients) {
    var state = zeroState;
    for (var ingredient : ingredients) {
      var edges = Edges.get(state);
      var edge = edges.stream().filter(e -> e.Character.equals(ingredient)).findFirst();
      if (edge.isEmpty()) {
        state = invalidState;
        break;
      }
      state = edge.get().Target;
    }
    return state;
  }

  public void addEdge(
      List<BrewingIngredient> sourcePath, List<BrewingIngredient> targetPath, BrewingIngredient character) {
    var source = getStateFromIngredients(sourcePath);
    var target = getStateFromIngredients(targetPath);
    var edge = new Edge(source, target, character);
    Edges.get(source).add(edge);
  }

  public void addEnhancement(PotionEnhancementRecipe recipe) {
    potionEnhancements.put(recipe.sourcePotion(), recipe);
  }

  public void addRecipe(AlchemicalPotion potion, List<BrewingIngredient> ingredients) {
    var color = Color.Blue;
    var state = zeroState;
    var i = 0;
    while (true) {
      var ingredient = ingredients.get(i);

      var edges = Edges.get(state);
      var edge = edges.stream().filter(e -> e.Character.equals(ingredient)).findFirst();
      if (edge.isEmpty()) {
        break;
      }
      color = color.add(ingredient.color);
      state = edge.get().Target;
      i++;
    }

    for (; i < ingredients.size(); i++) {
      var newState = i == ingredients.size() - 1 ? new State(nextId++, true, potion) : new State(nextId++, false, null);
      color = color.add(ingredients.get(i).color);
      var edge = new Edge(state, newState, ingredients.get(i));
      Edges.get(state).add(edge);
      state = newState;
    }
    potion.color = color;
    Color finalColor = color;
    Arrays.stream(potion.statusEffects).forEach(s -> MiscUtils.setStatusEffectColor(s, finalColor.toHex()));
  }

  public record Edge(State Source, State Target, BrewingIngredient Character) {
  }

  public record State(int id, boolean isFinishing, AlchemicalPotion brewedPotion) {
    @Override
    public String toString() {
      if (id == -1) return "Invalid State";
      if (id == 0) return "Initial State";
      return "State{" + "isFinishing=" + isFinishing + ", brewedPotion=" + brewedPotion + '}';
    }
  }
}