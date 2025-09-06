package faceless.artent.potions;

import faceless.artent.core.api.DefaultedDict;
import faceless.artent.core.api.MiscUtils;
import faceless.artent.core.math.Color;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.registry.BrewingRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BrewingAutomata {
  public DefaultedDict<State, List<Edge>> Edges = new DefaultedDict<>(ArrayList::new);
  public State zeroState = new State(0, false, null);
  public State invalidState = new State(-1, false, null);
  public int nextId = 1;

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

  public void addRecipe(AlchemicalPotion[] potions, BrewingIngredient... ingredients) {
    var newIngredients = new ArrayList<>(List.of(ingredients));
    for (AlchemicalPotion potion : potions) {
      addRecipe(potion, newIngredients.toArray(BrewingIngredient[]::new));
      newIngredients.add(ingredients[ingredients.length - 1]);
    }
  }

  public void addRecipe(AlchemicalPotion potion, BrewingIngredient... ingredients) {
    var color = Color.Blue;
    var state = zeroState;
    var i = 0;
    while (true) {
      var ingredient = ingredients[i];

      var edges = Edges.get(state);
      var edge = edges.stream().filter(e -> e.Character.equals(ingredient)).findFirst();
      if (edge.isEmpty()) {
        break;
      }
      color = color.add(BrewingRegistry.Ingredients.get(ingredient));
      state = edge.get().Target;
      i++;
    }

    for (; i < ingredients.length; i++) {
      var newState = i == ingredients.length - 1 ? new State(nextId++, true, potion) : new State(nextId++, false, null);
      color = color.add(BrewingRegistry.Ingredients.get(ingredients[i]));
      var edge = new Edge(state, newState, ingredients[i]);
      Edges.get(state).add(edge);
      state = newState;
    }
    potion.color = color;
    Color finalColor = color;
    // TODO add more blue color to potions like true blue, purple, cyan, emerald green,
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