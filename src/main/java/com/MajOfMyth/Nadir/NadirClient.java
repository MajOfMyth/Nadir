package com.MajOfMyth.Nadir;

import com.MajOfMyth.Nadir.block.NadirReforgingTableTileRenderer;
import com.MajOfMyth.Nadir.screen.NadirReforgingScreen;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.io.File;

@EventBusSubscriber(modid = Nadir.MODID, value = Dist.CLIENT)
public class NadirClient {

    @SubscribeEvent
    public static void setup(FMLClientSetupEvent e) {
        e.enqueueWork(() -> {
            BlockEntityRenderers.register(Nad.Tiles.NADIR_REFORGING_TABLE, k -> new NadirReforgingTableTileRenderer());
        });
    }

    @SubscribeEvent
    public static void screens(RegisterMenuScreensEvent e) {
        e.register(Nad.Menus.NADIR_REFORGING, NadirReforgingScreen::new);
    }

    @SubscribeEvent
    public static void models(ModelEvent.RegisterAdditional e) {
        e.register(NadirReforgingTableTileRenderer.HAMMER);
    }
}