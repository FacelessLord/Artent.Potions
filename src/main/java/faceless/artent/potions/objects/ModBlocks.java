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
import net.minecraft.block.piston.PistonBehavior;
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
  public static RegisteredBlock<?> BREWING_CAULDRON;
  public static RegisteredBlock<?> BREWING_CAULDRON_COPPER;
  public static RegisteredBlock<?> CAULDRON_FLUID;
  public static RegisteredBlock<?> FEMENTING_BARREL;

  public static RegisteredBlock<?> SHROOM;
  public static RegisteredBlock<?> SHADOWVEIL;
  public static RegisteredBlock<?> BLAZING_MARIGOLD;
  public static RegisteredBlock<?> SLIME_BERRY;

  public static RegisteredBlock<?>[] BERRY_BUSH;

  public static RegisteredBlock<?> CRIMSONWOOD_LOG;

  public static RegisteredBlock<?> CRIMSONWOOD_LEAVES;
  public static RegisteredBlock<?> CRIMSONWOOD_PLANKS;
  public static RegisteredBlock<?> CRIMSONWOOD_SAPLING;

  public static RegisteredBlock<?> DRYING_RACK;

  public static RegisteredBlock<?> FROST_PUMPKIN_CARVED;
  public static RegisteredBlock<?> FROST_PUMPKIN;
  public static RegisteredBlock<?> FROST_PUMPKIN_STEM;
  public static RegisteredBlock<?> FROST_PUMPKIN_STEM_ATTACHED;

  public static RegisteredBlock<?> BLAZING_MARIGOLD_CROPS;
  public static RegisteredBlock<?> SHADOWVEIL_CROPS;
  public static RegisteredBlock<?> SLIME_BERRY_CROPS;

  public static RegisteredBlock<?> ICE_CRYSTAL_BLOCK;
  public static RegisteredBlock<IceCrystalCluster> ICE_CRYSTAL_BUD_SMALL;
  public static RegisteredBlock<IceCrystalCluster> ICE_CRYSTAL_BUD_MEDIUM;
  public static RegisteredBlock<IceCrystalCluster> ICE_CRYSTAL_BUD_LARGE;
  public static RegisteredBlock<IceCrystalCluster> ICE_CRYSTAL_CLUSTER;

  public static MushroomBlockInfo[] MUSHROOM_INFO = new MushroomBlockInfo[3];

  public void register() {
    BREWING_CAULDRON = register(
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
        ModItemGroups.BASE);

    BREWING_CAULDRON_COPPER = register(
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
        ModItemGroups.BASE);

    CAULDRON_FLUID = register(
        "cauldron_fluid",
        Block::new,
        Block.Settings.copy(Blocks.WATER).mapColor(MapColor.GRAY).nonOpaque().dropsNothing(),
        null);

    SHROOM = register(
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
        ModItemGroups.BASE,
        faceless.artent.potions.item.ShroomItem::new);

    SHADOWVEIL = register(
        "shadowveil",
        (settings) -> new FlowerBlock(StatusEffects.BAD_OMEN, 5, settings),
        Block.Settings
            .copy(Blocks.ALLIUM)
            .offset(AbstractBlock.OffsetType.NONE)
            .nonOpaque()
            .noCollision()
            .breakInstantly()
            .sounds(BlockSoundGroup.GRASS),
        ModItemGroups.BASE);

    BLAZING_MARIGOLD = register(
        "blazing_marigold",
        (settings) -> new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 5, settings),
        Block.Settings
            .copy(Blocks.ALLIUM)
            .nonOpaque()
            .offset(AbstractBlock.OffsetType.NONE)
            .luminance((state) -> 11)
            .noCollision()
            .breakInstantly()
            .sounds(BlockSoundGroup.GRASS),
        ModItemGroups.BASE);

    SLIME_BERRY = register(
        "slime_berry",
        (settings) -> new FlowerBlock(StatusEffects.NAUSEA, 5, settings),
        Block.Settings
            .copy(Blocks.ALLIUM)
            .offset(AbstractBlock.OffsetType.NONE)
            .nonOpaque()
            .noCollision()
            .breakInstantly()
            .sounds(BlockSoundGroup.SLIME),
        ModItemGroups.BASE);

    BERRY_BUSH = new RegisteredBlock[4];
    for (int type = 0; type < BERRY_BUSH.length; type++) {
      int finalType = type;
      BERRY_BUSH[type] = register(
          Ingredients.GetBerryName(type) + "_bush",
          (settings) -> new BerryBush(finalType, settings),
          Block.Settings.copy(Blocks.ACACIA_LEAVES).mapColor(MapColor.GREEN).nonOpaque().sounds(BlockSoundGroup.GRASS),
          ModItemGroups.BASE);
    }

    CRIMSONWOOD_LOG = register(
        "crimsonwood_log",
        PillarBlock::new,
        Block.Settings
            .copy(Blocks.OAK_LOG)
            .mapColor(MapColor.SPRUCE_BROWN)
            .strength(2.0f)
            .sounds(BlockSoundGroup.WOOD)
            .burnable(),
        ModItemGroups.BASE);

    CRIMSONWOOD_LEAVES = register(
        "crimsonwood_leaves",
        CrimsonwoodLeaves::new,
        Blocks.createLeavesSettings(BlockSoundGroup.GRASS),
        ModItemGroups.BASE);

    CRIMSONWOOD_PLANKS = register(
        "crimsonwood_planks",
        Block::new,
        Block.Settings
            .copy(Blocks.OAK_PLANKS)
            .mapColor(MapColor.BROWN)
            .strength(2.0f, 3.0f)
            .sounds(BlockSoundGroup.WOOD)
            .burnable(),
        ModItemGroups.BASE);

    var crimsonSaplingGenerator = new SaplingGenerator(
        ArtentPotions.MODID + "_crimsonwood",
        Optional.of(FeatureRegistry.CRIMSON_MEGA_TREE_CONFIGURED_KEY),
        Optional.of(FeatureRegistry.CRIMSON_TREE_CONFIGURED_KEY),
        Optional.empty());

    CRIMSONWOOD_SAPLING = register(
        "crimsonwood_sapling",
        (settings) -> new CrimsonwoodSapling(crimsonSaplingGenerator, settings),
        Block.Settings
            .copy(Blocks.OAK_SAPLING)
            .noCollision()
            .ticksRandomly()
            .breakInstantly()
            .sounds(BlockSoundGroup.GRASS)
            .burnable(),
        ModItemGroups.BASE);

    FEMENTING_BARREL = register(
        "fermenting_barrel",
        FermentingBarrel::new,
        Block.Settings
            .copy(Blocks.OAK_PLANKS)
            .mapColor(MapColor.BROWN)
            .nonOpaque()
            .strength(2.0f, 3.0f)
            .sounds(BlockSoundGroup.WOOD),
        ModItemGroups.BASE);

    DRYING_RACK = register(
        "drying_rack",
        DryingRack::new,
        Block.Settings
            .copy(Blocks.OAK_PLANKS)
            .mapColor(MapColor.BROWN)
            .nonOpaque()
            .strength(2.0f, 3.0f)
            .sounds(BlockSoundGroup.WOOD),
        ModItemGroups.BASE);

    FROST_PUMPKIN_CARVED = register(
        BlockKeys.FROST_PUMPKIN_CARVED.getValue().getPath(),
        FrostPumpkinCarved::new,
        Block.Settings.copy(Blocks.CARVED_PUMPKIN).mapColor(MapColor.CYAN).nonOpaque(),
        ModItemGroups.BASE);

    FROST_PUMPKIN = register(
        BlockKeys.FROST_PUMPKIN.getValue().getPath(),
        FrostPumpkin::new,
        Block.Settings.copy(Blocks.PUMPKIN).mapColor(MapColor.CYAN).nonOpaque(),
        ModItemGroups.BASE);

    FROST_PUMPKIN_STEM = registerBlock(
        BlockKeys.FROST_PUMPKIN_STEM.getValue().getPath(),
        (settings) -> new StemBlock(
            BlockKeys.FROST_PUMPKIN,
            BlockKeys.FROST_PUMPKIN_STEM_ATTACHED,
            ItemKeys.FROST_PUMPKIN_SEEDS,
            settings),
        Block.Settings.copy(Blocks.PUMPKIN_STEM));
    FROST_PUMPKIN_STEM_ATTACHED = registerBlock(
        BlockKeys.FROST_PUMPKIN_STEM_ATTACHED.getValue().getPath(),
        (settings) -> new AttachedStemBlock(
            BlockKeys.FROST_PUMPKIN_STEM,
            BlockKeys.FROST_PUMPKIN,
            ItemKeys.FROST_PUMPKIN_SEEDS,
            settings),
        Block.Settings.copy(Blocks.ATTACHED_PUMPKIN_STEM));

    BLAZING_MARIGOLD_CROPS = registerBlock(
        BlockKeys.BLAZING_MARIGOLD_CROPS.getValue().getPath(),
        (settings) -> new FlowerCropBlock(
            4,
            ModBlocks.BLAZING_MARIGOLD.block(),
            () -> ModItems.BLAZING_MARIGOLD_SEEDS,
            settings),
        Block.Settings
            .copy(Blocks.BROWN_MUSHROOM)
            .ticksRandomly()
            .sounds(BlockSoundGroup.CROP)
            .mapColor(MapColor.GREEN));

    SHADOWVEIL_CROPS = registerBlock(
        BlockKeys.SHADOWVEIL_CROPS.getValue().getPath(),
        (settings) -> new FlowerCropBlock(3, ModBlocks.SHADOWVEIL.block(), () -> ModItems.SHADOWVEIL_SEEDS, settings),
        Block.Settings
            .copy(Blocks.BROWN_MUSHROOM)
            .ticksRandomly()
            .sounds(BlockSoundGroup.CROP)
            .mapColor(MapColor.GREEN));

    SLIME_BERRY_CROPS = registerBlock(
        BlockKeys.SLIME_BERRY_CROPS.getValue().getPath(),
        (settings) -> new FlowerCropBlock(3, ModBlocks.SLIME_BERRY.block(), () -> ModItems.SLIME_BERRY_SEEDS, settings),
        Block.Settings
            .copy(Blocks.BROWN_MUSHROOM)
            .ticksRandomly()
            .sounds(BlockSoundGroup.CROP)
            .mapColor(MapColor.GREEN));

    ICE_CRYSTAL_BLOCK = register(
        "ice_crystal_block",
        Block::new,
        Block.Settings.copy(Blocks.AMETHYST_BLOCK).mapColor(MapColor.CYAN).nonOpaque(),
        ModItemGroups.BASE);

    ICE_CRYSTAL_BUD_SMALL = register(
        "ice_crystal_bud_small",
        (settings) -> new IceCrystalCluster(3.0f, 4.0f, settings),
        Block.Settings.copy(Blocks.SMALL_AMETHYST_BUD).mapColor(MapColor.CYAN).nonOpaque(),
        ModItemGroups.BASE);

    ICE_CRYSTAL_BUD_MEDIUM = register(
        "ice_crystal_bud_medium",
        (settings) -> new IceCrystalCluster(4.0f, 3.0f, settings),
        Block.Settings.copy(Blocks.MEDIUM_AMETHYST_BUD).mapColor(MapColor.CYAN).ticksRandomly().nonOpaque(),
        ModItemGroups.BASE);

    ICE_CRYSTAL_BUD_LARGE = register(
        "ice_crystal_bud_large",
        (settings) -> new IceCrystalCluster(5.0f, 3.0f, settings),
        Block.Settings.copy(Blocks.LARGE_AMETHYST_BUD).mapColor(MapColor.CYAN).ticksRandomly().nonOpaque(),
        ModItemGroups.BASE);

    ICE_CRYSTAL_CLUSTER = register(
        "ice_crystal_cluster",
        (settings) -> new IceCrystalCluster(7.0f, 3.0f, settings),
        Block.Settings.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.CYAN).ticksRandomly().nonOpaque(),
        ModItemGroups.BASE);

    var BrownMushroomMycelium = register(
        "brown_mushroom_mycelium",
        (settings) -> new MushroomMycelium(MushroomType.Brown, settings),
        Block.Settings.copy(Blocks.MYCELIUM).ticksRandomly().mapColor(MapColor.BROWN),
        ModItemGroups.BASE);
    var growingBrownMushroom = registerBlock(
        "brown_mushroom_stage",
        (settings) -> new GrowingMushroom(
            MushroomType.Brown,
            Blocks.BROWN_MUSHROOM,
            () -> ModItems.BROWN_MUSHROOM_SPORES,
            settings),
        Block.Settings.copy(Blocks.BROWN_MUSHROOM).ticksRandomly().mapColor(MapColor.BROWN));
    MUSHROOM_INFO[MushroomType.Brown.ordinal()] = new MushroomBlockInfo(
        BrownMushroomMycelium.block(),
                                                                        growingBrownMushroom.block());

    var RedMushroomMycelium = register(
        "red_mushroom_mycelium",
        (settings) -> new MushroomMycelium(MushroomType.Red, settings),
        Block.Settings.copy(Blocks.MYCELIUM).ticksRandomly().mapColor(MapColor.RED),
        ModItemGroups.BASE);
    var growingRedMushroom = registerBlock(
        "red_mushroom_stage",
        (settings) -> new GrowingMushroom(
            MushroomType.Red,
            Blocks.RED_MUSHROOM,
            () -> ModItems.RED_MUSHROOM_SPORES,
            settings),
        Block.Settings.copy(Blocks.RED_MUSHROOM).ticksRandomly());
    MUSHROOM_INFO[MushroomType.Red.ordinal()] = new MushroomBlockInfo(
        RedMushroomMycelium.block(),
                                                                      growingRedMushroom.block());

    var ShroomMycelium = register(
        "shroom_mycelium",
        (settings) -> new MushroomMycelium(MushroomType.Shroom, settings),
        Block.Settings.copy(Blocks.MYCELIUM).ticksRandomly().mapColor(MapColor.LAPIS_BLUE),
        ModItemGroups.BASE);
    var growingShroomMushroom = registerBlock(
        "shroom_stage",
        (settings) -> new GrowingMushroom(
            MushroomType.Shroom,
            ModBlocks.SHROOM.block(),
            () -> ModItems.SHROOM_SPORES,
            settings),
        Block.Settings.copy(Blocks.RED_MUSHROOM).ticksRandomly().mapColor(MapColor.LAPIS_BLUE));
    MUSHROOM_INFO[MushroomType.Shroom.ordinal()] = new MushroomBlockInfo(
        ShroomMycelium.block(),
                                                                         growingShroomMushroom.block());

    FireBlock fireBlock = (FireBlock) Blocks.FIRE;
    fireBlock.registerFlammableBlock(CRIMSONWOOD_LOG.block(), 5, 20);
    fireBlock.registerFlammableBlock(CRIMSONWOOD_PLANKS.block(), 5, 20);
    fireBlock.registerFlammableBlock(CRIMSONWOOD_LEAVES.block(), 30, 60);
    fireBlock.registerFlammableBlock(CRIMSONWOOD_SAPLING.block(), 30, 60);
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
