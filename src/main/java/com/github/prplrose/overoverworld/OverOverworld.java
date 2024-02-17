package com.github.prplrose.overoverworld;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.kyrptonaught.customportalapi.portal.PortalIgnitionSource;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class OverOverworld implements ModInitializer {

    public static final String MODID = "overoverworld";
    public static final Identifier OVEROVERWORLD_ID = new Identifier(MODID, MODID);
    public static final Logger LOGGER = LoggerFactory.getLogger("Overoverworld");


    @Override
    public void onInitialize() {
        CustomPortalBuilder.beginPortal()
                .frameBlock(Blocks.PURPUR_BLOCK)
                .lightWithItem(Items.FEATHER)
                .destDimID(OVEROVERWORLD_ID)
                .tintColor(234, 183, 8)
                .registerPortal();

        //noinspection deprecation
        CustomPortalBuilder.beginPortal()
                .frameBlock(Blocks.OBSIDIAN)
                .customIgnitionSource(PortalIgnitionSource.FIRE)
                .returnDim(OVEROVERWORLD_ID, true)
                .destDimID(new Identifier(MODID, "netherer"))
                .tintColor(234, 183, 8)
                .registerPortalForced();

        //CustomPortalApiRegistry.forceAddPortal(Blocks.OBSIDIAN, nethererportal);


        ServerWorldEvents.LOAD.register((server, world) -> {
            if (!world.getRegistryKey().getValue().equals(OVEROVERWORLD_ID))
                return;
            if (StateSaverAndLoader.getServerState(world).isOveroverworldSpawnGenerated)
                return;

            Optional<StructureTemplate> spawnStructure = world.getServer().getStructureTemplateManager().getTemplate(new Identifier(MODID, "spawn"));
            if (spawnStructure.isEmpty())
                return;
            BlockPos spawnPos = new BlockPos(-5,61,-6);
            spawnStructure.get().place(world, spawnPos, spawnPos, new StructurePlacementData(), world.getRandom(), 0);
            world.setBlockState(spawnPos.add(5,3,6), Blocks.AIR.getDefaultState());
            StateSaverAndLoader.getServerState(world).isOveroverworldSpawnGenerated = true;
            LOGGER.info("Generated spawn in " + world.getRegistryKey().getValue());
        });

    }
}
