package com.dschaedler.minebetter;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;

public class MineBetter implements ModInitializer {

	// Petrified Log
	public static final PillarBlock PETRIFIED_LOG_BLOCK = new PillarBlock(
		FabricBlockSettings.of(Material.WOOD).hardness(2.0f));

	// Glass Trapdoor
	public static final MBTrapdoor GLASS_TRAPDOOR_BLOCK = new MBTrapdoor(
		FabricBlockSettings.of(Material.GLASS).hardness(0.3f));

	// --------

	// ConfiguredFeature for Spawning Petrified Logs
	private static ConfiguredFeature<?, ?> ORE_PETRIFIED_LOG = Feature.ORE
		.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
				PETRIFIED_LOG_BLOCK.getDefaultState(), 5)) // Vein Size
		.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig( // Height Settings
			0, // bottomOffset,
			5, // topOffset,
			32 // maximum
		))).spreadHorizontally().repeat(10); // Veins per Chunk

	// ConfiguredFeature for Spawning Silverfish Egg BLocks
	private static ConfiguredFeature<?, ?> ORE_SILVERFISH_EGG = Feature.ORE
		.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
				Blocks.INFESTED_STONE.getDefaultState(), 9)) // Vein Size
		.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig( // Height Settings
			0, // bottomOffset,
			5, // topOffset,
			63 // maximum
		))).spreadHorizontally().repeat(7); // Veins per Chunk

	// --------

	// Create the itemgroup / creative tab for the mod
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
		new Identifier("minebetter", "itemgroup"),
		() -> new ItemStack(GLASS_TRAPDOOR_BLOCK));

	// --------	

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		
		// --------

		// Say Hi!
		System.out.println("[MineBetter] Hello from MineBetter!");
		
		// --------

		// Register Blocks
		Registry.register(Registry.BLOCK, new Identifier("minebetter", "petrified_log"), PETRIFIED_LOG_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("minebetter", "petrified_log"), new BlockItem(PETRIFIED_LOG_BLOCK, new Item.Settings().group(MineBetter.ITEM_GROUP)));

		Registry.register(Registry.BLOCK, new Identifier("minebetter", "glass_trapdoor"), GLASS_TRAPDOOR_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("minebetter", "glass_trapdoor"), new BlockItem(GLASS_TRAPDOOR_BLOCK, new Item.Settings().group(MineBetter.ITEM_GROUP)));

		// --------

		// Make the Glass Trapdoor transparent
		// This works for now, but lets make sure this ends up client side only, yeah?
		BlockRenderLayerMap.INSTANCE.putBlock(MineBetter.GLASS_TRAPDOOR_BLOCK, RenderLayer.getCutout());

		// --------

		// Spawn Petrified Logs with Configured Feature
		// Register the ConfiguredFeature
		RegistryKey<ConfiguredFeature<?, ?>> orePetrifiedLog = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN,
			new Identifier("minebetter", "ore_petrified_log"));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, orePetrifiedLog.getValue(), ORE_PETRIFIED_LOG);

		//Spawn Logs
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, orePetrifiedLog);

		// Spawn Veins of Silverfish blocks
		RegistryKey<ConfiguredFeature<?, ?>> oreSilverfishEgg = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN,
			new Identifier("minebetter", "ore_silverfish_egg"));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, oreSilverfishEgg.getValue(), ORE_SILVERFISH_EGG);

		//Spawn Silverfish
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, oreSilverfishEgg);
	}
}
