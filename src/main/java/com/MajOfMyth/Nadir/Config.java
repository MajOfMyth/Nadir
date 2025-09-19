package com.MajOfMyth.Nadir;

import dev.shadowsoffire.placebo.config.Configuration;
import dev.shadowsoffire.placebo.network.PayloadProvider;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.Optional;

public class Config {
    public static boolean rarityRework = true;
    public static String[] disabledCompats = new String[] {};
    public static String[] disabledGems = new String[] {};
    public static String[] disabledAffixes = new String[] {};

    public static void load(Configuration c) {
        c.setTitle("Nadir Config THESE DON'T EXIST YET! I'll make them eventually:tm:");

        rarityRework = c.getBoolean(
                "Enable Rarity Rework",
                "features",
                rarityRework,
                "Enable the Rarity Rework?"
        );

        disabledCompats = c.getStringList(
                "Disabled Compats",
                "features",
                disabledCompats,
                "List of mods to disable custom gems/affixes for."
        );

        disabledGems = c.getStringList(
                "Disabled Gems",
                "features",
                disabledGems,
                "List of specific Nadir/Compat gems to disable."
        );

        disabledAffixes = c.getStringList(
                "Disabled Affixes",
                "features",
                disabledAffixes,
                "List of specific Nadir/Compat Affixes to disable."
        );
    }

    public static record ConfigPayload(boolean rarityRework) implements CustomPacketPayload {

        public static final Type<ConfigPayload> TYPE = new Type<>(Nadir.loc("config"));

        public static final StreamCodec<RegistryFriendlyByteBuf, ConfigPayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.BOOL, ConfigPayload::rarityRework,
                ConfigPayload::new
        );

        public ConfigPayload() {
            this(Config.rarityRework);
        }

        @Override
        public Type<ConfigPayload> type() {
            return TYPE;
        }

        public static class Provider implements PayloadProvider<ConfigPayload> {

            @Override
            public Type<ConfigPayload> getType() {
                return TYPE;
            }

            @Override
            public StreamCodec<? super RegistryFriendlyByteBuf, ConfigPayload> getCodec() {
                return CODEC;
            }

            @Override
            public void handle(ConfigPayload msg, IPayloadContext ctx) {
                Config.rarityRework = msg.rarityRework;
            }

            @Override
            public List<ConnectionProtocol> getSupportedProtocols() {
                return List.of(ConnectionProtocol.PLAY);
            }

            @Override
            public Optional<PacketFlow> getFlow() {
                return Optional.of(PacketFlow.CLIENTBOUND);
            }

            @Override
            public String getVersion() {
                return "1";
            }

        }

    }
}


/*
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_RARITY_REWORK = BUILDER
            .comment("Enable Rarity Rework?")
            .define("rarityRework", true);

    public static final ModConfigSpec.BooleanValue DISABLE_NADIR_GEMS = BUILDER
            .comment("Disable all Cross-Mod/Nadir Gems?")
            .define("nadirGems", false);

    public static final ModConfigSpec.BooleanValue DISABLE_NADIR_AFFIXES = BUILDER
            .comment("Disable all Cross-Mod/Nadir Affixes?")
            .define("nadirAffixes", false);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> DISABLED_GEMS = BUILDER
            .comment("Disable specific Cross-Mod/Nadir gems: ")
            .defineListAllowEmpty("disabledGems", List.of(), );

    static final ModConfigSpec SPEC = BUILDER.build();

    public static final Map<String, ModConfigSpec.BooleanValue> BOOLEAN_CONFIG_MAP = Map.of(
            "rarityRework", ENABLE_RARITY_REWORK,
            "nadirGems", DISABLE_NADIR_GEMS,
            "nadirAffixes", DISABLE_NADIR_AFFIXES
    );
    public static final Map<String, ModConfigSpec.ConfigValue<List<? extends String>>> STRING_LIST_CONFIG_MAP = Map.of(

    );



    public static boolean getBoolean(String configKey) {
        return Config.BOOLEAN_CONFIG_MAP.get(configKey).getAsBoolean();
    }

    public static List<? extends String> getList(String configKey) {
        return Config.STRING_LIST_CONFIG_MAP.get(configKey).get();
    }
}
*/
