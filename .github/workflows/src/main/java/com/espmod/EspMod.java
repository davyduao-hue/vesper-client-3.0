package com.espmod;

import com.espmod.config.EspConfig;
import com.espmod.freecam.FreecamHandler;
import com.espmod.gui.EspConfigScreen;
import com.espmod.render.*;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

@Mod(EspMod.MOD_ID)
@Mod.EventBusSubscriber(modid = EspMod.MOD_ID, value = Dist.CLIENT)
public class EspMod {

    public static final String MOD_ID = "espmod";

    public static final KeyMapping TOGGLE_XRAY = new KeyMapping(
            "key.espmod.toggle_xray", InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_X, "category.espmod");
    public static final KeyMapping TOGGLE_BLOCK_ESP = new KeyMapping(
            "key.espmod.toggle_block_esp", InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B, "category.espmod");
    public static final KeyMapping TOGGLE_STORAGE_ESP = new KeyMapping(
            "key.espmod.toggle_storage_esp", InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_C, "category.espmod");
    public static final KeyMapping TOGGLE_SPAWNER_ESP = new KeyMapping(
            "key.espmod.toggle_spawner_esp", InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V, "category.espmod");
    public static final KeyMapping TOGGLE_FREECAM = new KeyMapping(
            "key.espmod.toggle_freecam", InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_F6, "category.espmod");
    public static final KeyMapping OPEN_CONFIG = new KeyMapping(
            "key.espmod.open_config", InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT, "category.espmod");

    public EspMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerKeys);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new BlockEspRenderer());
        MinecraftForge.EVENT_BUS.register(new StorageEspRenderer());
        MinecraftForge.EVENT_BUS.register(new SpawnerEspRenderer());
        MinecraftForge.EVENT_BUS.register(new XrayHandler());
        MinecraftForge.EVENT_BUS.register(new FreecamHandler());
        EspConfig.load();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
    }

    private void registerKeys(final RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_XRAY);
        event.register(TOGGLE_BLOCK_ESP);
        event.register(TOGGLE_STORAGE_ESP);
        event.register(TOGGLE_SPAWNER_ESP);
        event.register(TOGGLE_FREECAM);
        event.register(OPEN_CONFIG);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        while (TOGGLE_XRAY.consumeClick()) EspConfig.xrayEnabled = !EspConfig.xrayEnabled;
        while (TOGGLE_BLOCK_ESP.consumeClick()) EspConfig.blockEspEnabled = !EspConfig.blockEspEnabled;
        while (TOGGLE_STORAGE_ESP.consumeClick()) EspConfig.storageEspEnabled = !EspConfig.storageEspEnabled;
        while (TOGGLE_SPAWNER_ESP.consumeClick()) EspConfig.spawnerEspEnabled = !EspConfig.spawnerEspEnabled;
        while (TOGGLE_FREECAM.consumeClick()) FreecamHandler.toggle();
        while (OPEN_CONFIG.consumeClick()) Minecraft.getInstance().setScreen(new EspConfigScreen());

        FreecamHandler.tick();
    }
}
