package faceless.artent.potions.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

import java.util.Optional;

public record DryingRecipe(Ingredient source, ItemStack result, int time, ItemStack byproduct, float byproductChance) {
  public boolean matches(DryingRecipeInput input, World world) {
    return Ingredient.matches(Optional.of(this.source), input.source());
  }

  public ItemStack craft(DryingRecipeInput input, RegistryWrapper.WrapperLookup registries) {
    return this.result;
  }

  public static class Serializer {
    public static final Codec<DryingRecipe> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            Ingredient.CODEC.fieldOf("source").forGetter(recipe -> recipe.source),
            ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
            Codecs.POSITIVE_INT.fieldOf("time").forGetter(recipe -> recipe.time),
            ItemStack.CODEC
                .fieldOf("byproduct")
                .forGetter(recipe -> recipe.byproduct != null ? recipe.byproduct : ItemStack.EMPTY),
            Codecs.POSITIVE_FLOAT.fieldOf("byproductChance").forGetter(recipe -> recipe.byproductChance))
        .apply(instance, DryingRecipe::new));
    public static final MapCodec<DryingRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance
        .group(
            Ingredient.CODEC.fieldOf("source").forGetter(recipe -> recipe.source),
            ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
            Codecs.POSITIVE_INT.fieldOf("time").forGetter(recipe -> recipe.time),
            ItemStack.CODEC
                .fieldOf("byproduct")
                .forGetter(recipe -> recipe.byproduct != null ? recipe.byproduct : ItemStack.EMPTY),
            Codecs.POSITIVE_FLOAT.fieldOf("byproductChance").forGetter(recipe -> recipe.byproductChance))
        .apply(instance, DryingRecipe::new));
    public static final PacketCodec<RegistryByteBuf, DryingRecipe> PACKET_CODEC = PacketCodec.tuple(
        Ingredient.PACKET_CODEC,
        (DryingRecipe recipe) -> recipe.source,
        ItemStack.PACKET_CODEC,
        (DryingRecipe recipe) -> recipe.result,
        PacketCodecs.INTEGER,
        (DryingRecipe recipe) -> recipe.time,
        ItemStack.PACKET_CODEC,
        (DryingRecipe recipe) -> recipe.byproduct != null ? recipe.byproduct : ItemStack.EMPTY,
        PacketCodecs.FLOAT,
        (DryingRecipe recipe) -> recipe.byproductChance,
        DryingRecipe::new);
  }
}
