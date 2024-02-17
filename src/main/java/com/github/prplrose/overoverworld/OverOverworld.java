package com.github.prplrose.overoverworld;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.CustomPortalsMod;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.kyrptonaught.customportalapi.portal.PortalIgnitionSource;
import net.kyrptonaught.customportalapi.util.PortalLink;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.gen.structure.Structure;
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

        @SuppressWarnings("deprecation")
        PortalLink nethererportal = CustomPortalBuilder.beginPortal()
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
