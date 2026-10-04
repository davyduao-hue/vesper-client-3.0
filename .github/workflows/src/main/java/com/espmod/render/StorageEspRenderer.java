package com.espmod.render;

import com.espmod.config.EspConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class StorageEspRenderer {

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;
        if (!EspConfig.storageEspEnabled) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        Vec3 camera = event.getCamera().getPosition();
        BlockPos playerPos = mc.player.blockPosition();
        int range = EspConfig.storageEspRange;

        PoseStack poseStack = event.getPoseStack();
        poseStack.pushPose();
        poseStack.translate(-camera.x, -camera.y, -camera.z);

        VertexConsumer outlineConsumer = mc.renderBuffers().bufferSource()
                .getBuffer(RenderType.lines());

        for (BlockPos pos : BlockPos.betweenClosed(
                playerPos.offset(-range, -range, -range),
                playerPos.offset(range, range, range))) {
            BlockState state = mc.level.getBlockState(pos);
            Block block = state.getBlock();

            if (isStorageBlock(block)) {
                LevelRenderer.renderLineBox(poseStack, outlineConsumer,
                        pos.getX(), pos.getY(), pos.getZ(),
                        pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1,
                        EspConfig.storageEspOutlineColor[0],
                        EspConfig.storageEspOutlineColor[1],
                        EspConfig.storageEspOutlineColor[2],
                        EspConfig.storageEspOutlineColor[3]);
            }
        }

        poseStack.popPose();
    }

    private static boolean isStorageBlock(Block block) {
        return block == Blocks.CHEST
                || block == Blocks.TRAPPED_CHEST
                || block == Blocks.ENDER_CHEST
                || block == Blocks.BARREL
                || block == Blocks.SHULKER_BOX
                || block == Blocks.WHITE_SHULKER_BOX
                || block == Blocks.ORANGE_SHULKER_BOX
                || block == Blocks.MAGENTA_SHULKER_BOX
                || block == Blocks.LIGHT_BLUE_SHULKER_BOX
                || block == Blocks.YELLOW_SHULKER_BOX
                || block == Blocks.LIME_SHULKER_BOX
                || block == Blocks.PINK_SHULKER_BOX
                || block == Blocks.GRAY_SHULKER_BOX
                || block == Blocks.LIGHT_GRAY_SHULKER_BOX
                || block == Blocks.CYAN_SHULKER_BOX
                || block == Blocks.PURPLE_SHULKER_BOX
                || block == Blocks.BLUE_SHULKER_BOX
                || block == Blocks.BROWN_SHULKER_BOX
                || block == Blocks.GREEN_SHULKER_BOX
                || block == Blocks.RED_SHULKER_BOX
                || block == Blocks.BLACK_SHULKER_BOX
                || block == Blocks.FURNACE
                || block == Blocks.BLAST_FURNACE
                || block == Blocks.SMOKER
                || block == Blocks.HOPPER
                || block == Blocks.DISPENSER
                || block == Blocks.DROPPER
                || block == Blocks.BREWING_STAND
                || block == Blocks.LECTERN
                || block == Blocks.CHISELED_BOOKSHELF;
    }
}
