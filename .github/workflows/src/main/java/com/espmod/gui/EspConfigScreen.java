package com.espmod.gui;

import com.espmod.config.EspConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class EspConfigScreen extends Screen {

    private EditBox blockInput;
    private EditBox xrayBlockInput;

    public EspConfigScreen() {
        super(Component.literal("ESP Mod Configuration"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = 30;

        addRenderableWidget(Button.builder(
                Component.literal("Xray: " + (EspConfig.xrayEnabled ? "ON" : "OFF")),
                btn -> {
                    EspConfig.xrayEnabled = !EspConfig.xrayEnabled;
                    btn.setMessage(Component.literal("Xray: " + (EspConfig.xrayEnabled ? "ON" : "OFF")));
                }).bounds(centerX - 200, y, 120, 20).build());

        addRenderableWidget(Button.builder(
                Component.literal("Block ESP: " + (EspConfig.blockEspEnabled ? "ON" : "OFF")),
                btn -> {
                    EspConfig.blockEspEnabled = !EspConfig.blockEspEnabled;
                    btn.setMessage(Component.literal("Block ESP: " + (EspConfig.blockEspEnabled ? "ON" : "OFF")));
                }).bounds(centerX - 70, y, 130, 20).build());

        addRenderableWidget(Button.builder(
                Component.literal("Storage ESP: " + (EspConfig.storageEspEnabled ? "ON" : "OFF")),
                btn -> {
                    EspConfig.storageEspEnabled = !EspConfig.storageEspEnabled;
                    btn.setMessage(Component.literal("Storage ESP: " + (EspConfig.storageEspEnabled ? "ON" : "OFF")));
                }).bounds(centerX + 70, y, 130, 20).build());

        y += 25;

        addRenderableWidget(Button.builder(
                Component.literal("Spawner ESP: " + (EspConfig.spawnerEspEnabled ? "ON" : "OFF")),
                btn -> {
                    EspConfig.spawnerEspEnabled = !EspConfig.spawnerEspEnabled;
                    btn.setMessage(Component.literal("Spawner ESP: " + (EspConfig.spawnerEspEnabled ? "ON" : "OFF")));
                }).bounds(centerX - 200, y, 150, 20).build());

        addRenderableWidget(Button.builder(
                Component.literal("Freecam Speed: " + EspConfig.freecamSpeed + "x"),
                btn -> {
                    EspConfig.freecamSpeed = EspConfig.freecamSpeed >= 5.0 ? 0.5 : EspConfig.freecamSpeed + 0.5;
                    btn.setMessage(Component.literal("Freecam Speed: " + EspConfig.freecamSpeed + "x"));
                }).bounds(centerX - 40, y, 170, 20).build());

        y += 35;

        blockInput = new EditBox(this.font, centerX - 200, y, 300, 20,
                Component.literal("Add block (e.g. minecraft:diamond_ore)"));
        addRenderableWidget(blockInput);
        addRenderableWidget(Button.builder(Component.literal("Add"), btn -> {
            if (!blockInput.getValue().isEmpty()) {
                try {
                    ResourceLocation rl = ResourceLocation.parse(blockInput.getValue().trim());
                    if (ForgeRegistries.BLOCKS.containsKey(rl)) {
                        EspConfig.blockEspTargets.add(rl);
                        blockInput.setValue("");
                    }
                } catch (Exception ignored) {}
            }
        }).bounds(centerX + 110, y, 60, 20).build());

        y += 25;

        xrayBlockInput = new EditBox(this.font, centerX - 200, y, 300, 20,
                Component.literal("Add xray block"));
        addRenderableWidget(xrayBlockInput);
        addRenderableWidget(Button.builder(Component.literal("Add"), btn -> {
            if (!xrayBlockInput.getValue().isEmpty()) {
                try {
                    ResourceLocation rl = ResourceLocation.parse(xrayBlockInput.getValue().trim());
                    if (ForgeRegistries.BLOCKS.containsKey(rl)) {
                        EspConfig.xrayBlocks.add(rl);
                        xrayBlockInput.setValue("");
                    }
                } catch (Exception ignored) {}
            }
        }).bounds(centerX + 110, y, 60, 20).build());

        y += 30;

        addColorButton(centerX - 200, y, "Block ESP Outline", EspConfig.blockEspOutlineColor);
        addColorButton(centerX - 200, y + 25, "Block ESP Fill", EspConfig.blockEspFillColor);
        addColorButton(centerX - 200, y + 50, "Storage ESP Outline", EspConfig.storageEspOutlineColor);
        addColorButton(centerX - 200, y + 75, "Storage ESP Fill", EspConfig.storageEspFillColor);
        addColorButton(centerX - 200, y + 100, "Spawner ESP Outline", EspConfig.spawnerEspOutlineColor);
        addColorButton(centerX - 200, y + 125, "Spawner ESP Fill", EspConfig.spawnerEspFillColor);

        addRenderableWidget(Button.builder(Component.literal("Done"), btn -> {
            this.onClose();
        }).bounds(centerX - 50, this.height - 30, 100, 20).build());
    }

    private void addColorButton(int x, int y, String label, float[] color) {
        addRenderableWidget(Button.builder(Component.literal(label), btn -> {
            color[0] = (color[0] + 0.25f) % 1.0f;
            color[1] = (color[1] + 0.25f) % 1.0f;
            color[2] = (color[2] + 0.25f) % 1.0f;
        }).bounds(x, y, 200, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 10, 0xFFFFFF);

        int y = 260;
        graphics.drawString(this.font, "Block ESP Targets:", 10, y, 0xFFAA00);
        y += 12;
        for (ResourceLocation rl : EspConfig.blockEspTargets) {
            if (y > this.height - 40) break;
            graphics.drawString(this.font, "  " + rl.toString(), 10, y, 0xCCCCCC);
            y += 10;
        }

        int y2 = 260;
        graphics.drawString(this.font, "Xray Blocks:", this.width - 150, y2, 0xFFAA00);
        y2 += 12;
        for (ResourceLocation rl : EspConfig.xrayBlocks) {
            if (y2 > this.height - 40) break;
            graphics.drawString(this.font, "  " + rl.toString(), this.width - 150, y2, 0xCCCCCC);
            y2 += 10;
        }

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
