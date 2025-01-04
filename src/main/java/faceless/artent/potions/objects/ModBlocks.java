package faceless.artent.potions.objects;

import faceless.artent.core.item.group.ArtentItemGroupBuilder;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.block.*;
import faceless.artent.potions.ingridients.Ingredients;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.Function;

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

  public static final Block BrewingCauldron;
  public static final Item BrewingCauldronItem;

  static {
    var pair = register(
        "cauldron",
        BrewingCauldron::new,
        Block.Settings
            .copy(Blocks.CAULDRON)
            .mapColor(MapColor.GRAY)
            .requiresTool()
            .nonOpaque()
            .luminance(state -> state.get(faceless.artent.potions.block.BrewingCauldron.IS_BURNING) ? 15 : 0)
            .strength(2f),
        ModItemGroups.Potions);
    BrewingCauldron = pair.getLeft();
    BrewingCauldronItem = pair.getRight();
  }

  public static final Block BrewingCauldronCopper;
  public static final Item BrewingCauldronCopperItem;

  static {
    var pair = register(
        "cauldron_copper",
        BrewingCauldronCopper::new,
        Block.Settings
            .copy(Blocks.CAULDRON)
            .mapColor(MapColor.GRAY)
            .requiresTool()
            .nonOpaque()
            .luminance(state -> state.get(faceless.artent.potions.block.BrewingCauldron.IS_BURNING) ? 15 : 0)
            .strength(2f),
        ModItemGroups.Potions);
    BrewingCauldronCopper = pair.getLeft();
    BrewingCauldronCopperItem = pair.getRight();
  }

  public static final Block CauldronFluid;
  public static final Item CauldronFluidItem;

  static {
    var pair = register(
        "cauldron_fluid",
        Block::new,
        Block.Settings.copy(Blocks.WATER).mapColor(MapColor.GRAY).nonOpaque().dropsNothing(),
        null);
    CauldronFluid = pair.getLeft();
    CauldronFluidItem = pair.getRight();
  }

  public static final Block Shroom;
  public static final Item ShroomItem;

  static {
    var pair = register(
        "shroom",
        (settings) -> new MushroomPlantBlock(null, settings),
        Block.Settings
            .copy(Blocks.BROWN_MUSHROOM)
            .mapColor(MapColor.BROWN)
            .nonOpaque()
            .noCollision()
            .ticksRandomly()
            .breakInstantly()
            .sounds(BlockSoundGroup.GRASS)
            .luminance(state -> 1)
            .postProcess((a, b, c) -> true),
        ModItemGroups.Potions);
    Shroom = pair.getLeft();
    ShroomItem = pair.getRight();
  }

  public static final Block Shadowveil;
  public static final Item ShadowveilItem;

  static {
    var pair = register(
        "shadowveil",
        (settings) -> new FlowerBlock(StatusEffects.BAD_OMEN, 5, settings),
        Block.Settings.copy(Blocks.ALLIUM).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS),
        ModItemGroups.Potions);
    Shadowveil = pair.getLeft();
    ShadowveilItem = pair.getRight();
  }

  public static final Block[] berryBush;
  public static final Item[] berryBushItem;

  static {
    berryBush = new Block[4];
    berryBushItem = new Item[4];
    for (int type = 0; type < berryBush.length; type++) {
      int finalType = type;
      var pair = register(
          Ingredients.GetBerryName(type) + "_bush",
          (settings) -> new BerryBush(finalType, settings),
          Block.Settings.copy(Blocks.ACACIA_LEAVES).mapColor(MapColor.GREEN).nonOpaque().sounds(BlockSoundGroup.GRASS),
          ModItemGroups.Potions);
      berryBush[type] = pair.getLeft();
      berryBushItem[type] = pair.getRight();
    }
  }

  public static final Block CrimsonwoodLog;
  public static final Item CrimsonwoodLogItem;

  static {
    var pair = register(
        "crimsonwood_log",
        PillarBlock::new,
        Block.Settings.copy(Blocks.OAK_LOG).mapColor(MapColor.SPRUCE_BROWN).strength(2.0f).sounds(BlockSoundGroup.WOOD),
        ModItemGroups.Potions);
    CrimsonwoodLog = pair.getLeft();
    CrimsonwoodLogItem = pair.getRight();
  }

  public static final Block CrimsonwoodLeaves;
  public static final Item CrimsonwoodLeavesItem;

  static {
    var pair = register(
        "crimsonwood_leaves",
        CrimsonwoodLeaves::new,
        Block.Settings
            .copy(Blocks.ACACIA_LEAVES)
            .strength(0.2f)
            .ticksRandomly()
            .sounds(BlockSoundGroup.GRASS)
            .allowsSpawning(ModBlocks::never)
            .suffocates(ModBlocks::never)
            .blockVision(ModBlocks::never),
        ModItemGroups.Potions);
    CrimsonwoodLeaves = pair.getLeft();
    CrimsonwoodLeavesItem = pair.getRight();
  }

  //	public static SaplingBlock CrimsonwoodSapling = new CrimsonwoodSapling(new CrimsonwoodSaplingGenerator(), FabricBlockSettings
//		.of(Material.PLANT)
//		.noCollision()
//		.ticksRandomly()
//		.breakInstantly()
//		.sounds(BlockSoundGroup.GRASS));
//	public static BlockItem CrimsonwoodSaplingItem = new BlockItem(CrimsonwoodSapling, new FabricItemSettings().group(Core.General));
  public static Block CrimsonwoodPlanks;
  public static Item CrimsonwoodPlanksItem;

  static {
    var pair = register(
        "crimsonwood_planks",
        Block::new,
        Block.Settings
            .copy(Blocks.OAK_PLANKS)
            .mapColor(MapColor.BROWN)
            .strength(2.0f, 3.0f)
            .sounds(BlockSoundGroup.WOOD),
        ModItemGroups.Potions);
    CrimsonwoodPlanks = pair.getLeft();
    CrimsonwoodPlanksItem = pair.getRight();
  }

  public static Block FermentingBarrel;
  public static Item FermentingBarrelItem;

  static {
    var pair = register(
        "fermenting_barrel",
        FermentingBarrel::new,
        Block.Settings
            .copy(Blocks.OAK_PLANKS)
            .mapColor(MapColor.BROWN)
            .nonOpaque()
            .strength(2.0f, 3.0f)
            .sounds(BlockSoundGroup.WOOD),
        ModItemGroups.Potions);
    FermentingBarrel = pair.getLeft();
    FermentingBarrelItem = pair.getRight();
  }

  public static Pair<Block, Item> register(
      String keyString,
      Function<AbstractBlock.Settings, Block> factory,
      AbstractBlock.Settings settings,
      ArtentItemGroupBuilder groupBuilder) {
    var key = keyOf(keyString);
    Block block = factory.apply(settings.registryKey(key));
    Registry.register(Registries.BLOCK, key, block);

    var itemKey = itemKeyOf(keyString);
    var blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
    Registry.register(Registries.ITEM, itemKey, blockItem);
    if (groupBuilder != null) groupBuilder.addItem(blockItem);
    return new Pair<>(block, blockItem);
  }

  private static RegistryKey<Block> keyOf(String id) {
    return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(ArtentPotions.MODID, id));
  }

  private static RegistryKey<Item> itemKeyOf(String id) {
    return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ArtentPotions.MODID, id));
  }
}
