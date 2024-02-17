package com.github.prplrose.overoverworld.mixin;

import eu.pb4.polymer.core.api.block.PolymerBlock;
import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CustomPortalBlock.class)
public class CustomPortalBlockMixin implements PolymerBlock {
    @Override
    public Block getPolymerBlock(BlockState state) {
        return Blocks.NETHER_PORTAL;
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        Direction.Axis axis = state.get(Properties.AXIS);
        if (axis == Direction.Axis.Y){
            return Blocks.NETHER_PORTAL.getDefaultState().with(Properties.HORIZONTAL_AXIS, Direction.Axis.Z);
        }
        return Blocks.NETHER_PORTAL.getDefaultState().with(Properties.HORIZONTAL_AXIS, axis);
    }
}
