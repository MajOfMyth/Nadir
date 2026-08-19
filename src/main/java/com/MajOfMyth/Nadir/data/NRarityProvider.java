package com.MajOfMyth.Nadir.data;

import com.MajOfMyth.Nadir.Nad;
import com.MajOfMyth.Nadir.Nadir;
import com.MajOfMyth.Nadir.color.NColors;
import dev.shadowsoffire.apotheosis.Apoth;
import dev.shadowsoffire.apotheosis.Apotheosis;
import dev.shadowsoffire.apotheosis.affix.AffixType;
import dev.shadowsoffire.apotheosis.loot.LootRarity;
import dev.shadowsoffire.apotheosis.loot.LootRule;
import dev.shadowsoffire.apotheosis.loot.RarityRegistry;
import dev.shadowsoffire.apotheosis.tiers.TieredWeights;
import dev.shadowsoffire.apotheosis.tiers.WorldTier;
import dev.shadowsoffire.placebo.color.GradientColor;
import dev.shadowsoffire.placebo.util.data.DynamicRegistryProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Unbreakable;

import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

public class NRarityProvider extends DynamicRegistryProvider<LootRarity> {


    public NRarityProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, RarityRegistry.INSTANCE);
    }

    @Override
    public String getName() {
        return "Nadir Rarities";
    }

    @Override
    public void generate() {
        this.addNadir("worthless", TextColor.fromRgb(0xFFFFFF), Nad.Items.WORTHLESS_MATERIAL, b -> b
                .sortIndex(200)
                .weights(TieredWeights.builder()
                        .with(WorldTier.HAVEN, 60, 0)
                        .with(WorldTier.FRONTIER, 0, 0)
                        .with(WorldTier.ASCENT, 0, 0)
                        .with(WorldTier.SUMMIT, 0, 0)
                        .with(WorldTier.PINNACLE, 0, 0))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .renderData(c -> c
                        .beamHeight(0)));

        this.addRarity("common", TextColor.fromRgb(0x808080), Apoth.Items.MYSTERIOUS_SCRAP_METAL, b -> b
                .sortIndex(300)
                .weights(TieredWeights.builder()
                        .with(WorldTier.HAVEN, 25, 1)
                        .with(WorldTier.FRONTIER, 0, 0)
                        .with(WorldTier.ASCENT, 0, 0)
                        .with(WorldTier.SUMMIT, 0, 0)
                        .with(WorldTier.PINNACLE, 0, 0))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.ChancedLootRule(0.25F, new LootRule.AffixLootRule(AffixType.STAT)))
                .renderData(c -> c
                        .beamHeight(0)));

        this.addRarity("uncommon", TextColor.fromRgb(0x33FF33), Apoth.Items.TIMEWORN_FABRIC, b -> b
                .sortIndex(400)
                .weights(TieredWeights.builder()
                        .with(WorldTier.HAVEN, 14, 2)
                        .with(WorldTier.FRONTIER, 0, 0)
                        .with(WorldTier.ASCENT, 0, 0)
                        .with(WorldTier.SUMMIT, 0, 0)
                        .with(WorldTier.PINNACLE, 0, 0))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.ChancedLootRule(0.5F, new LootRule.AffixLootRule(AffixType.BASIC_EFFECT)))
                .rule(new LootRule.SocketLootRule(0, 1))
                .renderData(c -> c
                        .beamHeight(0)
                        .shadow(d -> d
                                .alpha(0xAF)
                                .texture(Apotheosis.loc("textures/rarity/shadow_t1.png"))
                                .frames(20)
                                .frameTime(1.5F))));

        this.addRarity("rare", TextColor.fromRgb(0x5555FF), Apoth.Items.LUMINOUS_CRYSTAL_SHARD, b -> b
                .sortIndex(500)
                .weights(TieredWeights.builder()
                        .with(WorldTier.HAVEN, 1, 3)
                        .with(WorldTier.FRONTIER, 800, 0)
                        .with(WorldTier.ASCENT, 0, 0)
                        .with(WorldTier.SUMMIT, 0, 0)
                        .with(WorldTier.PINNACLE, 0, 0))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.ChancedLootRule(0.35F, new LootRule.AffixLootRule(AffixType.BASIC_EFFECT)))
                .rule(new LootRule.SocketLootRule(0, 2))
                .rule(new LootRule.DurabilityLootRule(0.1F, 0.25F))
                .renderData(c -> c
                        .beamHeight(2.5F)
                        .glowRadius(0)
                        .shadow(d -> d
                                .texture(Apotheosis.loc("textures/rarity/shadow_t2.png"))
                                .frames(20)
                                .frameTime(1.5F)))

        );

        this.addRarity("epic", TextColor.fromRgb(0xBB00BB), Apoth.Items.ARCANE_SANDS, b -> b
                .sortIndex(600)
                .weights(TieredWeights.builder()
                        .with(WorldTier.HAVEN, 0, 0)
                        .with(WorldTier.FRONTIER, 150, 1)
                        .with(WorldTier.ASCENT, 0, 0)
                        .with(WorldTier.SUMMIT, 0, 0)
                        .with(WorldTier.PINNACLE, 0, 0))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.ChancedLootRule(0.25F, new LootRule.AffixLootRule(AffixType.BASIC_EFFECT)))
                .rule(new LootRule.AffixLootRule(AffixType.ABILITY))
                .rule(new LootRule.SocketLootRule(1, 3))
                .rule(new LootRule.DurabilityLootRule(0.25F, 0.55F))
                .renderData(c -> c
                        .beamHeight(3F)
                        .shadow(d -> d
                                .texture(Apotheosis.loc("textures/rarity/shadow_t3.png"))
                                .frames(20)
                                .size(0.4F)
                                .frameTime(1.5F))
                        .particle(true))

        );

        this.addRarity("mythic", TextColor.fromRgb(0xED7014), Apoth.Items.GODFORGED_PEARL, b -> b
                .sortIndex(700)
                .weights(TieredWeights.builder()
                        .with(WorldTier.HAVEN, 0, 0)
                        .with(WorldTier.FRONTIER, 49, 2)
                        .with(WorldTier.ASCENT, 0, 0)
                        .with(WorldTier.SUMMIT, 0, 0)
                        .with(WorldTier.PINNACLE, 0, 0))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.ABILITY))
                .rule(new LootRule.SelectLootRule(0.95F, // 95% chance for 1-3 sockets, 5% chance for guaranteed 4 sockets.
                        new LootRule.SocketLootRule(1, 3),
                        new LootRule.SocketLootRule(4, 4)))
                .rule(new LootRule.SelectLootRule(0.99F, // 99% chance to roll a durability bonus, 1% to be unbreakable.
                        new LootRule.DurabilityLootRule(0.45F, 0.75F),
                        new LootRule.ComponentLootRule(DataComponentPatch.builder()
                                .set(DataComponents.UNBREAKABLE, new Unbreakable(true))
                                .remove(Apoth.Components.DURABILITY_BONUS)
                                .build())))
                .renderData(c -> c
                        .shadow(d -> d
                                .texture(Apotheosis.loc("textures/rarity/shadow_t4.png"))
                                .frames(7)
                                .size(0.4F)
                                .frameTime(1.5F))
                        .particle(true))

        );

        this.addNadir("legendary", TextColor.fromRgb(0xFFDE91), Nad.Items.LEGENDARY_MATERIAL, b -> b
                .sortIndex(800)
                .weights(TieredWeights.builder()
                        .with(WorldTier.HAVEN, 0, 0)
                        .with(WorldTier.FRONTIER, 1, 3)
                        .with(WorldTier.ASCENT, 800, 0)
                        .with(WorldTier.SUMMIT, 0, 0)
                        .with(WorldTier.PINNACLE, 0, 0))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.ABILITY))
                .rule(new LootRule.SelectLootRule(0.95F,
                        new LootRule.SocketLootRule(1, 3),
                        new LootRule.SocketLootRule(4, 4)))
                .rule(new LootRule.SelectLootRule(0.95F,
                        new LootRule.DurabilityLootRule(0.53F, 0.83F),
                        new LootRule.ComponentLootRule(DataComponentPatch.builder()
                                .set(DataComponents.UNBREAKABLE, new Unbreakable(true))
                                .remove(Apoth.Components.DURABILITY_BONUS)
                                .build())))
                .renderData(c -> c
                        .beamHeight(0)));

        this.addNadir("ancient", TextColor.fromRgb(0xAA0000), Nad.Items.ANCIENT_MATERIAL, b -> b
                .sortIndex(900)
                .weights(TieredWeights.builder()
                        .with(WorldTier.HAVEN, 0, 0)
                        .with(WorldTier.FRONTIER, 0, 0)
                        .with(WorldTier.ASCENT, 150, 1)
                        .with(WorldTier.SUMMIT, 0, 0)
                        .with(WorldTier.PINNACLE, 0, 0))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.ABILITY))
                .rule(new LootRule.SelectLootRule(0.95F,
                        new LootRule.SocketLootRule(1, 3),
                        new LootRule.SocketLootRule(4, 4)))
                .rule(new LootRule.SelectLootRule(0.90F,
                        new LootRule.DurabilityLootRule(0.56F, 0.86F),
                        new LootRule.ComponentLootRule(DataComponentPatch.builder()
                                .set(DataComponents.UNBREAKABLE, new Unbreakable(true))
                                .remove(Apoth.Components.DURABILITY_BONUS)
                                .build())))
                .renderData(c -> c
                        .beamHeight(0)));

        this.addNadir("forgotten", TextColor.fromRgb(0x824D31), Nad.Items.FORGOTTEN_MATERIAL, b -> b
                .sortIndex(1000)
                .weights(TieredWeights.builder()
                        .with(WorldTier.HAVEN, 0, 0)
                        .with(WorldTier.FRONTIER, 0, 0)
                        .with(WorldTier.ASCENT, 49, 2)
                        .with(WorldTier.SUMMIT, 0, 0)
                        .with(WorldTier.PINNACLE, 0, 0))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.ABILITY))
                .rule(new LootRule.SelectLootRule(0.95F,
                        new LootRule.SocketLootRule(2, 4),
                        new LootRule.SocketLootRule(5, 5)))
                .rule(new LootRule.SelectLootRule(0.85F,
                        new LootRule.DurabilityLootRule(0.60F, 0.90F),
                        new LootRule.ComponentLootRule(DataComponentPatch.builder()
                                .set(DataComponents.UNBREAKABLE, new Unbreakable(true))
                                .remove(Apoth.Components.DURABILITY_BONUS)
                                .build())))
                .renderData(c -> c
                        .beamHeight(0)));

        this.addNadir("primal", TextColor.fromRgb(0xFF55FF), Nad.Items.PRIMAL_MATERIAL, b -> b
                .sortIndex(1100)
                .weights(TieredWeights.builder()
                        .with(WorldTier.HAVEN, 0, 0)
                        .with(WorldTier.FRONTIER, 0, 0)
                        .with(WorldTier.ASCENT, 1, 3)
                        .with(WorldTier.SUMMIT, 8500, 0)
                        .with(WorldTier.PINNACLE, 0, 0))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.ABILITY))
                .rule(new LootRule.SelectLootRule(0.95F,
                        new LootRule.SocketLootRule(2, 4),
                        new LootRule.SocketLootRule(5, 5)))
                .rule(new LootRule.SelectLootRule(0.80F,
                        new LootRule.DurabilityLootRule(0.63F, 0.93F),
                        new LootRule.ComponentLootRule(DataComponentPatch.builder()
                                .set(DataComponents.UNBREAKABLE, new Unbreakable(true))
                                .remove(Apoth.Components.DURABILITY_BONUS)
                                .build())))
                .renderData(c -> c
                        .beamHeight(0)));

        this.addNadir("stellar", NColors.STELLAR, Nad.Items.STELLAR_MATERIAL, b -> b
                .sortIndex(1200)
                .weights(TieredWeights.builder()
                        .with(WorldTier.HAVEN, 0, 0)
                        .with(WorldTier.FRONTIER, 0, 0)
                        .with(WorldTier.ASCENT, 0, 0)
                        .with(WorldTier.SUMMIT, 1000, 1)
                        .with(WorldTier.PINNACLE, 8500, 0))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.ABILITY))
                .rule(new LootRule.AffixLootRule(AffixType.ABILITY))
                .rule(new LootRule.SelectLootRule(0.95F,
                        new LootRule.SocketLootRule(2, 4),
                        new LootRule.SocketLootRule(5, 5)))
                .rule(new LootRule.SelectLootRule(0.75F,
                        new LootRule.DurabilityLootRule(0.66F, 0.96F),
                        new LootRule.ComponentLootRule(DataComponentPatch.builder()
                                .set(DataComponents.UNBREAKABLE, new Unbreakable(true))
                                .remove(Apoth.Components.DURABILITY_BONUS)
                                .build())))
                .renderData(c -> c
                        .beamHeight(0)));

        this.addNadir("divine", NColors.DIVINE, Nad.Items.DIVINE_MATERIAL, b -> b
                .sortIndex(1300)
                .weights(TieredWeights.builder()
                        .with(WorldTier.HAVEN, 0, 0)
                        .with(WorldTier.FRONTIER, 0, 0)
                        .with(WorldTier.ASCENT, 0, 0)
                        .with(WorldTier.SUMMIT, 499, 2)
                        .with(WorldTier.PINNACLE, 1000, 1))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.ABILITY))
                .rule(new LootRule.AffixLootRule(AffixType.ABILITY))
                .rule(new LootRule.SelectLootRule(0.95F,
                        new LootRule.SocketLootRule(3, 5),
                        new LootRule.SocketLootRule(6, 6)))
                .rule(new LootRule.SelectLootRule(0.70F,
                        new LootRule.DurabilityLootRule(0.70F, 1),
                        new LootRule.ComponentLootRule(DataComponentPatch.builder()
                                .set(DataComponents.UNBREAKABLE, new Unbreakable(true))
                                .remove(Apoth.Components.DURABILITY_BONUS)
                                .build())))
                .renderData(c -> c
                        .beamHeight(0)));

        this.addNadir("esoteric", NColors.ESOTERIC, Nad.Items.ESOTERIC_MATERIAL, b -> b
                .sortIndex(1400)
                .weights(TieredWeights.builder()
                        .with(WorldTier.HAVEN, 0, 0)
                        .with(WorldTier.FRONTIER, 0, 0)
                        .with(WorldTier.ASCENT, 0, 0)
                        .with(WorldTier.SUMMIT, 1, 3)
                        .with(WorldTier.PINNACLE, 499, 2))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.ABILITY))
                .rule(new LootRule.AffixLootRule(AffixType.ABILITY))
                .rule(new LootRule.SelectLootRule(0.95F,
                        new LootRule.SocketLootRule(3, 5),
                        new LootRule.SocketLootRule(6, 6)))
                .rule(new LootRule.SelectLootRule(0.65F,
                        new LootRule.DurabilityLootRule(0.75F, 1),
                        new LootRule.ComponentLootRule(DataComponentPatch.builder()
                                .set(DataComponents.UNBREAKABLE, new Unbreakable(true))
                                .remove(Apoth.Components.DURABILITY_BONUS)
                                .build())))
                .renderData(c -> c
                        .beamHeight(0)));

        this.addNadir("cataclysmic", GradientColor.RAINBOW, Nad.Items.CATACLYSMIC_MATERIAL, b -> b
                .sortIndex(1500)
                .weights(TieredWeights.builder()
                        .with(WorldTier.HAVEN, 0, 0)
                        .with(WorldTier.FRONTIER, 0, 0)
                        .with(WorldTier.ASCENT, 0, 0)
                        .with(WorldTier.SUMMIT, 0, 0)
                        .with(WorldTier.PINNACLE, 1, 3))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.STAT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.BASIC_EFFECT))
                .rule(new LootRule.AffixLootRule(AffixType.ABILITY))
                .rule(new LootRule.AffixLootRule(AffixType.ABILITY))
                .rule(new LootRule.SelectLootRule(0.95F,
                        new LootRule.SocketLootRule(3, 5),
                        new LootRule.SocketLootRule(6, 6)))
                .rule(new LootRule.SelectLootRule(0.60F,
                        new LootRule.DurabilityLootRule(0.80F, 1),
                        new LootRule.ComponentLootRule(DataComponentPatch.builder()
                                .set(DataComponents.UNBREAKABLE, new Unbreakable(true))
                                .remove(Apoth.Components.DURABILITY_BONUS)
                                .build())))
                .renderData(c -> c
                        .beamHeight(0)));
    }

    static <T> LootRule componentRule(DataComponentType<T> type, T value) {
        return new LootRule.ComponentLootRule(DataComponentPatch.builder().set(type, value).build());
    }

    void addRarity(String id, TextColor color, Holder<Item> material, UnaryOperator<LootRarity.Builder> config) {
        this.add(Apotheosis.loc(id), config.apply(builder(color, material)).build());
        //this.addConditionally(Apotheosis.loc(id), config.apply(builder(color, material)).build(), new rarityRework());
    }

    void addNadir(String id, TextColor color, Holder<Item> material, UnaryOperator<LootRarity.Builder> config) {
        this.add(Nadir.loc(id), config.apply(builder(color, material)).build());
        //this.addConditionally(Nadir.loc(id), config.apply(builder(color, material)).build(), new rarityRework());
    }

    public static LootRarity.Builder builder(TextColor color, Holder<Item> material) {
        return new LootRarity.Builder(color, material);
    }
}
