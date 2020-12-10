package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ExampleMod implements ModInitializer {

	// Initialize Blocks
	public static final Block PETRIFIED_LOG = new Block(FabricBlockSettings.of(Material.WOOD).hardness(2.0f));
	
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		// Register Blocks
		Registry.register(Registry.BLOCK, new Identifier("minebetter", "petrified_log"), PETRIFIED_LOG);
        Registry.register(Registry.ITEM, new Identifier("minebetter", "petrified_log"), new BlockItem(PETRIFIED_LOG, new Item.Settings().group(ItemGroup.MISC)));

	}
}
