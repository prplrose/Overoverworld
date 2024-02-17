package com.github.prplrose.overoverworld.mixin;

import com.github.prplrose.overoverworld.OverOverworld;
import com.google.common.collect.ImmutableList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.village.ZombieSiegeManager;
import net.minecraft.world.WanderingTraderManager;
import net.minecraft.world.World;
import net.minecraft.world.level.UnmodifiableLevelProperties;
import net.minecraft.world.spawner.CatSpawner;
import net.minecraft.world.spawner.PatrolSpawner;
import net.minecraft.world.spawner.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

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
