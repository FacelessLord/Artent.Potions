package faceless.artent.potions.objects;

import faceless.artent.core.item.group.ArtentItemGroupBuilder;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.api.MushroomType;
import faceless.artent.potions.api.MushroomBlockInfo;
import faceless.artent.potions.block.*;
import faceless.artent.potions.ingridients.Ingredients;
import faceless.artent.potions.registry.FeatureRegistry;
import net.minecraft.block.*;
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

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class ModBlocks {
  public static Block BrewingCauldron;
  public static Item BrewingCauldronItem;

  public static Block BrewingCauldronCopper;
  public static Item BrewingCauldronCopperItem;

  public static Block CauldronFluid;
  public static Item CauldronFluidItem;

  public static Block Shroom;
  public static Item ShroomItem;

  public static Block Shadowveil;
  public static Item ShadowveilItem;

  public static Block BlazingMarigold;
  public static Item BlazingMarigoldItem;

  public static Block SlimeBerry;
  public static Item SlimeBerryItem;

  public static Block[] berryBush;
  public static Item[] berryBushItem;

  public static Block CrimsonwoodLog;
  public static Item CrimsonwoodLogItem;

  public static Block CrimsonwoodLeaves;
  public static Item CrimsonwoodLeavesItem;

  public static Block CrimsonwoodPlanks;
  public static Item CrimsonwoodPlanksItem;

  public static Block FermentingBarrel;
  public static Item FermentingBarrelItem;

  public static Block CrimsonwoodSapling;
  public static Item CrimsonwoodSaplingItem;

  public static Block DryingRack;
  public static Item DryingRackItem;

  public static Block FrostPumpkin;
  public static Item FrostPumpkinItem;
  public static Block FrostPumpkinStem;
  public static Item FrostPumpkinStemItem;
  public static Block FrostPumpkinStemAttached;
  public static Item FrostPumpkinStemAttachedItem;

  public static Block IceCrystalBlock;
  public static Item IceCrystalBlock_Item;
  public static IceCrystalCluster IceCrystalBud_Small;
  public static Item IceCrystalBud_Small_Item;
  public static IceCrystalCluster IceCrystalBud_Medium;
  public static Item IceCrystalBud_Medium_Item;
  public static IceCrystalCluster IceCrystalBud_Large;
  public static Item IceCrystalBud_Large_Item;
  public static IceCrystalCluster IceCrystalBud_Cluster;
  public static Item IceCrystalBud_Cluster_Item;

  public static MushroomBlockInfo[] MushroomInfo = new MushroomBlockInfo[3];

  public void register() {
    var BrewingCauldronPair = register(
        "cauldron",
        BrewingCauldron::new,
        Block.Settings
            .copy(Blocks.CAULDRON)
            .mapColor(MapColor.GRAY)
            .requiresTool()
            .nonOpaque()
            .ticksRandomly()
            .luminance(state -> state.get(faceless.artent.potions.block.BrewingCauldron.IS_BURNING) ? 15 : 0)
            .strength(2f),
        ModItemGroups.Potions);
    BrewingCauldron = BrewingCauldronPair.getLeft();
    BrewingCauldronItem = BrewingCauldronPair.getRight();

    var BrewingCauldronCopperPair = register(
        "cauldron_copper",
        BrewingCauldronCopper::new,
        Block.Settings
            .copy(Blocks.CAULDRON)
            .mapColor(MapColor.GRAY)
            .requiresTool()
            .nonOpaque()
            .ticksRandomly()
            .luminance(state -> state.get(faceless.artent.potions.block.BrewingCauldron.IS_BURNING) ? 15 : 0)
            .strength(2f),
        ModItemGroups.Potions);
    BrewingCauldronCopper = BrewingCauldronCopperPair.getLeft();
    BrewingCauldronCopperItem = BrewingCauldronCopperPair.getRight();

    var CauldronFluidPair = register(
        "cauldron_fluid",
        Block::new,
        Block.Settings.copy(Blocks.WATER).mapColor(MapColor.GRAY).nonOpaque().dropsNothing(),
        null);
    CauldronFluid = CauldronFluidPair.getLeft();
    CauldronFluidItem = CauldronFluidPair.getRight();

    var ShroomPair = register(
        "shroom",
        faceless.artent.potions.block.Shroom::new,
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
        ModItemGroups.Potions,
        faceless.artent.potions.item.ShroomItem::new
                             );
    Shroom = ShroomPair.getLeft();
    ShroomItem = ShroomPair.getRight();

    var ShadowveilPair = register(
        "shadowveil",
        (settings) -> new FlowerBlock(StatusEffects.BAD_OMEN, 5, settings),
        Block.Settings.copy(Blocks.ALLIUM).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS),
        ModItemGroups.Potions);
    Shadowveil = ShadowveilPair.getLeft();
    ShadowveilItem = ShadowveilPair.getRight();

    var BlazingMarigoldPair = register(
        "blazing_marigold",
        (settings) -> new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 5, settings),
        Block.Settings
            .copy(Blocks.ALLIUM)
            .nonOpaque()
            .luminance((state) -> 5)
            .noCollision()
            .breakInstantly()
            .sounds(BlockSoundGroup.GRASS),
        ModItemGroups.Potions);
    BlazingMarigold = BlazingMarigoldPair.getLeft();
    BlazingMarigoldItem = BlazingMarigoldPair.getRight();

    var SlimeBerryPair = register(
        "slime_berry",
        (settings) -> new FlowerBlock(StatusEffects.NAUSEA, 5, settings),
        Block.Settings.copy(Blocks.ALLIUM).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.SLIME),
        ModItemGroups.Potions);
    SlimeBerry = SlimeBerryPair.getLeft();
    SlimeBerryItem = SlimeBerryPair.getRight();

    berryBush = new Block[4];
    berryBushItem = new Item[4];
    for (int type = 0; type < berryBush.length; type++) {
      int finalType = type;
      var berryBushPair = register(
          Ingredients.GetBerryName(type) + "_bush",
          (settings) -> new BerryBush(finalType, settings),
          Block.Settings.copy(Blocks.ACACIA_LEAVES).mapColor(MapColor.GREEN).nonOpaque().sounds(BlockSoundGroup.GRASS),
          ModItemGroups.Potions);
      berryBush[type] = berryBushPair.getLeft();
      berryBushItem[type] = berryBushPair.getRight();
    }

    var CrimsonwoodLogPair = register(
        "crimsonwood_log",
        PillarBlock::new,
        Block.Settings
            .copy(Blocks.OAK_LOG)
            .mapColor(MapColor.SPRUCE_BROWN)
            .strength(2.0f)
            .sounds(BlockSoundGroup.WOOD)
            .burnable(),
        ModItemGroups.Potions);
    CrimsonwoodLog = CrimsonwoodLogPair.getLeft();
    CrimsonwoodLogItem = CrimsonwoodLogPair.getRight();

    var CrimsonwoodLeavesPair = register(
        "crimsonwood_leaves",
        CrimsonwoodLeaves::new,
        Blocks.createLeavesSettings(BlockSoundGroup.GRASS),
        ModItemGroups.Potions);
    CrimsonwoodLeaves = CrimsonwoodLeavesPair.getLeft();
    CrimsonwoodLeavesItem = CrimsonwoodLeavesPair.getRight();

    var CrimsonwoodPlanksPair = register(
        "crimsonwood_planks",
        Block::new,
        Block.Settings
            .copy(Blocks.OAK_PLANKS)
            .mapColor(MapColor.BROWN)
            .strength(2.0f, 3.0f)
            .sounds(BlockSoundGroup.WOOD)
            .burnable(),
        ModItemGroups.Potions);
    CrimsonwoodPlanks = CrimsonwoodPlanksPair.getLeft();
    CrimsonwoodPlanksItem = CrimsonwoodPlanksPair.getRight();

    var crimsonSaplingGenerator = new SaplingGenerator(
        ArtentPotions.MODID + "_crimsonwood",
        Optional.of(FeatureRegistry.CRIMSON_MEGA_TREE_CONFIGURED_KEY),
        Optional.of(FeatureRegistry.CRIMSON_TREE_CONFIGURED_KEY),
        Optional.empty());

    var CrimsonwoodSaplingPair = register(
        "crimsonwood_sapling",
        (settings) -> new CrimsonwoodSapling(crimsonSaplingGenerator, settings),
        Block.Settings
            .copy(Blocks.OAK_SAPLING)
            .noCollision()
            .ticksRandomly()
            .breakInstantly()
            .sounds(BlockSoundGroup.GRASS)
            .burnable(),
        ModItemGroups.Potions);
    CrimsonwoodSapling = CrimsonwoodSaplingPair.getLeft();
    CrimsonwoodSaplingItem = CrimsonwoodSaplingPair.getRight();

    var FermentingBarrelPair = register(
        "fermenting_barrel",
        FermentingBarrel::new,
        Block.Settings
            .copy(Blocks.OAK_PLANKS)
            .mapColor(MapColor.BROWN)
            .nonOpaque()
            .strength(2.0f, 3.0f)
            .sounds(BlockSoundGroup.WOOD),
        ModItemGroups.Potions);
    FermentingBarrel = FermentingBarrelPair.getLeft();
    FermentingBarrelItem = FermentingBarrelPair.getRight();

    var DryingRackPair = register(
        "drying_rack",
        DryingRack::new,
        Block.Settings
            .copy(Blocks.OAK_PLANKS)
            .mapColor(MapColor.BROWN)
            .nonOpaque()
            .strength(2.0f, 3.0f)
            .sounds(BlockSoundGroup.WOOD),
        ModItemGroups.Potions);
    DryingRack = DryingRackPair.getLeft();
    DryingRackItem = DryingRackPair.getRight();

    var FrostPumpkinPair = register(
        BlockKeys.FROST_PUMPKIN.getValue().getPath(),
        FrostPumpkin::new,
        Block.Settings
            .copy(Blocks.PUMPKIN)
            .mapColor(MapColor.CYAN)
            .nonOpaque(),
        ModItemGroups.Potions);
    FrostPumpkin = FrostPumpkinPair.getLeft();
    FrostPumpkinItem = FrostPumpkinPair.getRight();

    FrostPumpkinStem = registerBlock(
        BlockKeys.FROST_PUMPKIN_STEM.getValue().getPath(),
        (settings) -> new StemBlock(
            BlockKeys.FROST_PUMPKIN,
            BlockKeys.FROST_PUMPKIN_STEM_ATTACHED,
            ItemKeys.FROST_PUMPKIN_SEEDS,
            settings),
        Block.Settings
            .copy(Blocks.PUMPKIN_STEM));

    FrostPumpkinStemAttached = registerBlock(
        BlockKeys.FROST_PUMPKIN_STEM_ATTACHED.getValue().getPath(),
        (settings) -> new AttachedStemBlock(
            BlockKeys.FROST_PUMPKIN_STEM,
            BlockKeys.FROST_PUMPKIN,
            ItemKeys.FROST_PUMPKIN_SEEDS,
            settings),
        Block.Settings
            .copy(Blocks.ATTACHED_PUMPKIN_STEM));

    var IceCrystalBlockPair = register(
        "ice_crystal_block",
        Block::new,
        Block.Settings
            .copy(Blocks.AMETHYST_BLOCK)
            .mapColor(MapColor.CYAN)
            .nonOpaque(),
        ModItemGroups.Potions);
    IceCrystalBlock = IceCrystalBlockPair.getLeft();
    IceCrystalBlock_Item = IceCrystalBlockPair.getRight();

    var IceCrystalBud_SmallPair = register(
        "ice_crystal_bud_small",
        (settings) -> new IceCrystalCluster(3.0f, 4.0f, settings),
        Block.Settings
            .copy(Blocks.SMALL_AMETHYST_BUD)
            .mapColor(MapColor.CYAN)
            .nonOpaque(),
        ModItemGroups.Potions);
    IceCrystalBud_Small = IceCrystalBud_SmallPair.getLeft();
    IceCrystalBud_Small_Item = IceCrystalBud_SmallPair.getRight();

    var IceCrystalBud_MediumPair = register(
        "ice_crystal_bud_medium",
        (settings) -> new IceCrystalCluster(4.0f, 3.0f, settings),
        Block.Settings
            .copy(Blocks.MEDIUM_AMETHYST_BUD)
            .mapColor(MapColor.CYAN)
            .ticksRandomly()
            .nonOpaque(),
        ModItemGroups.Potions);
    IceCrystalBud_Medium = IceCrystalBud_MediumPair.getLeft();
    IceCrystalBud_Medium_Item = IceCrystalBud_MediumPair.getRight();

    var IceCrystalBud_LargePair = register(
        "ice_crystal_bud_large",
        (settings) -> new IceCrystalCluster(5.0f, 3.0f, settings),
        Block.Settings
            .copy(Blocks.LARGE_AMETHYST_BUD)
            .mapColor(MapColor.CYAN)
            .ticksRandomly()
            .nonOpaque(),
        ModItemGroups.Potions);
    IceCrystalBud_Large = IceCrystalBud_LargePair.getLeft();
    IceCrystalBud_Large_Item = IceCrystalBud_LargePair.getRight();

    var IceCrystalBud_ClusterPair = register(
        "ice_crystal_cluster",
        (settings) -> new IceCrystalCluster(7.0f, 3.0f, settings),
        Block.Settings
            .copy(Blocks.AMETHYST_CLUSTER)
            .mapColor(MapColor.CYAN)
            .ticksRandomly()
            .nonOpaque(),
        ModItemGroups.Potions);
    IceCrystalBud_Cluster = IceCrystalBud_ClusterPair.getLeft();
    IceCrystalBud_Cluster_Item = IceCrystalBud_ClusterPair.getRight();

    var BrownMushroomMyceliumPair = register(
        "brown_mushroom_mycelium",
        (settings) -> new MushroomMycelium(MushroomType.Brown, settings),
        Block.Settings
            .copy(Blocks.MYCELIUM).ticksRandomly(),
        ModItemGroups.Potions);
    var growingBrownMushroom = registerBlock(
        "brown_mushroom_stage",
        (settings) -> new GrowingMushroom(
            MushroomType.Brown,
            Blocks.BROWN_MUSHROOM,
            () -> ModItems.BrownMushroomSpores,
            settings),
        Block.Settings
            .copy(Blocks.BROWN_MUSHROOM).ticksRandomly());
    MushroomInfo[MushroomType.Brown.ordinal()] = new MushroomBlockInfo(
        BrownMushroomMyceliumPair.getLeft(),
        BrownMushroomMyceliumPair.getRight(),
        growingBrownMushroom);

    var RedMushroomMyceliumPair = register(
        "red_mushroom_mycelium",
        (settings) -> new MushroomMycelium(MushroomType.Red, settings),
        Block.Settings
            .copy(Blocks.MYCELIUM).ticksRandomly(),
        ModItemGroups.Potions);
    var growingRedMushroom = registerBlock(
        "red_mushroom_stage",
        (settings) -> new GrowingMushroom(
            MushroomType.Red,
            Blocks.RED_MUSHROOM,
            () -> ModItems.RedMushroomSpores,
            settings),
        Block.Settings
            .copy(Blocks.RED_MUSHROOM).ticksRandomly());
    MushroomInfo[MushroomType.Red.ordinal()] = new MushroomBlockInfo(
        RedMushroomMyceliumPair.getLeft(),
        RedMushroomMyceliumPair.getRight(),
        growingRedMushroom);

    var ShroomMyceliumPair = register(
        "shroom_mycelium",
        (settings) -> new MushroomMycelium(MushroomType.Shroom, settings),
        Block.Settings
            .copy(Blocks.MYCELIUM).ticksRandomly(),
        ModItemGroups.Potions);
    var growingShroomMushroom = registerBlock(
        "shroom_stage",
        (settings) -> new GrowingMushroom(
            MushroomType.Shroom,
            ModBlocks.Shroom,
            () -> ModItems.ShroomSpores,
            settings),
        Block.Settings
            .copy(Blocks.RED_MUSHROOM).ticksRandomly());
    MushroomInfo[MushroomType.Shroom.ordinal()] = new MushroomBlockInfo(
        ShroomMyceliumPair.getLeft(),
        ShroomMyceliumPair.getRight(),
        growingShroomMushroom);

    FireBlock fireBlock = (FireBlock) Blocks.FIRE;
    fireBlock.registerFlammableBlock(CrimsonwoodLog, 5, 20);
    fireBlock.registerFlammableBlock(CrimsonwoodPlanks, 5, 20);
    fireBlock.registerFlammableBlock(CrimsonwoodLeaves, 30, 60);
    fireBlock.registerFlammableBlock(CrimsonwoodSapling, 30, 60);
  }

  public static <TBlock extends Block> Pair<TBlock, Item> register(
      String keyString,
      Function<AbstractBlock.Settings, TBlock> factory,
      AbstractBlock.Settings settings,
      ArtentItemGroupBuilder groupBuilder) {
    var key = keyOf(keyString);
    TBlock block = factory.apply(settings.registryKey(key));
    Registry.register(Registries.BLOCK, key, block);

    var itemKey = keyOf(key);
    var blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
    Registry.register(Registries.ITEM, itemKey, blockItem);
    if (groupBuilder != null) groupBuilder.addItem(blockItem);
    return new Pair<>(block, blockItem);
  }

  public static <TBlock extends Block> TBlock registerBlock(
      String keyString,
      Function<AbstractBlock.Settings, TBlock> factory,
      AbstractBlock.Settings settings) {
    var key = keyOf(keyString);
    TBlock block = factory.apply(settings.registryKey(key));
    Registry.register(Registries.BLOCK, key, block);
    return block;
  }

  public static Pair<Block, Item> register(
      String keyString,
      Function<AbstractBlock.Settings, Block> factory,
      AbstractBlock.Settings settings,
      ArtentItemGroupBuilder groupBuilder,
      BiFunction<Block, Item.Settings, BlockItem> customBlockItem) {
    var key = keyOf(keyString);
    Block block = factory.apply(settings.registryKey(key));
    Registry.register(Registries.BLOCK, key, block);

    var itemKey = keyOf(key);
    var blockItem = customBlockItem.apply(block, new Item.Settings().registryKey(itemKey));
    Registry.register(Registries.ITEM, itemKey, blockItem);
    if (groupBuilder != null) groupBuilder.addItem(blockItem);
    return new Pair<>(block, blockItem);
  }

  private static RegistryKey<Block> keyOf(String id) {
    return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(ArtentPotions.MODID, id));
  }

  private static RegistryKey<Item> keyOf(RegistryKey<Block> blockKey) {
    return RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());
  }
}
