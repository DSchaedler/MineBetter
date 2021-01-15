package com.dschaedler.minebetter;

import net.minecraft.client.render.entity.MinecartEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.HopperMinecartEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class MBTrackMinecart extends HopperMinecartEntity {
    
    public MBTrackMinecart(EntityType<? extends HopperMinecartEntity> entityType, World world) {
        super(entityType, world);
    }
    
}