package com.dschaedler.minebetter;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.TrapdoorBlock;

public class MBTrapdoor extends TrapdoorBlock{

    public MBTrapdoor(FabricBlockSettings fabricBlockSettings) {
        super(FabricBlockSettings.copyOf(fabricBlockSettings).nonOpaque());
    }
    
}
