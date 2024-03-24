package com.github.prplrose.overoverworld.mixin;

import com.github.prplrose.overoverworld.IndependentLevelProperties;
import com.github.prplrose.overoverworld.OverOverworld;
import com.github.prplrose.overoverworld.StateSaverAndLoader;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.ZombieSiegeManager;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.WanderingTraderManager;
import net.minecraft.world.World;
import net.minecraft.world.spawner.CatSpawner;
import net.minecraft.world.spawner.PatrolSpawner;
import net.minecraft.world.spawner.PhantomSpawner;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @Final @Shadow protected SaveProperties saveProperties;


    @Shadow public abstract ServerWorld getOverworld();

    @ModifyArgs( method = "createWorlds", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;<init>(Lnet/minecraft/server/MinecraftServer;Ljava/util/concurrent/Executor;Lnet/minecraft/world/level/storage/LevelStorage$Session;Lnet/minecraft/world/level/ServerWorldProperties;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/world/dimension/DimensionOptions;Lnet/minecraft/server/WorldGenerationProgressListener;ZJLjava/util/List;ZLnet/minecraft/util/math/random/RandomSequencesState;)V", ordinal = 1))
    void createOveroverworld(Args args, @Local ServerWorld overworld){
        RegistryKey<World> worldKey = args.get(4);
        if (!worldKey.getValue().equals(OverOverworld.OVEROVERWORLD_ID))
            return;

        IndependentLevelProperties independentLevelProperties = new IndependentLevelProperties(this.saveProperties, StateSaverAndLoader.getServerState(overworld).levelProperties);
        OverOverworld.OVEROVERWORLD_PROPERTIES = independentLevelProperties;
        args.set(3, independentLevelProperties);
        args.set(9, ImmutableList.of(new PhantomSpawner(), new PatrolSpawner(), new CatSpawner(), new ZombieSiegeManager(), new WanderingTraderManager(independentLevelProperties)));
        args.set(10, true);
    }

    @Inject(method = "save", at = @At("TAIL"))
    void saveProperties(boolean suppressLogs, boolean flush, boolean force, CallbackInfoReturnable<Boolean> cir){
        if (OverOverworld.OVEROVERWORLD_PROPERTIES != null)
            StateSaverAndLoader.getServerState(this.getOverworld()).levelProperties = OverOverworld.OVEROVERWORLD_PROPERTIES.save();
    }

}
