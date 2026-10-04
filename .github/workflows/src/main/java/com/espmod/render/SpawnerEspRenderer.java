package com.espmod.render;

import com.espmod.config.EspConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class SpawnerEspRenderer {

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;
        if (!EspConfig.spawnerEspEnabled) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        Vec3 camera = event.getCamera().getPosition();
        BlockPos playerPos = mc.player.blockPosition();
        int range = EspConfig.spawnerEspRange;

        PoseStack poseStack = event.getPoseStack();
        poseStack.pushPose();
        poseStack.translate(-camera.x, -camera.y, -camera.z);

        VertexConsumer outlineConsumer = mc.renderBuffers().bufferSource()
                .getBuffer(RenderType.lines());

        for (BlockPos pos : BlockPos.betweenClosed(
                playerPos.offset(-range, -range, -range),
                playerPos.offset(range, range, range))) {
            BlockState state = mc.level.getBlockState(pos);

            if (state.getBlock() == Blocks.SPAWNER) {
                LevelRenderer.renderLineBox(poseStack, outlineConsumer,
                        pos.getX(), pos.getY(), pos.getZ(),
                        pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1,
                        EspConfig.spawnerEspOutlineColor[0],
                        EspConfig.spawnerEspOutlineColor[1],
                        EspConfig.spawnerEspOutlineColor[2],
                        EspConfig.spawnerEspOutlineColor[3]);
            }
        }

        poseStack.popPose();
    }
}
