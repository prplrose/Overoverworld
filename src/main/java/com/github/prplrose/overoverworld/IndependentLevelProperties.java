package com.github.prplrose.overoverworld;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.timer.Timer;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class IndependentLevelProperties implements ServerWorldProperties {

    private int spawnX;
    private int spawnY;
    private int spawnZ;
    private float spawnAngle;
    private long time;
    private long timeOfDay;
    private int clearWeatherTime;
    private boolean raining;
    private int rainTime;
    private boolean thundering;
    private int thunderTime;
    private boolean initialized;
    private int wanderingTraderSpawnDelay;
    private int wanderingTraderSpawnChance;
    @Nullable
    private UUID wanderingTraderId;
    private final Timer<MinecraftServer> scheduledEvents;
    private final SaveProperties saveProperties;


    private IndependentLevelProperties(SaveProperties saveProperties,
            int spawnX, int spawnY, int spawnZ, float spawnAngle,
            long time, long timeOfDay,
            int clearWeatherTime,
            boolean raining, int rainTime,
            boolean thundering, int thunderTime,
            int wanderingTraderSpawnDelay, int wanderingTraderSpawnChance, @Nullable UUID wanderingTraderId) {
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.spawnZ = spawnZ;
        this.spawnAngle = spawnAngle;
        this.time = time;
        this.timeOfDay = timeOfDay;
        this.clearWeatherTime = clearWeatherTime;
        this.raining = raining;
        this.rainTime = rainTime;
        this.thundering = thundering;
        this.thunderTime = thunderTime;
        this.wanderingTraderSpawnDelay = wanderingTraderSpawnDelay;
        this.wanderingTraderSpawnChance = wanderingTraderSpawnChance;
        this.wanderingTraderId = wanderingTraderId;
        this.saveProperties = saveProperties;
        this.scheduledEvents = saveProperties.getMainWorldProperties().getScheduledEvents();
    }

    public IndependentLevelProperties(SaveProperties saveProperties, @Nullable NbtCompound nbtCompound) {
        this(saveProperties,
                0,0,0,0.0f,
                0L,0L,
                0,
                false,0,
                false,0,
                0,0,null);
        if (nbtCompound!=null)
            read(nbtCompound);
    }

    public NbtCompound save(){
        NbtCompound nbtCompound = new NbtCompound();

        nbtCompound.putInt("SpawnX", this.spawnX);
        nbtCompound.putInt("SpawnY", this.spawnY);
        nbtCompound.putInt("SpawnZ", this.spawnZ);
        nbtCompound.putFloat("SpawnAngle", this.spawnAngle);
        nbtCompound.putLong("Time", this.time);
        nbtCompound.putLong("DayTime", this.timeOfDay);
        nbtCompound.putInt("clearWeatherTime", this.clearWeatherTime);
        nbtCompound.putInt("rainTime", this.rainTime);
        nbtCompound.putBoolean("raining", this.raining);
        nbtCompound.putInt("thunderTime", this.thunderTime);
        nbtCompound.putBoolean("thundering", this.thundering);
        nbtCompound.putBoolean("initialized", this.initialized);
        nbtCompound.put("ScheduledEvents", this.scheduledEvents.toNbt());
        nbtCompound.putInt("WanderingTraderSpawnDelay", this.wanderingTraderSpawnDelay);
        nbtCompound.putInt("WanderingTraderSpawnChance", this.wanderingTraderSpawnChance);
        if (this.wanderingTraderId != null) {
            nbtCompound.putUuid("WanderingTraderId", this.wanderingTraderId);
        }
        return nbtCompound;
    }

    public void read(NbtCompound nbtCompound){

        this.spawnX = nbtCompound.getInt("SpawnX");
        this.spawnY = nbtCompound.getInt("SpawnY");
        this.spawnZ = nbtCompound.getInt("SpawnZ");
        this.spawnAngle = nbtCompound.getFloat("SpawnAngle");
        this.time = nbtCompound.getLong("Time");
        this.timeOfDay = nbtCompound.getLong("DayTime");
        this.clearWeatherTime = nbtCompound.getInt("clearWeatherTime");
        this.rainTime = nbtCompound.getInt("rainTime");
        this.raining = nbtCompound.getBoolean("raining");
        this.thunderTime = nbtCompound.getInt("thunderTime");
        this.thundering = nbtCompound.getBoolean("thundering");
        this.initialized = nbtCompound.getBoolean("initialized");
        //this.scheduledEvents = nbtCompound.get("ScheduledEvents");
        this.wanderingTraderSpawnDelay = nbtCompound.getInt("WanderingTraderSpawnDelay");
        this.wanderingTraderSpawnChance = nbtCompound.getInt("WanderingTraderSpawnChance");
        if (this.wanderingTraderId != null) {
            this.wanderingTraderId = nbtCompound.getUuid("WanderingTraderId");
        }
    }


    @Override
    public int getSpawnX() {
        return this.spawnX;
    }

    @Override
    public int getSpawnY() {
        return this.spawnY;
    }

    @Override
    public int getSpawnZ() {
        return this.spawnZ;
    }

    @Override
    public float getSpawnAngle() {
        return this.spawnAngle;
    }

    @Override
    public long getTime() {
        return this.time;
    }

    @Override
    public long getTimeOfDay() {
        return this.timeOfDay;
    }

    @Override
    public void setSpawnX(int spawnX) {
        this.spawnX = spawnX;
    }

    @Override
    public void setSpawnY(int spawnY) {
        this.spawnY = spawnY;
    }

    @Override
    public void setSpawnZ(int spawnZ) {
        this.spawnZ = spawnZ;
    }

    @Override
    public void setSpawnAngle(float spawnAngle) {
        this.spawnAngle = spawnAngle;
    }

    @Override
    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public void setTimeOfDay(long timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    @Override
    public void setSpawnPos(BlockPos pos, float angle) {
        this.spawnX = pos.getX();
        this.spawnY = pos.getY();
        this.spawnZ = pos.getZ();
        this.spawnAngle = angle;
    }

    @Override
    public String getLevelName() {
        return this.saveProperties.getLevelName();
    }


    @Override
    public int getClearWeatherTime() {
        return this.clearWeatherTime;
    }

    @Override
    public void setClearWeatherTime(int clearWeatherTime) {
        this.clearWeatherTime = clearWeatherTime;
    }

    @Override
    public boolean isThundering() {
        return this.thundering;
    }

    @Override
    public void setThundering(boolean thundering) {
        this.thundering = thundering;
    }

    @Override
    public int getThunderTime() {
        return this.thunderTime;
    }

    @Override
    public void setThunderTime(int thunderTime) {
        this.thunderTime = thunderTime;
    }

    @Override
    public boolean isRaining() {
        return this.raining;
    }

    @Override
    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    @Override
    public int getRainTime() {
        return this.rainTime;
    }

    @Override
    public void setRainTime(int rainTime) {
        this.rainTime = rainTime;
    }

    @Override
    public GameMode getGameMode() {
        return this.saveProperties.getGameMode();
    }

    @Override
    public void setGameMode(GameMode gameMode) {
    }

    @Override
    public boolean isHardcore() {
        return this.saveProperties.isHardcore();
    }

    @Override
    public boolean areCommandsAllowed() {
        return this.saveProperties.areCommandsAllowed();
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

    @Override
    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    @Override
    public GameRules getGameRules() {
        return this.saveProperties.getGameRules();
    }

    @Override
    public WorldBorder.Properties getWorldBorder() {
        return this.saveProperties.getMainWorldProperties().getWorldBorder();
    }

    @Override
    public void setWorldBorder(WorldBorder.Properties worldBorder) {
    }

    @Override
    public Difficulty getDifficulty() {
        return this.saveProperties.getDifficulty();
    }

    @Override
    public boolean isDifficultyLocked() {
        return this.saveProperties.isDifficultyLocked();
    }


    @Override
    public Timer<MinecraftServer> getScheduledEvents() {
        return this.scheduledEvents;
    }


    @Override
    public int getWanderingTraderSpawnDelay() {
        return this.wanderingTraderSpawnDelay;
    }

    @Override
    public void setWanderingTraderSpawnDelay(int wanderingTraderSpawnDelay) {
        this.wanderingTraderSpawnDelay = wanderingTraderSpawnDelay;
    }

    @Override
    public int getWanderingTraderSpawnChance() {
        return this.wanderingTraderSpawnChance;
    }

    @Override
    public void setWanderingTraderSpawnChance(int wanderingTraderSpawnChance) {
        this.wanderingTraderSpawnChance = wanderingTraderSpawnChance;
    }

    @Override
    @Nullable
    public UUID getWanderingTraderId() {
        return this.wanderingTraderId;
    }

    @Override
    public void setWanderingTraderId(@Nullable UUID wanderingTraderId) {
        this.wanderingTraderId = wanderingTraderId;
    }

}
