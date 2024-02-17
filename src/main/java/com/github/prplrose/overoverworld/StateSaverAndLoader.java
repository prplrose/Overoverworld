package com.github.prplrose.overoverworld;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

public class StateSaverAndLoader extends PersistentState {

    public Boolean isOveroverworldSpawnGenerated = false;
    static final String SPAWN_GENERATED_KEY = "isOveroverworldSpawnGenerated";
    private static final Type<StateSaverAndLoader> type = new Type<>(
            StateSaverAndLoader::new,
            StateSaverAndLoader::createFromNbt,
            null
    );

    @Override
    public NbtCompound writeNbt(NbtCompound nbtCompound) {
        nbtCompound.putBoolean(SPAWN_GENERATED_KEY, isOveroverworldSpawnGenerated);
        return nbtCompound;
    }

    public static StateSaverAndLoader createFromNbt(NbtCompound nbtCompound){
        StateSaverAndLoader stateSaverAndLoader = new StateSaverAndLoader();
        stateSaverAndLoader.isOveroverworldSpawnGenerated = nbtCompound.getBoolean(SPAWN_GENERATED_KEY);
        return stateSaverAndLoader;
    }


    public static StateSaverAndLoader getServerState(ServerWorld serverWorld) {

        PersistentStateManager persistentStateManager = serverWorld.getPersistentStateManager();
        StateSaverAndLoader state = persistentStateManager.getOrCreate(type, OverOverworld.MODID);
        state.markDirty();

        return state;
    }
}
