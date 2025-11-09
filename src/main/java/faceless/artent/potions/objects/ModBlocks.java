package faceless.artent.potions.objects;

import faceless.artent.core.item.group.ArtentItemGroupBuilder;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.api.MushroomBlockInfo;
import faceless.artent.potions.api.MushroomType;
import faceless.artent.potions.api.RegisteredBlock;
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

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class ModBlocks {
  public static RegisteredBlock<?> BrewingCauldron;
  public static RegisteredBlock<?> BrewingCauldronCopper;
  public static RegisteredBlock<?> CauldronFluid;
  public static RegisteredBlock<?> FermentingBarrel;

  public static RegisteredBlock<?> Shroom;
  public static RegisteredBlock<?> Shadowveil;
  public static RegisteredBlock<?> BlazingMarigold;
  public static RegisteredBlock<?> SlimeBerry;

  public static RegisteredBlock<?>[] berryBush;

  public static RegisteredBlock<?> CrimsonwoodLog;

  public static RegisteredBlock<?> CrimsonwoodLeaves;
  public static RegisteredBlock<?> CrimsonwoodPlanks;
  public static RegisteredBlock<?> CrimsonwoodSapling;

  public static RegisteredBlock<?> DryingRack;

  public static RegisteredBlock<?> FrostPumpkinCarved;
  public static RegisteredBlock<?> FrostPumpkin;
  public static RegisteredBlock<?> FrostPumpkinStem;
  public static RegisteredBlock<?> FrostPumpkinStemAttached;

  public static RegisteredBlock<?> IceCrystalBlock;
  public static RegisteredBlock<IceCrystalCluster> IceCrystalBud_Small;
  public static RegisteredBlock<IceCrystalCluster> IceCrystalBud_Medium;
  public static RegisteredBlock<IceCrystalCluster> IceCrystalBud_Large;
  public static RegisteredBlock<IceCrystalCluster> IceCrystalBud_Cluster;

  public static MushroomBlockInfo[] MushroomInfo = new MushroomBlockInfo[3];

  public void register() {
    BrewingCauldron = register(
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

    BrewingCauldronCopper = register(
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

    CauldronFluid = register(
        "cauldron_fluid",
        Block::new,
        Block.Settings.copy(Blocks.WATER).mapColor(MapColor.GRAY).nonOpaque().dropsNothing(),
        null);

    Shroom = register(
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
        faceless.artent.potions.item.ShroomItem::new);

    Shadowveil = register(
        "shadowveil",
        (settings) -> new FlowerBlock(StatusEffects.BAD_OMEN, 5, settings),
        Block.Settings.copy(Blocks.ALLIUM).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS),
        ModItemGroups.Potions);

    BlazingMarigold = register(
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

    SlimeBerry = register(
        "slime_berry",
        (settings) -> new FlowerBlock(StatusEffects.NAUSEA, 5, settings),
        Block.Settings.copy(Blocks.ALLIUM).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.SLIME),
        ModItemGroups.Potions);

    berryBush = new RegisteredBlock[4];
    for (int type = 0; type < berryBush.length; type++) {
      int finalType = type;
      berryBush[type] = register(
          Ingredients.GetBerryName(type) + "_bush",
          (settings) -> new BerryBush(finalType, settings),
          Block.Settings.copy(Blocks.ACACIA_LEAVES).mapColor(MapColor.GREEN).nonOpaque().sounds(BlockSoundGroup.GRASS),
          ModItemGroups.Potions);
    }

    CrimsonwoodLog = register(
        "crimsonwood_log",
        PillarBlock::new,
        Block.Settings
            .copy(Blocks.OAK_LOG)
            .mapColor(MapColor.SPRUCE_BROWN)
            .strength(2.0f)
            .sounds(BlockSoundGroup.WOOD)
            .burnable(),
        ModItemGroups.Potions);

    CrimsonwoodLeaves = register(
        "crimsonwood_leaves",
        CrimsonwoodLeaves::new,
        Blocks.createLeavesSettings(BlockSoundGroup.GRASS),
        ModItemGroups.Potions);

    CrimsonwoodPlanks = register(
        "crimsonwood_planks",
        Block::new,
        Block.Settings
            .copy(Blocks.OAK_PLANKS)
            .mapColor(MapColor.BROWN)
            .strength(2.0f, 3.0f)
            .sounds(BlockSoundGroup.WOOD)
            .burnable(),
        ModItemGroups.Potions);

    var crimsonSaplingGenerator = new SaplingGenerator(
        ArtentPotions.MODID + "_crimsonwood",
        Optional.of(FeatureRegistry.CRIMSON_MEGA_TREE_CONFIGURED_KEY),
        Optional.of(FeatureRegistry.CRIMSON_TREE_CONFIGURED_KEY),
        Optional.empty());

    CrimsonwoodSapling = register(
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

    FermentingBarrel = register(
        "fermenting_barrel",
        FermentingBarrel::new,
        Block.Settings
            .copy(Blocks.OAK_PLANKS)
            .mapColor(MapColor.BROWN)
            .nonOpaque()
            .strength(2.0f, 3.0f)
            .sounds(BlockSoundGroup.WOOD),
        ModItemGroups.Potions);

    DryingRack = register(
        "drying_rack",
        DryingRack::new,
        Block.Settings
            .copy(Blocks.OAK_PLANKS)
            .mapColor(MapColor.BROWN)
            .nonOpaque()
            .strength(2.0f, 3.0f)
            .sounds(BlockSoundGroup.WOOD),
        ModItemGroups.Potions);

    FrostPumpkinCarved = register(
        BlockKeys.FROST_PUMPKIN_CARVED.getValue().getPath(),
        FrostPumpkinCarved::new,
        Block.Settings.copy(Blocks.CARVED_PUMPKIN).mapColor(MapColor.CYAN).nonOpaque(),
        ModItemGroups.Potions);

    FrostPumpkin = register(
        BlockKeys.FROST_PUMPKIN.getValue().getPath(),
        FrostPumpkin::new,
        Block.Settings.copy(Blocks.PUMPKIN).mapColor(MapColor.CYAN).nonOpaque(),
        ModItemGroups.Potions);

    FrostPumpkinStem = registerBlock(
        BlockKeys.FROST_PUMPKIN_STEM.getValue().getPath(),
        (settings) -> new StemBlock(
            BlockKeys.FROST_PUMPKIN,
            BlockKeys.FROST_PUMPKIN_STEM_ATTACHED,
            ItemKeys.FROST_PUMPKIN_SEEDS,
            settings),
        Block.Settings.copy(Blocks.PUMPKIN_STEM));

    FrostPumpkinStemAttached = registerBlock(
        BlockKeys.FROST_PUMPKIN_STEM_ATTACHED.getValue().getPath(),
        (settings) -> new AttachedStemBlock(
            BlockKeys.FROST_PUMPKIN_STEM,
            BlockKeys.FROST_PUMPKIN,
            ItemKeys.FROST_PUMPKIN_SEEDS,
            settings),
        Block.Settings.copy(Blocks.ATTACHED_PUMPKIN_STEM));

    IceCrystalBlock = register(
        "ice_crystal_block",
        Block::new,
        Block.Settings.copy(Blocks.AMETHYST_BLOCK).mapColor(MapColor.CYAN).nonOpaque(),
        ModItemGroups.Potions);

    IceCrystalBud_Small = register(
        "ice_crystal_bud_small",
        (settings) -> new IceCrystalCluster(3.0f, 4.0f, settings),
        Block.Settings.copy(Blocks.SMALL_AMETHYST_BUD).mapColor(MapColor.CYAN).nonOpaque(),
        ModItemGroups.Potions);

    IceCrystalBud_Medium = register(
        "ice_crystal_bud_medium",
        (settings) -> new IceCrystalCluster(4.0f, 3.0f, settings),
        Block.Settings.copy(Blocks.MEDIUM_AMETHYST_BUD).mapColor(MapColor.CYAN).ticksRandomly().nonOpaque(),
        ModItemGroups.Potions);

    IceCrystalBud_Large = register(
        "ice_crystal_bud_large",
        (settings) -> new IceCrystalCluster(5.0f, 3.0f, settings),
        Block.Settings.copy(Blocks.LARGE_AMETHYST_BUD).mapColor(MapColor.CYAN).ticksRandomly().nonOpaque(),
        ModItemGroups.Potions);

    IceCrystalBud_Cluster = register(
        "ice_crystal_cluster",
        (settings) -> new IceCrystalCluster(7.0f, 3.0f, settings),
        Block.Settings.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.CYAN).ticksRandomly().nonOpaque(),
        ModItemGroups.Potions);

    var BrownMushroomMycelium = register(
        "brown_mushroom_mycelium",
        (settings) -> new MushroomMycelium(MushroomType.Brown, settings),
        Block.Settings.copy(Blocks.MYCELIUM).ticksRandomly(),
        ModItemGroups.Potions);
    var growingBrownMushroom = registerBlock(
        "brown_mushroom_stage",
        (settings) -> new GrowingMushroom(
            MushroomType.Brown,
            Blocks.BROWN_MUSHROOM,
            () -> ModItems.BrownMushroomSpores,
            settings),
        Block.Settings.copy(Blocks.BROWN_MUSHROOM).ticksRandomly());
    MushroomInfo[MushroomType.Brown.ordinal()] = new MushroomBlockInfo(
        BrownMushroomMycelium.block(),
        growingBrownMushroom.block());

    var RedMushroomMycelium = register(
        "red_mushroom_mycelium",
        (settings) -> new MushroomMycelium(MushroomType.Red, settings),
        Block.Settings.copy(Blocks.MYCELIUM).ticksRandomly(),
        ModItemGroups.Potions);
    var growingRedMushroom = registerBlock(
        "red_mushroom_stage",
        (settings) -> new GrowingMushroom(
            MushroomType.Red,
            Blocks.RED_MUSHROOM,
            () -> ModItems.RedMushroomSpores,
            settings),
        Block.Settings.copy(Blocks.RED_MUSHROOM).ticksRandomly());
    MushroomInfo[MushroomType.Red.ordinal()] = new MushroomBlockInfo(
        RedMushroomMycelium.block(),
        growingRedMushroom.block());

    var ShroomMycelium = register(
        "shroom_mycelium",
        (settings) -> new MushroomMycelium(MushroomType.Shroom, settings),
        Block.Settings.copy(Blocks.MYCELIUM).ticksRandomly(),
        ModItemGroups.Potions);
    var growingShroomMushroom = registerBlock(
        "shroom_stage",
        (settings) -> new GrowingMushroom(
            MushroomType.Shroom,
            ModBlocks.Shroom.block(),
            () -> ModItems.ShroomSpores,
            settings),
        Block.Settings.copy(Blocks.RED_MUSHROOM).ticksRandomly());
    MushroomInfo[MushroomType.Shroom.ordinal()] = new MushroomBlockInfo(
        ShroomMycelium.block(),
        growingShroomMushroom.block());

    FireBlock fireBlock = (FireBlock) Blocks.FIRE;
    fireBlock.registerFlammableBlock(CrimsonwoodLog.block(), 5, 20);
    fireBlock.registerFlammableBlock(CrimsonwoodPlanks.block(), 5, 20);
    fireBlock.registerFlammableBlock(CrimsonwoodLeaves.block(), 30, 60);
    fireBlock.registerFlammableBlock(CrimsonwoodSapling.block(), 30, 60);
  }

  public static <TBlock extends Block> RegisteredBlock<TBlock> register(
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
    return new RegisteredBlock<>(block, blockItem);
  }

  public static <TBlock extends Block> RegisteredBlock<TBlock> registerBlock(
      String keyString,
      Function<AbstractBlock.Settings, TBlock> factory,
      AbstractBlock.Settings settings) {
    var key = keyOf(keyString);
    TBlock block = factory.apply(settings.registryKey(key));
    Registry.register(Registries.BLOCK, key, block);
    return new RegisteredBlock<>(block, null);
  }

  public static RegisteredBlock<Block> register(
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
    return new RegisteredBlock<>(block, blockItem);
  }

  private static RegistryKey<Block> keyOf(String id) {
    return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(ArtentPotions.MODID, id));
  }

  private static RegistryKey<Item> keyOf(RegistryKey<Block> blockKey) {
    return RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());
  }
}
