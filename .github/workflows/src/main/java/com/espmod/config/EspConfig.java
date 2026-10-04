package com.espmod.config;

import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class EspConfig {

    public static boolean xrayEnabled = false;
    public static boolean blockEspEnabled = false;
    public static boolean storageEspEnabled = false;
    public static boolean spawnerEspEnabled = false;

    public static float[] blockEspOutlineColor = {1.0f, 0.0f, 0.0f, 1.0f};
    public static float[] blockEspFillColor = {1.0f, 0.0f, 0.0f, 0.15f};
    public static float[] storageEspOutlineColor = {1.0f, 1.0f, 0.0f, 1.0f};
    public static float[] storageEspFillColor = {1.0f, 1.0f, 0.0f, 0.15f};
    public static float[] spawnerEspOutlineColor = {0.0f, 1.0f, 1.0f, 1.0f};
    public static float[] spawnerEspFillColor = {0.0f, 1.0f, 1.0f, 0.15f};

    public static Set<ResourceLocation> blockEspTargets = new HashSet<>(Arrays.asList(
            ResourceLocation.parse("minecraft:diamond_ore"),
            ResourceLocation.parse("minecraft:deepslate_diamond_ore"),
            ResourceLocation.parse("minecraft:gold_ore"),
            ResourceLocation.parse("minecraft:deepslate_gold_ore"),
            ResourceLocation.parse("minecraft:iron_ore"),
            ResourceLocation.parse("minecraft:deepslate_iron_ore"),
            ResourceLocation.parse("minecraft:coal_ore"),
            ResourceLocation.parse("minecraft:deepslate_coal_ore"),
            ResourceLocation.parse("minecraft:ancient_debris"),
            ResourceLocation.parse("minecraft:emerald_ore"),
            ResourceLocation.parse("minecraft:deepslate_emerald_ore"),
            ResourceLocation.parse("minecraft:redstone_ore"),
            ResourceLocation.parse("minecraft:deepslate_redstone_ore"),
            ResourceLocation.parse("minecraft:lapis_ore"),
            ResourceLocation.parse("minecraft:deepslate_lapis_ore"),
            ResourceLocation.parse("minecraft:copper_ore"),
            ResourceLocation.parse("minecraft:deepslate_copper_ore")
    ));

    public static int blockEspRange = 64;
    public static int storageEspRange = 64;
    public static int spawnerEspRange = 64;

    public static Set<ResourceLocation> xrayBlocks = new HashSet<>(Arrays.asList(
            ResourceLocation.parse("minecraft:diamond_ore"),
            ResourceLocation.parse("minecraft:deepslate_diamond_ore"),
            ResourceLocation.parse("minecraft:emerald_ore"),
            ResourceLocation.parse("minecraft:deepslate_emerald_ore"),
            ResourceLocation.parse("minecraft:ancient_debris")
    ));

    public static double freecamSpeed = 1.0;
    public static boolean freecamNoClip = true;

    public static void load() {
    }

    public static void save() {
    }
}
