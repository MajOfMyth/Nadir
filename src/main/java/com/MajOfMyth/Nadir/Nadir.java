package com.MajOfMyth.Nadir;

import com.MajOfMyth.Nadir.color.NColors;
import com.mojang.serialization.MapCodec;
import dev.shadowsoffire.apotheosis.Apoth;
import dev.shadowsoffire.placebo.config.Configuration;
import dev.shadowsoffire.placebo.network.PayloadHelper;
import dev.shadowsoffire.placebo.tabs.TabFillingRegistry;
import dev.shadowsoffire.placebo.util.PlaceboUtil;
import dev.shadowsoffire.placebo.util.RunnableReloader;
import dev.shadowsoffire.placebo.util.data.DynamicRegistryProvider;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.slf4j.Logger;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import dev.shadowsoffire.placebo.datagen.DataGenBuilder;
import com.MajOfMyth.Nadir.data.*;
import dev.shadowsoffire.apotheosis.data.RarityProvider;
import org.slf4j.LoggerFactory;

import java.io.File;

@Mod(Nadir.MODID)
public class Nadir {
    public static final String MODID = "nadir";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS =
            DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, Nadir.MODID);

    public Nadir(IEventBus modEventBus) {
        modEventBus.register(this);
        Nad.bootstrap(modEventBus);
        modEventBus.addListener(this::data);
        CONDITION_CODECS.register(modEventBus);
    }

    @SubscribeEvent
    public void setup(FMLCommonSetupEvent e) {
        e.enqueueWork(() -> {
            PlaceboUtil.registerCustomColor(NColors.STELLAR);
            PlaceboUtil.registerCustomColor(NColors.DIVINE);
            PlaceboUtil.registerCustomColor(NColors.ESOTERIC);
            TabFillingRegistry.register(Apoth.Tabs.ADVENTURE.getKey(),
                    Nad.Items.WORTHLESS_MATERIAL,
                    Nad.Items.LEGENDARY_MATERIAL,
                    Nad.Items.ANCIENT_MATERIAL,
                    Nad.Items.FORGOTTEN_MATERIAL,
                    Nad.Items.PRIMAL_MATERIAL,
                    Nad.Items.STELLAR_MATERIAL,
                    Nad.Items.DIVINE_MATERIAL,
                    Nad.Items.ESOTERIC_MATERIAL,
                    Nad.Items.CATACLYSMIC_MATERIAL,
                    Nad.Items.ANCIENT_REFORGING_TABLE,
                    Nad.Items.DIVINE_REFORGING_TABLE,
                    Nad.Items.CATACLYSMIC_REFORGING_TABLE
            );
        });
        PayloadHelper.registerPayload(new Config.ConfigPayload.Provider());
        NeoForge.EVENT_BUS.register(new NadirEvents());
        loadConfig(true);
        NeoForge.EVENT_BUS.addListener(AddReloadListenerEvent.class, event -> event.addListener(RunnableReloader.of(() -> loadConfig(false))));
    }

    public void data(GatherDataEvent e) {
        DataGenBuilder.create(MODID)
                .provider(DynamicRegistryProvider.runSilently(RarityProvider::new))
                .provider(NLootProvider::create)
                .provider(NRecipeProvider::new)
                .provider(NRarityProvider::new)
                .provider(NAffixProvider::new)
                .provider(NInvaderProvider::new)
                .build(e);

        //im like 78% sure this is not necessary but ill remove it later :3
        Object2IntOpenHashMap<String> map = (Object2IntOpenHashMap<String>) DataProvider.FIXED_ORDER_FIELDS;
        map.put("nadir:worthless", 0);
        map.put("nadir:legendary", 6);
        map.put("nadir:ancient", 7);
        map.put("nadir:forgotten", 8);
        map.put("nadir:primal", 9);
        map.put("nadir:stellar", 10);
        map.put("nadir:divine", 11);
        map.put("nadir:esoteric", 12);
        map.put("nadir:cataclysmic", 13);
    }

    public static void loadConfig(boolean firstLoad) {
        Configuration config = new Configuration(getConfigFile(MODID));
        Config.load(config);
        if (firstLoad && config.hasChanged()) {
            config.save();
        }
    }

    public static File getConfigFile(String path) {
        return new File(FMLPaths.CONFIGDIR.get().toFile(), path + ".cfg");
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
