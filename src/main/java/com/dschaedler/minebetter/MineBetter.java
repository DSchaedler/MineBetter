package com.dschaedler.minebetter;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
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



public class MineBetter implements ModInitializer {

	// Initialize Blocks
	// Petrified Log
	public static final PillarBlock PETRIFIED_LOG = new PillarBlock(
		FabricBlockSettings.of(Material.WOOD).hardness(2.0f));

	public static final MBTrapdoor GLASS_TRAPDOOR_BLOCK = new MBTrapdoor(
		FabricBlockSettings.of(Material.GLASS).hardness(0.3f));
	
	// ConfiguredFeature for Spawning Petrified Logs
	private static ConfiguredFeature<?, ?> ORE_PETRIFIED_LOG = Feature.ORE
			.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
					PETRIFIED_LOG.getDefaultState(), 5)) // Vein Size
			.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0, // bottomOffset,
					5, // topOffset,
					32 // maximum
			))).spreadHorizontally().repeat(10); // Veins per Chunk

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		
		// Say Hi!
		System.out.println("[MineBetter] Hello from MineBetter!");
		
		// Register Blocks
		System.out.println("[MineBetter] Registering Blocks...");
		Registry.register(Registry.BLOCK, new Identifier("minebetter", "petrified_log"), PETRIFIED_LOG);
		Registry.register(Registry.ITEM, new Identifier("minebetter", "petrified_log"), new BlockItem(PETRIFIED_LOG, new Item.Settings().group(ItemGroup.MISC)));


		Registry.register(Registry.BLOCK, new Identifier("minebetter", "glass_trapdoor"), GLASS_TRAPDOOR_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("minebetter", "glass_trapdoor"), new BlockItem(GLASS_TRAPDOOR_BLOCK, new Item.Settings().group(ItemGroup.MISC)));

		// Spawn Petrified Logs with Configured Feature
		// Register the ConfiguredFeature
		System.out.println("[MineBetter] Spawning Petrified Logs...");
		RegistryKey<ConfiguredFeature<?, ?>> orePetrifiedLog = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN,
			new Identifier("minebetter", "ore_petrified_log"));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, orePetrifiedLog.getValue(), ORE_PETRIFIED_LOG);

		//Spawn Logs
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, orePetrifiedLog);

		System.out.println("[MineBetter] All Done!");

	}
}
