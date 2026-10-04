package com.espmod.render;

import com.espmod.config.EspConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class XrayHandler {

    public static boolean shouldRenderBlock(BlockState state, BlockPos pos) {
        if (!EspConfig.xrayEnabled) return true;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return true;

        if (state.isAir()) return true;

        ResourceLocation blockId = ForgeRegistries.BLOCKS.getKey(state.getBlock());
        if (blockId != null && EspConfig.xrayBlocks.contains(blockId)) return true;

        for (BlockPos offset : new BlockPos[]{
                pos.above(), pos.below(), pos.north(), pos.south(), pos.east(), pos.west()}) {
            BlockState neighbor = mc.level.getBlockState(offset);
            ResourceLocation neighborId = ForgeRegistries.BLOCKS.getKey(neighbor.getBlock());
            if (neighborId != null && EspConfig.xrayBlocks.contains(neighborId)) {
                return true;
            }
        }

        return false;
    }
}
