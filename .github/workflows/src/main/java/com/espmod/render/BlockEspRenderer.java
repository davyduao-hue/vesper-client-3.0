package com.espmod.render;

import com.espmod.config.EspConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class BlockEspRenderer {

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;
        if (!EspConfig.blockEspEnabled || EspConfig.blockEspTargets.isEmpty()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        Vec3 camera = event.getCamera().getPosition();
        BlockPos playerPos = mc.player.blockPosition();
        int range = EspConfig.blockEspRange;

        PoseStack poseStack = event.getPoseStack();
        poseStack.pushPose();
        poseStack.translate(-camera.x, -camera.y, -camera.z);

        VertexConsumer outlineConsumer = mc.renderBuffers().bufferSource()
                .getBuffer(RenderType.lines());

        for (BlockPos pos : BlockPos.betweenClosed(
                playerPos.offset(-range, -range, -range),
                playerPos.offset(range, range, range))) {
            BlockState state = mc.level.getBlockState(pos);
            ResourceLocation blockId = ForgeRegistries.BLOCKS.getKey(state.getBlock());

            if (blockId != null && EspConfig.blockEspTargets.contains(blockId)) {
                LevelRenderer.renderLineBox(poseStack, outlineConsumer,
                        pos.getX(), pos.getY(), pos.getZ(),
                        pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1,
                        EspConfig.blockEspOutlineColor[0],
                        EspConfig.blockEspOutlineColor[1],
                        EspConfig.blockEspOutlineColor[2],
                        EspConfig.blockEspOutlineColor[3]);
            }
        }

        poseStack.popPose();
    }
}
