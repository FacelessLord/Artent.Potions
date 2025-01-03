package faceless.artent_potions.objects;

import faceless.artent_potions.brewingApi.block.ArtentBlock;
import faceless.artent_potions.brewing.block.*;
import faceless.artent_potions.sharpening.block.SharpeningAnvil;
import faceless.artent_potions.spells.block.InscriptionTable;
import faceless.artent_potions.spells.block.InscriptionTablePt2;
import faceless.artent_potions.spells.block.VoidBlock;
import faceless.artent_potions.trading.block.Trader;
import faceless.artent_potions.transmutations.block.AlchemicalCircleBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public final class ModBlocks {

    /**
     * A shortcut to always return {@code false} a context predicate, used as
     * {@code settings.solidBlock(Blocks::never)}.
     * Copied from new.minecraft.block.Blocks
     */
    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return false;
    }

    public static BrewingCauldron BrewingCauldron = new BrewingCauldron(
      FabricBlockSettings
        .copyOf(Blocks.CAULDRON)
        .mapColor(MapColor.GRAY)
        .requiresTool()
        .nonOpaque()
        .luminance(state -> state.get(faceless.artent_potions.brewing.block.BrewingCauldron.IS_BURNING) ? 15 : 0)
        .strength(2f));
    public static BlockItem BrewingCauldronItem;
    public static BrewingCauldronCopper BrewingCauldronCopper = new BrewingCauldronCopper(
      FabricBlockSettings
        .copyOf(Blocks.CAULDRON)
        .mapColor(MapColor.GRAY)
        .requiresTool()
        .nonOpaque()
        .luminance(state -> state.get(
          faceless.artent_potions.brewing.block.BrewingCauldron.IS_BURNING) ? 15 : 0)
        .strength(2f));
    public static BlockItem BrewingCauldronCopperItem;
    public static Block CauldronFluid = new Block(
      FabricBlockSettings
        .copyOf(Blocks.WATER)
        .mapColor(MapColor.GRAY)
        .nonOpaque()
        .dropsNothing());
    public static MushroomPlantBlock Shroom = new MushroomPlantBlock(null,
                                                                     FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM)
                                                                                        .mapColor(MapColor.BROWN)
                                                                                        .nonOpaque()
                                                                                        .noCollision()
                                                                                        .ticksRandomly()
                                                                                        .breakInstantly()
                                                                                        .sounds(BlockSoundGroup.GRASS)
                                                                                        .luminance(state -> 1)
                                                                                        .postProcess((a, b, c) -> true));
    public static BlockItem ShroomItem;
    public static FlowerBlock Shadowveil = new FlowerBlock(StatusEffects.BAD_OMEN,
                                                           5,
                                                           FabricBlockSettings.copyOf(Blocks.ALLIUM)
                                                                              .nonOpaque()
                                                                              .noCollision()
                                                                              .breakInstantly()
                                                                              .sounds(BlockSoundGroup.GRASS));
    public static BlockItem ShadowveilItem;
    public static BerryBush[] berryBush = new BerryBush[]{new BerryBush(0,
                                                                        FabricBlockSettings.copyOf(Blocks.ACACIA_LEAVES)
                                                                                           .mapColor(MapColor.GREEN)
                                                                                           .nonOpaque()
                                                                                           .sounds(BlockSoundGroup.GRASS)), new BerryBush(
      1,
      FabricBlockSettings.copyOf(Blocks.ACACIA_LEAVES)
                         .mapColor(MapColor.GREEN)
                         .nonOpaque()
                         .sounds(BlockSoundGroup.GRASS)), new BerryBush(2,
                                                                        FabricBlockSettings.copyOf(Blocks.ACACIA_LEAVES)
                                                                                           .mapColor(MapColor.GREEN)
                                                                                           .nonOpaque()
                                                                                           .sounds(BlockSoundGroup.GRASS)), new BerryBush(
      3,
      FabricBlockSettings.copyOf(Blocks.ACACIA_LEAVES)
                         .mapColor(MapColor.GREEN)
                         .nonOpaque()
                         .sounds(BlockSoundGroup.GRASS)),};
    public static BlockItem[] berryBushItem = new BlockItem[berryBush.length];

    public static PillarBlock CrimsonwoodLog =
      new PillarBlock(FabricBlockSettings
                        .copyOf(Blocks.OAK_LOG)
                        .mapColor(MapColor.SPRUCE_BROWN)
                        .strength(2.0f)
                        .sounds(BlockSoundGroup.WOOD));
    public static BlockItem CrimsonwoodLogItem;
    public static CrimsonwoodLeaves CrimsonwoodLeaves =
      new CrimsonwoodLeaves(FabricBlockSettings
                              .copyOf(Blocks.ACACIA_LEAVES)
                              .strength(0.2f)
                              .ticksRandomly()
                              .sounds(BlockSoundGroup.GRASS)
                              .allowsSpawning(ModBlocks::never)
                              .suffocates(ModBlocks::never)
                              .blockVision(ModBlocks::never));
    public static BlockItem CrimsonwoodLeavesItem;

    //	public static SaplingBlock CrimsonwoodSapling = new CrimsonwoodSapling(new CrimsonwoodSaplingGenerator(), FabricBlockSettings
//		.of(Material.PLANT)
//		.noCollision()
//		.ticksRandomly()
//		.breakInstantly()
//		.sounds(BlockSoundGroup.GRASS));
//	public static BlockItem CrimsonwoodSaplingItem = new BlockItem(CrimsonwoodSapling, new FabricItemSettings().group(Core.General));
    public static Block CrimsonwoodPlanks =
      new Block(FabricBlockSettings
                  .copyOf(Blocks.OAK_PLANKS)
                  .mapColor(MapColor.BROWN)
                  .strength(2.0f, 3.0f)
                  .sounds(BlockSoundGroup.WOOD));
    public static BlockItem CrimsonwoodPlanksItem;
    public static FermentingBarrel FermentingBarrel =
      new FermentingBarrel(FabricBlockSettings
                             .copyOf(Blocks.OAK_PLANKS)
                             .mapColor(MapColor.BROWN)
                             .nonOpaque()
                             .strength(2.0f, 3.0f)
                             .sounds(BlockSoundGroup.WOOD));
    public static BlockItem FermentingBarrelItem;
}
