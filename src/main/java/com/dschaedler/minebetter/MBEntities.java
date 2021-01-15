package com.dschaedler.minebetter;

import java.util.LinkedHashMap;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MBEntities {

    public static final LinkedHashMap<EntityType, Identifier> ENTITIES = new LinkedHashMap<>();

    public static final EntityType<MBTrackMinecart> TRACK_MINECART = create("track_minecart", FabricEntityTypeBuilder
    .<MBTrackMinecart>create(SpawnGroup.MISC, (type, world) -> new MBTrackMinecart(type, world))
    .build());

    public static void register()
    {
        ENTITIES.keySet().forEach(type -> Registry.register(Registry.ENTITY_TYPE, ENTITIES.get(type), type));
    }

    private static <T extends EntityType> T create(String name, T type)
    {
        ENTITIES.put(type, new Identifier(MineBetter.MOD_ID, name));
        return type;
    }
}
