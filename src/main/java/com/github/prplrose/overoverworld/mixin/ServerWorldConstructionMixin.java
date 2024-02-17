package com.github.prplrose.overoverworld.mixin;

import com.github.prplrose.overoverworld.OverOverworld;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.RandomSequencesState;
import net.minecraft.village.ZombieSiegeManager;
import net.minecraft.world.WanderingTraderManager;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.UnmodifiableLevelProperties;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.spawner.CatSpawner;
import net.minecraft.world.spawner.PatrolSpawner;
import net.minecraft.world.spawner.PhantomSpawner;
import net.minecraft.world.spawner.SpecialSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.List;
import java.util.concurrent.Executor;

@Mixin(MinecraftServer.class)
public class ServerWorldConstructionMixin {

    @ModifyArgs( method = "createWorlds", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;<init>(Lnet/minecraft/server/MinecraftServer;Ljava/util/concurrent/Executor;Lnet/minecraft/world/level/storage/LevelStorage$Session;Lnet/minecraft/world/level/ServerWorldProperties;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/world/dimension/DimensionOptions;Lnet/minecraft/server/WorldGenerationProgressListener;ZJLjava/util/List;ZLnet/minecraft/util/math/random/RandomSequencesState;)V", ordinal = 1))
    void createOveroverworld(Args args){
        RegistryKey<World> worldKey = args.get(4);
        if (!worldKey.getValue().equals(OverOverworld.OVEROVERWORLD_ID))
            return;
        UnmodifiableLevelProperties unmodifiableLevelProperties = args.get(3);
        args.set(9, ImmutableList.of(new PhantomSpawner(), new PatrolSpawner(), new CatSpawner(), new ZombieSiegeManager(), new WanderingTraderManager(unmodifiableLevelProperties)));
    }

}
