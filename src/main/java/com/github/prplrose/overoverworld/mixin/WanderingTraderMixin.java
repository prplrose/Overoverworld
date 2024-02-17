package com.github.prplrose.overoverworld.mixin;

import com.github.prplrose.overoverworld.OverOverworld;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.WanderingTraderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(WanderingTraderManager.class)
public class WanderingTraderMixin {
/*
    @Redirect(method = "trySpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;getRandomAlivePlayer()Lnet/minecraft/server/network/ServerPlayerEntity;"))
    ServerPlayerEntity getRandomPlayer(ServerWorld world, @Local LocalRef<ServerWorld> localRef){
        List<ServerPlayerEntity> playerList = new ArrayList<>(List.copyOf(world.getPlayers(LivingEntity::isAlive)));
        for (ServerWorld serverWorld : world.getServer().getWorlds()) {
            if (serverWorld.getRegistryKey().getValue().equals(OverOverworld.OVEROVERWORLD_ID))
                playerList.addAll(serverWorld.getPlayers(PlayerEntity::isAlive));
        }
        int playerCount = playerList.size();
        if (playerCount < 1)
            return null;
        ServerPlayerEntity player = playerList.get(world.getRandom().nextInt(playerCount));
        localRef.set(player.getServerWorld());
        OverOverworld.LOGGER.info("Spawning Trader for " + player.getName() + " in " + localRef.get().getRegistryKey().getValue());
        return player;
    }*/

   @Inject(method = "spawn", at = @At(value = "RETURN", ordinal = 4))
    void debug1(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> cir){

        OverOverworld.LOGGER.info("Trader spawn attempt in: " + world.getRegistryKey().getValue() + " unlucky rng ");
    }

    @Inject(method = "spawn", at = @At(value = "RETURN", ordinal = 5))
    void debug0(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> cir){

        OverOverworld.LOGGER.info("Trader spawn attempt in: " + world.getRegistryKey().getValue() + " and looks promising ");
    }

    @Inject(method = "spawn", at = @At(value = "RETURN", ordinal = 6))
    void debug10(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> cir){

        OverOverworld.LOGGER.info("Trader spawn attempt in: " + world.getRegistryKey().getValue() + " and didnt  ");
    }

    @Inject(method = "trySpawn", at = @At(value = "RETURN", ordinal = 0))
    void debug2(ServerWorld world, CallbackInfoReturnable<Boolean> cir){
        OverOverworld.LOGGER.info("Attempted spawn in: " + world.getRegistryKey().getValue() + " and failed: player is null");
    }


    @Inject(method = "trySpawn", at = @At(value = "RETURN", ordinal = 1))
    void debug3(ServerWorld world, CallbackInfoReturnable<Boolean> cir){
        OverOverworld.LOGGER.info("Attempted spawn in: " + world.getRegistryKey().getValue() + " and failed: unlucky RNG");
    }

    @Inject(method = "trySpawn", at = @At(value = "RETURN", ordinal = 2))
    void debug4(ServerWorld world, CallbackInfoReturnable<Boolean> cir){
        OverOverworld.LOGGER.info("Attempted spawn in: " + world.getRegistryKey().getValue() + " and failed: bad biome");
    }

    @Inject(method = "trySpawn", at = @At(value = "RETURN", ordinal = 3))
    void debug5(ServerWorld world, CallbackInfoReturnable<Boolean> cir){
        OverOverworld.LOGGER.info("Attempted spawn in: " + world.getRegistryKey().getValue() + " and succeeded :)");
    }

    @Inject(method = "trySpawn", at = @At(value = "RETURN", ordinal = 4))
    void debug6(ServerWorld world, CallbackInfoReturnable<Boolean> cir){
        OverOverworld.LOGGER.info("Attempted spawn in: " + world.getRegistryKey().getValue() + " and failed: no valid spawn position");
    }
}
