package com.espmod.freecam;

import com.espmod.config.EspConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

public class FreecamHandler {

    private static boolean active = false;
    private static Vec3 savedPosition = null;
    private static double camX, camY, camZ;

    public static void toggle() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        if (!active) {
            active = true;
            savedPosition = mc.player.position();
            camX = savedPosition.x;
            camY = savedPosition.y + mc.player.getEyeHeight();
            camZ = savedPosition.z;
            mc.player.noPhysics = true;
        } else {
            active = false;
            mc.player.noPhysics = false;
            if (savedPosition != null) {
                mc.player.setPos(savedPosition.x, savedPosition.y, savedPosition.z);
            }
        }
    }

    public static void tick() {
        if (!active) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || savedPosition == null) return;

        double speed = EspConfig.freecamSpeed * (mc.options.keySprint.isDown() ? 2.0 : 1.0);

        float yaw = mc.player.getYRot();
        float pitch = mc.player.getXRot();

        Vec3 forward = Vec3.directionFromRotation(pitch, yaw);
        Vec3 right = Vec3.directionFromRotation(0, yaw + 90);

        double dx = 0, dy = 0, dz = 0;

        if (mc.options.keyUp.isDown()) {
            dx += forward.x * speed; dy += forward.y * speed; dz += forward.z * speed;
        }
        if (mc.options.keyDown.isDown()) {
            dx -= forward.x * speed; dy -= forward.y * speed; dz -= forward.z * speed;
        }
        if (mc.options.keyRight.isDown()) { dx += right.x * speed; dz += right.z * speed; }
        if (mc.options.keyLeft.isDown())  { dx -= right.x * speed; dz -= right.z * speed; }
        if (mc.options.keyJump.isDown())  { dy += speed; }
        if (mc.options.keyShift.isDown()) { dy -= speed; }

        camX += dx; camY += dy; camZ += dz;

        mc.player.setPos(camX, camY - mc.player.getEyeHeight(), camZ);
        mc.player.setDeltaMovement(Vec3.ZERO);
    }

    public static boolean isActive() { return active; }
}
