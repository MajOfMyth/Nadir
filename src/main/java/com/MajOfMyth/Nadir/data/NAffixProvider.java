package com.MajOfMyth.Nadir.data;


import com.MajOfMyth.Nadir.Nadir;
import dev.shadowsoffire.apotheosis.Apotheosis;
import dev.shadowsoffire.apotheosis.loot.LootCategory;
import dev.shadowsoffire.apotheosis.affix.*;
import dev.shadowsoffire.apotheosis.affix.effect.*;
import dev.shadowsoffire.apotheosis.Apoth;
import dev.shadowsoffire.apotheosis.loot.LootRarity;
import dev.shadowsoffire.apotheosis.loot.RarityRegistry;
import dev.shadowsoffire.apotheosis.tiers.TieredWeights;
import dev.shadowsoffire.apotheosis.tiers.WorldTier;
import dev.shadowsoffire.apotheosis.util.ApothMiscUtil;
import dev.shadowsoffire.apothic_attributes.api.ALObjects;
import dev.shadowsoffire.placebo.reload.DynamicHolder;
import dev.shadowsoffire.placebo.util.StepFunction;
import dev.shadowsoffire.placebo.util.data.DynamicRegistryProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.minecraft.core.HolderLookup.RegistryLookup;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

public class NAffixProvider extends DynamicRegistryProvider<Affix> {

    public static final int DEFAULT_WEIGHT = 25;
    public static final float DEFAULT_QUALITY = 0.1F;

    public static final LootCategory[] ARMOR = { Apoth.LootCategories.HELMET, Apoth.LootCategories.CHESTPLATE, Apoth.LootCategories.LEGGINGS, Apoth.LootCategories.BOOTS };

    public NAffixProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, AffixRegistry.INSTANCE);
    }

    @Override
    public String getName() {
        return "Nadir Affixes";
    }

    @Override
    public void generate() {
        LootRarity worthless = rarity("worthless");
        //LootRarity common = rarity("common");
        //LootRarity uncommon = rarity("uncommon");
        //LootRarity rare = rarity("rare");
        //LootRarity epic = rarity("epic");
        //LootRarity mythic = rarity("mythic");
        LootRarity legendary = rarity("legendary");
        LootRarity ancient = rarity("ancient");
        LootRarity forgotten = rarity("forgotten");
        LootRarity primal = rarity("primal");
        LootRarity stellar = rarity("stellar");
        LootRarity divine = rarity("divine");
        LootRarity esoteric = rarity("esoteric");
        LootRarity cataclysmic = rarity("cataclysmic");

        RegistryLookup<Enchantment> enchants = this.lookupProvider.join().lookup(Registries.ENCHANTMENT).get();


        //Capital 'F'. No 'F' on whole numbers. 0.X0F if it's a percentage, 0.XF if not.
        //if I messed up somewhere: lol

        // Generic Attributes
        this.addAttribute("generic", "lucky", Attributes.LUCK, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.BuiltInRegs.LOOT_CATEGORY.stream().filter(c -> c != Apoth.LootCategories.NONE).toArray(LootCategory[]::new))
                .step(0.25F)
                .value(worthless, 0.5F, 1F)
                .value(legendary, 4F, 8F)
                .value(ancient, 5F, 10F)
                .value(forgotten, 6F, 11F)
                .value(primal, 8F, 12F)
                .value(stellar, 9F, 13F)
                .value(divine, 10F, 14F)
                .value(esoteric, 12F, 16F)
                .value(cataclysmic, 14F, 18F));

        // Telepathic, which applies to a bunch of categories
        this.add(Nadir.loc("generic/telepathic"),
                new TelepathicAffix(
                        AffixDefinition.builder(AffixType.BASIC_EFFECT).weights(TieredWeights.forAllTiers(DEFAULT_WEIGHT, DEFAULT_QUALITY)).build(),
                        linkedSet(legendary,ancient,forgotten,primal,stellar,divine,esoteric,cataclysmic)));

        // Armor Attributes
        this.addAttribute("armor", "aquatic", NeoForgeMod.SWIM_SPEED, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BOOTS)
                .value(worthless, 0.1F, 0.2F)
                .value(legendary, 0.4F, 0.7F)
                .value(ancient, 0.4F, 0.7F)
                .value(forgotten, 0.5F, 0.8F)
                .value(primal, 0.5F, 0.8F)
                .value(stellar, 0.6F, 0.9F)
                .value(divine, 0.6F, 0.9F)
                .value(esoteric, 0.6F, 0.9F)
                .value(cataclysmic, 0.7F, 1));

        this.addAttribute("armor", "blessed", Attributes.MAX_HEALTH, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(ARMOR)
                .step(0.25F)
                .value(worthless, 1, 4)
                .value(legendary, 5, 9)
                .value(ancient, 6, 9)
                .value(forgotten, 6, 10)
                .value(primal, 7, 10)
                .value(stellar, 7, 11)
                .value(divine, 8, 11)
                .value(esoteric, 8, 12)
                .value(cataclysmic, 9, 12));

        this.addAttribute("armor", "elastic", Attributes.STEP_HEIGHT, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BOOTS)
                .step(0.25F)
                .value(worthless, 0.25F)
                .value(legendary, 1, 2)
                .value(ancient, 1, 2.5F)
                .value(forgotten, 1, 2.5F)
                .value(primal, 1.5F, 3)
                .value(stellar, 1.5F, 3)
                .value(divine, 1.5F, 3.5F)
                .value(esoteric, 1.5F, 3.5F)
                .value(cataclysmic, 2, 4));

        this.addAttribute("armor", "fortunate", Attributes.LUCK, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(ARMOR)
                .step(0.25F)
                .value(worthless, 0.25F, 1)
                .value(legendary, 3, 5)
                .value(ancient, 4, 6)
                .value(forgotten, 4, 6)
                .value(primal, 5, 7)
                .value(stellar, 5, 7)
                .value(divine, 6, 8)
                .value(esoteric, 6, 8)
                .value(cataclysmic, 7, 9));

        this.addAttribute("armor", "gravitational", Attributes.GRAVITY, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.CHESTPLATE)
                .step(-0.01F)
                .value(worthless, -0.05F, -0.10F)
                .value(legendary, -0.20F, -0.55F)
                .value(ancient, -0.25F, -0.60F)
                .value(forgotten, -0.25F, -0.65F)
                .value(primal, -0.30F, -0.70F)
                .value(stellar, -0.30F, -0.75F)
                .value(divine, -0.35F, -0.80F)
                .value(esoteric, -0.35F, -0.85F)
                .value(cataclysmic, -0.40F, -0.90F));

        this.addAttribute("armor", "ironforged", Attributes.ARMOR, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(ARMOR)
                .step(0.25F)
                .value(worthless, 0.5F, 1)
                .value(legendary, 4.5F, 10)
                .value(ancient, 5, 12)
                .value(forgotten, 8, 14)
                .value(primal, 10, 16)
                .value(stellar, 10, 18)
                .value(divine, 12, 20)
                .value(esoteric, 14, 22)
                .value(cataclysmic, 16, 24));

        this.addAttribute("armor", "adamantine", Attributes.ARMOR, AttributeModifier.Operation.ADD_MULTIPLIED_BASE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.CHESTPLATE, Apoth.LootCategories.LEGGINGS)
                .value(legendary, 0.25F, 0.40F)
                .value(ancient, 0.35F, 0.50F)
                .value(forgotten, 0.35F, 0.50F)
                .value(primal, 0.45F, 0.60F)
                .value(stellar, 0.45F, 0.60F)
                .value(divine, 0.55F, 0.70F)
                .value(esoteric, 0.55F, 0.70F)
                .value(cataclysmic, 0.65F, 0.80F));

        this.addAttribute("armor", "spiritual", ALObjects.Attributes.HEALING_RECEIVED, AttributeModifier.Operation.ADD_MULTIPLIED_BASE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.CHESTPLATE, Apoth.LootCategories.LEGGINGS)
                .value(legendary, 0.25F, 0.45F)
                .value(ancient, 0.30F, 0.50F)
                .value(forgotten, 0.35F, 0.55F)
                .value(primal, 0.40F, 0.60F)
                .value(stellar, 0.45F, 0.65F)
                .value(divine, 0.50F, 0.70F)
                .value(esoteric, 0.55F, 0.75F)
                .value(cataclysmic, 0.60F, 0.80F));

        this.addAttribute("armor", "stalwart", Attributes.KNOCKBACK_RESISTANCE, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(ARMOR)
                .value(legendary, 0.30F, 0.40F)
                .value(ancient, 0.35F, 0.45F)
                .value(forgotten, 0.40F, 0.50F)
                .value(primal, 0.45F, 0.55F)
                .value(stellar, 0.50F, 0.60F)
                .value(divine, 0.55F, 0.65F)
                .value(esoteric, 0.60F, 0.70F)
                .value(cataclysmic, 0.65F, 0.75F));

        this.addAttribute("armor", "steel_touched", Attributes.ARMOR_TOUGHNESS, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(ARMOR)
                .step(0.25F)
                .value(legendary, 2.5F, 8)
                .value(ancient, 3, 9)
                .value(forgotten, 2, 10)
                .value(primal, 2, 11)
                .value(stellar, 2, 12)
                .value(divine, 2, 13)
                .value(esoteric, 2, 14)
                .value(cataclysmic, 2, 15));

        this.addAttribute("armor", "windswept", Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.LEGGINGS, Apoth.LootCategories.BOOTS)
                .value(worthless, 0.05F, 0.10F)
                .value(legendary, 0.20F, 0.50F)
                .value(ancient, 0.25F, 0.55F)
                .value(forgotten, 0.25F, 0.60F)
                .value(primal, 0.30F, 0.65F)
                .value(stellar, 0.30F, 0.70F)
                .value(divine, 0.35F, 0.75F)
                .value(esoteric, 0.35F, 0.80F)
                .value(cataclysmic, 0.40F, 0.85F));

        this.addAttribute("armor", "winged", ALObjects.Attributes.ELYTRA_FLIGHT, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.CHESTPLATE)
                .value(legendary, 1)
                .value(ancient, 1)
                .value(forgotten, 1)
                .value(primal, 1)
                .value(stellar, 1)
                .value(divine, 1)
                .value(esoteric, 1)
                .value(cataclysmic, 1));

        this.addAttribute("armor", "unbound", NeoForgeMod.CREATIVE_FLIGHT, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, d -> d
                        .weights(TieredWeights.onlyFor(WorldTier.PINNACLE, 1, DEFAULT_QUALITY * 2F))
                        .exclusiveWith(afx("armor/attribute/winged")))
                .categories(Apoth.LootCategories.CHESTPLATE)
                .value(legendary, 1)
                .value(ancient, 1)
                .value(forgotten, 1)
                .value(primal, 1)
                .value(stellar, 1)
                .value(divine, 1)
                .value(esoteric, 1)
                .value(cataclysmic, 1));

        this.addAttribute("armor", "fireproof", Attributes.BURNING_TIME, AttributeModifier.Operation.ADD_MULTIPLIED_BASE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.HELMET)
                .step(-0.05F)
                .value(legendary, -1)
                .value(ancient, -1)
                .value(forgotten, -1)
                .value(primal, -1)
                .value(stellar, -1)
                .value(divine, -1)
                .value(esoteric, -1)
                .value(cataclysmic, -1));

        this.addAttribute("armor", "oxygenated", Attributes.OXYGEN_BONUS, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.HELMET)
                .value(legendary, 1.25F, 1.5F)
                .value(ancient, 1.5F, 2)
                .value(forgotten, 2, 2.5F)
                .value(primal, 2.5F, 3)
                .value(stellar, 3, 3.5F)
                .value(divine, 3.5F, 4)
                .value(esoteric, 4, 4.5F)
                .value(cataclysmic, 4.5F, 5));

        // Breaker Attributes
        this.addAttribute("breaker", "destructive", ALObjects.Attributes.MINING_SPEED, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BREAKER, Apoth.LootCategories.SHEARS)
                .value(worthless, 0.05F, 0.10F)
                .value(legendary, 0.55F, 0.90F)
                .value(ancient, 0.60F, 1)
                .value(forgotten, 0.60F, 1.10F)
                .value(primal, 0.70F, 1.20F)
                .value(stellar, 0.70F, 1.30F)
                .value(divine, 0.80F, 1.40F)
                .value(esoteric, 0.80F, 1.50F)
                .value(cataclysmic, 0.90F, 1.60F));

        this.addAttribute("breaker", "experienced", ALObjects.Attributes.EXPERIENCE_GAINED, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BREAKER)
                .value(worthless, 0.15F, 0.3F)
                .value(legendary, 0.55F, 0.7F)
                .value(ancient, 0.65F, 0.8F)
                .value(forgotten, 0.65F, 0.9F)
                .value(primal, 0.75F, 1)
                .value(stellar, 0.75F, 1.1F)
                .value(divine, 0.85F, 1.2F)
                .value(esoteric, 0.85F, 1.3F)
                .value(cataclysmic, 0.95F, 1.4F));

        this.addAttribute("breaker", "lengthy", Attributes.BLOCK_INTERACTION_RANGE, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BREAKER)
                .step(0.25F)
                .value(worthless, 0.25F, 0.5F)
                .value(legendary, 2, 5)
                .value(ancient, 2.5F, 5.5F)
                .value(forgotten, 3, 6)
                .value(primal, 3.5F, 6.5F)
                .value(stellar, 4, 7)
                .value(divine, 4.5F, 7.5F)
                .value(esoteric, 5, 8)
                .value(cataclysmic, 5.5F, 8.5F));

        this.addAttribute("breaker", "submerged", Attributes.SUBMERGED_MINING_SPEED, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BREAKER)
                .value(worthless, 0.05F, 0.15F)
                .value(legendary, 0.6F, 1)
                .value(ancient, 0.7F, 1.1F)
                .value(forgotten, 0.8F, 1.2F)
                .value(primal, 0.9F, 1.3F)
                .value(stellar, 1, 1.4F)
                .value(divine, 1.1F, 1.5F)
                .value(esoteric, 1.2F, 1.6F)
                .value(cataclysmic, 1.3F, 1.7F));

        // Ranged Attributes
        this.addAttribute("ranged", "agile", ALObjects.Attributes.DRAW_SPEED, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BOW)
                .value(worthless, 0.1F, 0.3F)
                .value(legendary, 0.6F, 0.7F)
                .value(ancient, 0.7F, 0.8F)
                .value(forgotten, 0.8F, 0.9F)
                .value(primal, 0.9F, 1)
                .value(stellar, 1, 1.1F)
                .value(divine, 1.1F, 1.2F)
                .value(esoteric, 1.2F, 1.3F)
                .value(cataclysmic, 1.3F, 1.4F));

        this.addAttribute("ranged", "elven", ALObjects.Attributes.ARROW_DAMAGE, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BOW, Apoth.LootCategories.TRIDENT)
                .value(worthless, 0.1F, 0.15F)
                .value(legendary, 0.3F, 0.45F)
                .value(ancient, 0.3F, 0.5F)
                .value(forgotten, 0.35F, 0.55F)
                .value(primal, 0.35F, 0.6F)
                .value(stellar, 0.4F, 0.65F)
                .value(divine, 0.4F, 0.7F)
                .value(esoteric, 0.45F, 0.75F)
                .value(cataclysmic, 0.45F, 0.8F));

        this.addAttribute("ranged", "streamlined", ALObjects.Attributes.ARROW_VELOCITY, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BOW, Apoth.LootCategories.TRIDENT)
                .value(worthless, 0.15F)
                .value(legendary, 0.15F, 0.4F)
                .value(ancient, 0.15F, 0.25F)
                .value(forgotten, 0.15F, 0.30F)
                .value(primal, 0.15F, 0.35F)
                .value(stellar, 0.15F, 0.4F)
                .value(divine, 0.15F, 0.45F)
                .value(esoteric, 0.15F, 0.5F)
                .value(cataclysmic, 0.15F, 0.55F));

        this.addAttribute("ranged", "windswept", Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BOW, Apoth.LootCategories.TRIDENT)
                .value(worthless, 0.1F, 0.2F)
                .value(legendary, 0.2F, 0.45F)
                .value(ancient, 0.2F, 0.5F)
                .value(forgotten, 0.2F, 0.55F)
                .value(primal, 0.2F, 0.6F)
                .value(stellar, 0.2F, 0.65F)
                .value(divine, 0.3F, 0.7F)
                .value(esoteric, 0.3F, 0.75F)
                .value(cataclysmic, 0.3F, 0.8F));

        // Shield Attributes
        this.addAttribute("shield", "ironforged", Attributes.ARMOR, AttributeModifier.Operation.ADD_MULTIPLIED_BASE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.SHIELD)
                .value(worthless, 0.05F, 0.1F)
                .value(legendary, 0.20F, 0.35F)
                .value(ancient, 0.25F, 0.35F)
                .value(forgotten, 0.25F, 0.40F)
                .value(primal, 0.30F, 0.45F)
                .value(stellar, 0.30F, 0.45F)
                .value(divine, 0.35F, 0.50F)
                .value(esoteric, 0.35F, 0.55F)
                .value(cataclysmic, 0.4F, 0.60F));

        this.addAttribute("shield", "stalwart", Attributes.KNOCKBACK_RESISTANCE, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.SHIELD)
                .value(worthless, 0.05F, 0.10F)
                .value(legendary, 0.25F, 0.35F)
                .value(ancient, 0.35F, 0.45F)
                .value(forgotten, 0.35F, 0.45F)
                .value(primal, 0.45F, 0.55F)
                .value(stellar, 0.45F, 0.55F)
                .value(divine, 0.50F, 0.60F)
                .value(esoteric, 0.50F, 0.60F)
                .value(cataclysmic, 0.60F, 0.70F));

        this.addAttribute("shield", "steel_touched", Attributes.ARMOR_TOUGHNESS, AttributeModifier.Operation.ADD_MULTIPLIED_BASE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.SHIELD)
                .value(legendary, 0.20F, 0.30F)
                .value(ancient, 0.25F, 0.40F)
                .value(forgotten, 0.25F, 0.40F)
                .value(primal, 0.30F, 0.50F)
                .value(stellar, 0.30F, 0.50F)
                .value(divine, 0.35F, 0.60F)
                .value(esoteric, 0.35F, 0.60F)
                .value(cataclysmic, 0.40F, 0.70F));

        // Melee Weapon Attributes
        this.addAttribute("melee", "vampiric", ALObjects.Attributes.LIFE_STEAL, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, d -> d
                        .weights(TieredWeights.forAllTiers(DEFAULT_WEIGHT, DEFAULT_QUALITY))
                        .exclusiveWith(afx("melee/attribute/berserking")))
                .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT)
                .value(worthless, 0.01F, 0.04F)
                .value(legendary, 0.12F, 0.18F)
                .value(ancient, 0.15F, 0.20F)
                .value(forgotten, 0.18F, 0.22F)
                .value(primal, 0.20F, 0.24F)
                .value(stellar, 0.22F, 0.26F)
                .value(divine, 0.24F, 0.28F)
                .value(esoteric, 0.26F, 0.30F)
                .value(cataclysmic, 0.28F, 0.32F));

        this.addAttribute("melee", "murderous", Attributes.ATTACK_DAMAGE, AttributeModifier.Operation.ADD_MULTIPLIED_BASE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT)
                .value(legendary, 0.26F, 0.60F)
                .value(ancient, 0.28F, 0.65F)
                .value(forgotten, 0.30F, 0.70F)
                .value(primal, 0.32F, 0.75F)
                .value(stellar, 0.34F, 0.80F)
                .value(divine, 0.36F, 0.85F)
                .value(esoteric, 0.38F, 0.90F)
                .value(cataclysmic, 0.40F, 0.95F));

        this.addAttribute("melee", "violent", Attributes.ATTACK_DAMAGE, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT)
                .step(0.25F)
                .value(worthless, 1, 2)
                .value(legendary, 6, 9)
                .value(ancient, 7, 10)
                .value(forgotten, 8, 11)
                .value(primal, 9, 12)
                .value(stellar, 10, 13)
                .value(divine, 11, 14)
                .value(esoteric, 12, 15)
                .value(cataclysmic, 13, 16));

        this.addAttribute("melee", "piercing", ALObjects.Attributes.ARMOR_PIERCE, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT, Apoth.LootCategories.BOW)
                .step(0.25F)
                .value(worthless, 1, 2)
                .value(legendary, 6, 14)
                .value(ancient, 7, 16)
                .value(forgotten, 8, 18)
                .value(primal, 9, 20)
                .value(stellar, 10, 22)
                .value(divine, 12, 24)
                .value(esoteric, 14, 26)
                .value(cataclysmic, 16, 28));

        this.addAttribute("weapon", "shredding", ALObjects.Attributes.ARMOR_SHRED, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT, Apoth.LootCategories.BOW)
                .value(worthless, 0.05F, 0.15F)
                .value(legendary, 0.35F, 0.45F)
                .value(ancient, 0.35F, 0.50F)
                .value(forgotten, 0.40F, 0.50F)
                .value(primal, 0.40F, 0.55F)
                .value(stellar, 0.45F, 0.55F)
                .value(divine, 0.45F, 0.60F)
                .value(esoteric, 0.50F, 0.60F)
                .value(cataclysmic, 0.50F, 0.65F));

        this.addAttribute("melee", "lacerating", ALObjects.Attributes.CRIT_DAMAGE, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT, Apoth.LootCategories.BOW)
                .value(worthless, 0.05F, 0.15F)
                .value(legendary, 0.25F, 0.40F)
                .value(ancient, 0.30F, 0.45F)
                .value(forgotten, 0.30F, 0.45F)
                .value(primal, 0.35F, 0.50F)
                .value(stellar, 0.35F, 0.50F)
                .value(divine, 0.40F, 0.55F)
                .value(esoteric, 0.40F, 0.55F)
                .value(cataclysmic, 0.45F, 0.60F));

        this.addAttribute("melee", "intricate", ALObjects.Attributes.CRIT_CHANCE, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT, Apoth.LootCategories.BOW)
                .value(worthless, 0.05F, 0.10F)
                .value(legendary, 0.25F, 0.55F)
                .value(ancient, 0.25F, 0.60F)
                .value(forgotten, 0.25F, 0.60F)
                .value(primal, 0.30F, 0.65F)
                .value(stellar, 0.30F, 0.65F)
                .value(divine, 0.30F, 0.70F)
                .value(esoteric, 0.35F, 0.70F)
                .value(cataclysmic, 0.35F, 0.75F));

        this.addAttribute("melee", "infernal", ALObjects.Attributes.FIRE_DAMAGE, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, d -> d
                        .weights(TieredWeights.forAllTiers(DEFAULT_WEIGHT, DEFAULT_QUALITY))
                        .exclusiveWith(afx("melee/attribute/glacial")))
                .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT)
                .step(0.25F)
                .value(legendary, 6, 11)
                .value(ancient, 6, 12)
                .value(forgotten, 8, 13)
                .value(primal, 8, 14)
                .value(stellar, 10, 15)
                .value(divine, 10, 16)
                .value(esoteric, 12, 17)
                .value(cataclysmic, 12, 18));

        this.addAttribute("melee", "graceful", Attributes.ATTACK_SPEED, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT)
                .value(worthless, 0.10F, 0.20F)
                .value(legendary, 0.40F, 0.85F)
                .value(ancient, 0.45F, 0.90F)
                .value(forgotten, 0.45F, 0.95F)
                .value(primal, 0.50F, 1)
                .value(stellar, 0.50F, 1.05F)
                .value(divine, 0.50F, 1.1F)
                .value(esoteric, 0.50F, 1.15F)
                .value(cataclysmic, 0.50F, 1.2F));

        this.addAttribute("melee", "glacial", ALObjects.Attributes.COLD_DAMAGE, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, d -> d
                        .weights(TieredWeights.forAllTiers(DEFAULT_WEIGHT, DEFAULT_QUALITY))
                        .exclusiveWith(afx("melee/attribute/infernal")))
                .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT)
                .step(0.25F)
                .value(legendary, 6, 11)
                .value(ancient, 6, 12)
                .value(forgotten, 8, 13)
                .value(primal, 8, 14)
                .value(stellar, 10, 15)
                .value(divine, 10, 16)
                .value(esoteric, 12, 17)
                .value(cataclysmic, 12, 18));

        this.addAttribute("melee", "lengthy", Attributes.ENTITY_INTERACTION_RANGE, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT)
                .step(0.25F)
                .value(worthless, 0.25F, 0.5F)
                .value(legendary, 2, 3.5F)
                .value(ancient, 2, 3.75F)
                .value(forgotten, 2, 4)
                .value(primal, 3, 4.25F)
                .value(stellar, 3, 4.5F)
                .value(divine, 4, 4.75F)
                .value(esoteric, 4, 5)
                .value(cataclysmic, 4, 5.5F));

        this.addAttribute("melee", "forceful", Attributes.ATTACK_KNOCKBACK, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.MELEE_WEAPON)
                .step(0.25F)
                .value(worthless, 0.25F, 0.5F)
                .value(legendary, 1.5F, 3.5F)
                .value(ancient, 2, 4)
                .value(forgotten, 2, 4.5F)
                .value(primal, 2.5F, 5)
                .value(stellar, 2.5F, 5.5F)
                .value(divine, 3, 6)
                .value(esoteric, 3, 6.5F)
                .value(cataclysmic, 3.5F, 7));

        this.addAttribute("melee", "berserking", ALObjects.Attributes.OVERHEAL, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, d -> d
                        .weights(TieredWeights.forAllTiers(DEFAULT_WEIGHT, DEFAULT_QUALITY))
                        .exclusiveWith(afx("melee/attribute/vampiric")))
                .categories(Apoth.LootCategories.MELEE_WEAPON)
                .value(worthless, 0.01F, 0.05F)
                .value(legendary, 0.15F, 0.25F)
                .value(ancient, 0.20F, 0.30F)
                .value(forgotten, 0.20F, 0.30F)
                .value(primal, 0.25F, 0.35F)
                .value(stellar, 0.25F, 0.35F)
                .value(divine, 0.30F, 0.40F)
                .value(esoteric, 0.30F, 0.40F)
                .value(cataclysmic, 0.35F, 0.45F));

        this.addAttribute("melee", "giant_slaying", ALObjects.Attributes.CURRENT_HP_DAMAGE, AttributeModifier.Operation.ADD_VALUE, b -> b
                .definition(AffixType.STAT, d -> d
                        .weights(TieredWeights.onlyFor(WorldTier.PINNACLE, 15, 0.75F)))
                .categories(Apoth.LootCategories.MELEE_WEAPON)
                .value(legendary, 0.10F, 0.30F)
                .value(ancient, 0.15F, 0.35F)
                .value(forgotten, 0.15F, 0.40F)
                .value(primal, 0.15F, 0.45F)
                .value(stellar, 0.20F, 0.50F)
                .value(divine, 0.20F, 0.55F)
                .value(esoteric, 0.20F, 0.60F)
                .value(cataclysmic, 0.25F, 0.65F));

        // Damage Reduction Affixes
        this.addDamageReduction("armor", "blockading", DamageReductionAffix.DamageType.PHYSICAL, b -> b
                .definition(AffixType.ABILITY, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.CHESTPLATE, Apoth.LootCategories.LEGGINGS)
                .value(worthless, 0.01F, 0.02F)
                .value(legendary, 0.10F, 0.15F)
                .value(ancient, 0.10F, 0.20F)
                .value(forgotten, 0.10F, 0.20F)
                .value(primal, 0.15F, 0.25F)
                .value(stellar, 0.15F, 0.25F)
                .value(divine, 0.15F, 0.30F)
                .value(esoteric, 0.20F, 0.30F)
                .value(cataclysmic, 0.20F, 0.30F));

        this.addDamageReduction("armor", "runed", DamageReductionAffix.DamageType.MAGIC, b -> b
                .definition(AffixType.ABILITY, d -> d
                        .weights(TieredWeights.forAllTiers(DEFAULT_WEIGHT, DEFAULT_QUALITY))
                        .exclusiveWith(afx("armor/dmg_reduction/blockading")))
                .categories(Apoth.LootCategories.HELMET, Apoth.LootCategories.CHESTPLATE, Apoth.LootCategories.LEGGINGS, Apoth.LootCategories.BOOTS)
                .value(worthless, 0.01F, 0.02F)
                .value(legendary, 0.10F, 0.15F)
                .value(ancient, 0.10F, 0.20F)
                .value(forgotten, 0.10F, 0.20F)
                .value(primal, 0.15F, 0.25F)
                .value(stellar, 0.15F, 0.25F)
                .value(divine, 0.15F, 0.30F)
                .value(esoteric, 0.20F, 0.30F)
                .value(cataclysmic, 0.20F, 0.30F));

        // Armor Basic Effects
        this.addDamageReduction("armor", "blast_forged", DamageReductionAffix.DamageType.EXPLOSION, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.CHESTPLATE, Apoth.LootCategories.LEGGINGS)
                .value(worthless, 0.01F, 0.05F)
                .value(legendary, 0.20F, 0.40F)
                .value(ancient, 0.20F, 0.45F)
                .value(forgotten, 0.20F, 0.50F)
                .value(primal, 0.25F, 0.55F)
                .value(stellar, 0.25F, 0.60F)
                .value(divine, 0.25F, 0.65F)
                .value(esoteric, 0.30F, 0.70F)
                .value(cataclysmic, 0.30F, 0.75F));

        this.addDamageReduction("armor", "feathery", DamageReductionAffix.DamageType.FALL, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BOOTS)
                .value(worthless, 0.01F, 0.05F)
                .value(legendary, 0.20F, 0.40F)
                .value(ancient, 0.20F, 0.45F)
                .value(forgotten, 0.20F, 0.50F)
                .value(primal, 0.25F, 0.55F)
                .value(stellar, 0.25F, 0.60F)
                .value(divine, 0.25F, 0.65F)
                .value(esoteric, 0.30F, 0.70F)
                .value(cataclysmic, 0.30F, 0.75F));

        this.addDamageReduction("armor", "deflective", DamageReductionAffix.DamageType.PROJECTILE, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.HELMET, Apoth.LootCategories.CHESTPLATE)
                .value(worthless, 0.01F, 0.05F)
                .value(legendary, 0.20F, 0.40F)
                .value(ancient, 0.20F, 0.45F)
                .value(forgotten, 0.20F, 0.50F)
                .value(primal, 0.25F, 0.55F)
                .value(stellar, 0.25F, 0.60F)
                .value(divine, 0.25F, 0.65F)
                .value(esoteric, 0.30F, 0.70F)
                .value(cataclysmic, 0.30F, 0.75F));

        this.addDamageReduction("armor", "grounded", DamageReductionAffix.DamageType.LIGHTNING, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.HELMET, Apoth.LootCategories.BOOTS)
                .value(worthless, 0.01F, 0.05F)
                .value(legendary, 0.20F, 0.45F)
                .value(ancient, 0.20F, 0.50F)
                .value(forgotten, 0.20F, 0.55F)
                .value(primal, 0.25F, 0.60F)
                .value(stellar, 0.25F, 0.65F)
                .value(divine, 0.25F, 0.70F)
                .value(esoteric, 0.30F, 0.75F)
                .value(cataclysmic, 0.30F, 0.80F));

        this.addMobEffect("armor", "revitalizing", MobEffects.HEAL, MobEffectAffix.Target.HURT_SELF, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.CHESTPLATE, Apoth.LootCategories.LEGGINGS)
                .value(legendary, StepFunction.constant(1), StepFunction.fromBounds(1, 2, 0.25F), 240)
                .value(ancient, StepFunction.constant(1), StepFunction.fromBounds(1, 2, 0.25F), 200)
                .value(forgotten, StepFunction.constant(1), StepFunction.fromBounds(2, 3, 0.25F), 200)
                .value(primal, StepFunction.constant(1), StepFunction.fromBounds(2, 3, 0.25F), 200)
                .value(stellar, StepFunction.constant(1), StepFunction.fromBounds(3, 4, 0.25F), 140)
                .value(divine, StepFunction.constant(1), StepFunction.fromBounds(3, 4, 0.25F), 140)
                .value(esoteric, StepFunction.constant(1), StepFunction.fromBounds(4, 5, 0.25F), 140)
                .value(cataclysmic, StepFunction.constant(1), StepFunction.fromBounds(4, 5, 0.25F), 100));

        this.addMobEffect("armor", "nimble", MobEffects.MOVEMENT_SPEED, MobEffectAffix.Target.HURT_SELF, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.LEGGINGS, Apoth.LootCategories.BOOTS)
                .value(legendary, 300, 500, StepFunction.fromBounds(1, 3, 0.5F), 540)
                .value(ancient, 300, 500, StepFunction.fromBounds(1, 3, 0.5F), 500)
                .value(forgotten, 300, 500, StepFunction.fromBounds(2, 4, 0.5F), 440)
                .value(primal, 400, 600, StepFunction.fromBounds(2, 4, 0.5F), 400)
                .value(stellar, 400, 600, StepFunction.fromBounds(3, 5, 0.5F), 340)
                .value(divine, 400, 600, StepFunction.fromBounds(3, 5, 0.5F), 300)
                .value(esoteric, 500, 700, StepFunction.fromBounds(4, 6, 0.5F), 240)
                .value(cataclysmic, 500, 700, StepFunction.fromBounds(4, 6, 0.5F), 200));


        this.addMobEffect("armor", "bursting", ALObjects.MobEffects.VITALITY, MobEffectAffix.Target.HURT_SELF, b -> b
                .definition(AffixType.BASIC_EFFECT, d -> d
                        .weights(TieredWeights.forAllTiers(DEFAULT_WEIGHT, DEFAULT_QUALITY))
                        .exclusiveWith(afx("armor/mob_effect/revitalizing")))
                .categories(Apoth.LootCategories.CHESTPLATE, Apoth.LootCategories.LEGGINGS)
                .value(legendary, StepFunction.constant(200), StepFunction.fromBounds(1, 2, 0.25F), 250)
                .value(ancient, StepFunction.constant(250), StepFunction.fromBounds(1, 2, 0.25F), 250)
                .value(forgotten, StepFunction.constant(250), StepFunction.fromBounds(2, 3, 0.25F), 200)
                .value(primal, StepFunction.constant(300), StepFunction.fromBounds(2, 3, 0.25F), 200)
                .value(stellar, StepFunction.constant(300), StepFunction.fromBounds(3, 4, 0.25F), 150)
                .value(divine, StepFunction.constant(350), StepFunction.fromBounds(3, 4, 0.25F), 150)
                .value(esoteric, StepFunction.constant(350), StepFunction.fromBounds(4, 5, 0.25F), 100)
                .value(cataclysmic, StepFunction.constant(400), StepFunction.fromBounds(4, 5, 0.25F), 100));

        this.addMobEffect("armor", "bolstering", MobEffects.DAMAGE_RESISTANCE, MobEffectAffix.Target.HURT_SELF, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.CHESTPLATE, Apoth.LootCategories.LEGGINGS)
                .value(legendary, 100, 180, StepFunction.fromBounds(0, 1, 0.5F), 240)
                .value(ancient, 100, 180, StepFunction.fromBounds(0, 1, 0.5F), 240)
                .value(forgotten, 100, 200, StepFunction.fromBounds(1, 2, 0.5F), 240)
                .value(primal, 120, 200, StepFunction.fromBounds(1, 2, 0.5F), 240)
                .value(stellar, 120, 220, StepFunction.fromBounds(1, 2, 0.5F), 220)
                .value(divine, 120, 220, StepFunction.fromBounds(1, 2, 0.5F), 220)
                .value(esoteric, 140, 240, StepFunction.fromBounds(2, 3, 0.5F), 220)
                .value(cataclysmic, 140, 240, StepFunction.fromBounds(2, 3, 0.5F), 200));

        this.addMobEffect("armor", "blinding", MobEffects.BLINDNESS, MobEffectAffix.Target.HURT_ATTACKER, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.HELMET)
                .value(legendary, 60, 100, 0, 200)
                .value(ancient, 60, 100, 0, 200)
                .value(forgotten, 80, 120, 0, 200)
                .value(primal, 80, 120, 0, 200)
                .value(stellar, 80, 120, 1, 200)
                .value(divine, 100, 140, 1, 200)
                .value(esoteric, 100, 140, 1, 200)
                .value(cataclysmic, 100, 140, 1, 200));

        // Breaker Basic Effects
        this.addMobEffect("breaker", "swift", MobEffects.DIG_SPEED, MobEffectAffix.Target.BREAK_SELF, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BREAKER)
                .value(legendary, 240, 400, StepFunction.fromBounds(0, 2, 0.25F), 500)
                .value(ancient, 260, 460, StepFunction.fromBounds(0, 2, 0.25F), 500)
                .value(forgotten, 260, 500, StepFunction.fromBounds(1, 3, 0.25F), 500)
                .value(primal, 280, 500, StepFunction.fromBounds(1, 3, 0.25F), 500)
                .value(stellar, 280, 560, StepFunction.fromBounds(1, 3, 0.25F), 500)
                .value(divine, 300, 600, StepFunction.fromBounds(2, 4, 0.25F), 400)
                .value(esoteric, 300, 600, StepFunction.fromBounds(2, 4, 0.25F), 400)
                .value(cataclysmic, 320, 660, StepFunction.fromBounds(2, 4, 0.25F), 400));

        this.addMobEffect("breaker", "spelunkers", MobEffects.MOVEMENT_SPEED, MobEffectAffix.Target.BREAK_SELF, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BREAKER)
                .value(legendary, 340, 500, StepFunction.fromBounds(0, 2, 0.25F), 500)
                .value(ancient, 360, 560, StepFunction.fromBounds(0, 2, 0.25F), 500)
                .value(forgotten, 360, 600, StepFunction.fromBounds(1, 3, 0.25F), 500)
                .value(primal, 380, 600, StepFunction.fromBounds(1, 3, 0.25F), 500)
                .value(stellar, 380, 660, StepFunction.fromBounds(1, 3, 0.25F), 500)
                .value(divine, 400, 700, StepFunction.fromBounds(2, 4, 0.25F), 400)
                .value(esoteric, 400, 700, StepFunction.fromBounds(2, 4, 0.25F), 400)
                .value(cataclysmic, 420, 760, StepFunction.fromBounds(2, 4, 0.25F), 400));

        Holder<Enchantment> fortune = enchants.getOrThrow(Enchantments.FORTUNE);
        this.addEnchantment("breaker", "prosperous", fortune, EnchantmentAffix.Mode.EXISTING, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BREAKER)
                .step(0.5F)
                .value(legendary, 2, 4)
                .value(ancient, 2, 4)
                .value(forgotten, 3, 5)
                .value(primal, 3, 5)
                .value(stellar, 3, 5)
                .value(divine, 4, 6)
                .value(esoteric, 4, 6)
                .value(cataclysmic, 6, 8));

        this.add(Nadir.loc("breaker/effect/omnetic"),
                new OmneticAffix.Builder()
                        .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, 5)
                        .value(legendary, "netherite", Items.NETHERITE_AXE, Items.NETHERITE_SHOVEL, Items.NETHERITE_PICKAXE, Items.NETHERITE_SWORD, Items.NETHERITE_HOE)
                        .value(ancient, "netherite", Items.NETHERITE_AXE, Items.NETHERITE_SHOVEL, Items.NETHERITE_PICKAXE, Items.NETHERITE_SWORD, Items.NETHERITE_HOE)
                        .value(forgotten, "netherite", Items.NETHERITE_AXE, Items.NETHERITE_SHOVEL, Items.NETHERITE_PICKAXE, Items.NETHERITE_SWORD, Items.NETHERITE_HOE)
                        .value(primal, "netherite", Items.NETHERITE_AXE, Items.NETHERITE_SHOVEL, Items.NETHERITE_PICKAXE, Items.NETHERITE_SWORD, Items.NETHERITE_HOE)
                        .value(stellar, "netherite", Items.NETHERITE_AXE, Items.NETHERITE_SHOVEL, Items.NETHERITE_PICKAXE, Items.NETHERITE_SWORD, Items.NETHERITE_HOE)
                        .value(divine, "netherite", Items.NETHERITE_AXE, Items.NETHERITE_SHOVEL, Items.NETHERITE_PICKAXE, Items.NETHERITE_SWORD, Items.NETHERITE_HOE)
                        .value(esoteric, "netherite", Items.NETHERITE_AXE, Items.NETHERITE_SHOVEL, Items.NETHERITE_PICKAXE, Items.NETHERITE_SWORD, Items.NETHERITE_HOE)
                        .value(cataclysmic, "netherite", Items.NETHERITE_AXE, Items.NETHERITE_SHOVEL, Items.NETHERITE_PICKAXE, Items.NETHERITE_SWORD, Items.NETHERITE_HOE)
                        .build());

        this.add(Nadir.loc("breaker/effect/radial"),
                new RadialAffix.Builder()
                        .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, 5)
                        .categories(Apoth.LootCategories.BREAKER, Apoth.LootCategories.SHEARS)
                        .value(legendary, c -> c
                                .radii(5, 3)
                                .radii(5, 5)
                                .radii(7, 5))
                        .value(ancient, c -> c
                                .radii(5, 3)
                                .radii(5, 5)
                                .radii(7, 5))
                        .value(forgotten, c -> c
                                .radii(5, 5)
                                .radii(7, 5)
                                .radii(7, 7))
                        .value(primal, c -> c
                                .radii(5, 5)
                                .radii(7, 5)
                                .radii(7, 7))
                        .value(stellar, c -> c
                                .radii(7, 5)
                                .radii(7, 7)
                                .radii(9, 7))
                        .value(divine, c -> c
                                .radii(7, 5)
                                .radii(7, 7)
                                .radii(9, 7))
                        .value(esoteric, c -> c
                                .radii(7, 7)
                                .radii(9, 7)
                                .radii(9, 9))
                        .value(cataclysmic, c -> c
                                .radii(7, 7)
                                .radii(9, 7)
                                .radii(9, 9))
                        .build());

        // Ranged Basic Effects
        this.addMobEffect("ranged", "shulkers", MobEffects.LEVITATION, MobEffectAffix.Target.ARROW_TARGET, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BOW)
                .value(legendary, 40, 100, StepFunction.fromBounds(0, 2, 0.25F), 140)
                .value(ancient, 40, 120, StepFunction.fromBounds(0, 2, 0.25F), 140)
                .value(forgotten, 60, 120, StepFunction.fromBounds(1, 3, 0.25F), 120)
                .value(primal, 60, 140, StepFunction.fromBounds(1, 3, 0.25F), 120)
                .value(stellar, 80, 140, StepFunction.fromBounds(1, 3, 0.25F), 120)
                .value(divine, 80, 160, StepFunction.fromBounds(2, 4, 0.25F), 120)
                .value(esoteric, 100, 160, StepFunction.fromBounds(2, 4, 0.25F), 100)
                .value(cataclysmic, 100, 180, StepFunction.fromBounds(2, 4, 0.25F), 100));

        this.addMobEffect("ranged", "acidic", ALObjects.MobEffects.SUNDERING, MobEffectAffix.Target.ARROW_TARGET, b -> b
                .definition(AffixType.BASIC_EFFECT, d -> d
                        .weights(TieredWeights.onlyFor(WorldTier.PINNACLE, 20, 0.75F)))
                .categories(Apoth.LootCategories.BOW)
                .stacking()
                .limit(4)
                .value(legendary, 80, 180, 0, 40)
                .value(ancient, 100, 180, 0, 40)
                .value(forgotten, 100, 200, 0, 40)
                .value(primal, 120, 200, 1, 40)
                .value(stellar, 120, 220, 1, 40)
                .value(divine, 140, 220, 1, 40)
                .value(esoteric, 140, 240, 1, 40)
                .value(cataclysmic, 160, 240, 2, 40));

        this.addMobEffect("ranged", "ensnaring", MobEffects.MOVEMENT_SLOWDOWN, MobEffectAffix.Target.ARROW_TARGET, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BOW, Apoth.LootCategories.TRIDENT)
                .value(legendary, 80, 160, StepFunction.fromBounds(0, 2, 0.25F), 160)
                .value(ancient, 80, 180, StepFunction.fromBounds(0, 2, 0.25F), 160)
                .value(forgotten, 100, 180, StepFunction.fromBounds(1, 3, 0.25F), 160)
                .value(primal, 100, 180, StepFunction.fromBounds(1, 3, 0.25F), 160)
                .value(stellar, 100, 200, StepFunction.fromBounds(1, 3, 0.25F), 140)
                .value(divine, 120, 200, StepFunction.fromBounds(2, 4, 0.25F), 140)
                .value(esoteric, 120, 200, StepFunction.fromBounds(2, 4, 0.25F), 140)
                .value(cataclysmic, 120, 220, StepFunction.fromBounds(2, 4, 0.25F), 120));


        this.addMobEffect("ranged", "fleeting", MobEffects.MOVEMENT_SPEED, MobEffectAffix.Target.ARROW_SELF, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BOW, Apoth.LootCategories.TRIDENT)
                .value(legendary, 100, 300, StepFunction.fromBounds(0, 2, 0.25F), 0)
                .value(ancient, 100, 300, StepFunction.fromBounds(0, 2, 0.25F), 0)
                .value(forgotten, 200, 400, StepFunction.fromBounds(0, 2, 0.25F), 0)
                .value(primal, 200, 400, StepFunction.fromBounds(0, 2, 0.25F), 0)
                .value(stellar, 200, 400, StepFunction.fromBounds(1, 3, 0.25F), 0)
                .value(divine, 300, 500, StepFunction.fromBounds(1, 3, 0.25F), 0)
                .value(esoteric, 300, 500, StepFunction.fromBounds(1, 3, 0.25F), 0)
                .value(cataclysmic, 300, 500, StepFunction.fromBounds(1, 3, 0.25F), 0));

        this.addMobEffect("ranged", "grievous", ALObjects.MobEffects.GRIEVOUS, MobEffectAffix.Target.ARROW_TARGET, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BOW, Apoth.LootCategories.TRIDENT)
                .value(legendary, 200, 300, StepFunction.fromBounds(0, 2, 0.25F), 400)
                .value(ancient, 300, 400, StepFunction.fromBounds(0, 2, 0.25F), 300)
                .value(forgotten, 300, 400, StepFunction.fromBounds(0, 2, 0.25F), 300)
                .value(primal, 300, 400, StepFunction.fromBounds(0, 2, 0.25F), 300)
                .value(stellar, 300, 400, StepFunction.fromBounds(1, 3, 0.25F), 300)
                .value(divine, 400, 500, StepFunction.fromBounds(1, 3, 0.25F), 300)
                .value(esoteric, 400, 500, StepFunction.fromBounds(1, 3, 0.25F), 200)
                .value(cataclysmic, 400, 500, StepFunction.fromBounds(1, 3, 0.25F), 200));

        this.addMobEffect("ranged", "ivy_laced", MobEffects.POISON, MobEffectAffix.Target.ARROW_TARGET, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BOW, Apoth.LootCategories.TRIDENT)
                .stacking()
                .limit(5)
                .value(legendary, 100, 200, StepFunction.fromBounds(0, 2, 0.25F), 40)
                .value(ancient, 100, 260, StepFunction.fromBounds(0, 2, 0.25F), 40)
                .value(forgotten, 100, 260, StepFunction.fromBounds(0, 2, 0.25F), 40)
                .value(primal, 200, 300, StepFunction.fromBounds(0, 2, 0.25F), 20)
                .value(stellar, 200, 300, StepFunction.fromBounds(1, 3, 0.25F), 20)
                .value(divine, 200, 360, StepFunction.fromBounds(1, 3, 0.25F), 20)
                .value(esoteric, 200, 360, StepFunction.fromBounds(1, 3, 0.25F), 20)
                .value(cataclysmic, 300, 400, StepFunction.fromBounds(1, 3, 0.25F), 20));

        this.addMobEffect("ranged", "blighted", MobEffects.WITHER, MobEffectAffix.Target.ARROW_TARGET, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BOW, Apoth.LootCategories.TRIDENT)
                .value(legendary, 160, 200, StepFunction.fromBounds(0, 3, 0.25F), 300)
                .value(ancient, 200, 260, StepFunction.fromBounds(0, 3, 0.25F), 300)
                .value(forgotten, 200, 260, StepFunction.fromBounds(0, 4, 0.25F), 300)
                .value(primal, 200, 260, StepFunction.fromBounds(0, 4, 0.25F), 200)
                .value(stellar, 260, 300, StepFunction.fromBounds(0, 4, 0.25F), 200)
                .value(divine, 260, 300, StepFunction.fromBounds(1, 5, 0.25F), 200)
                .value(esoteric, 260, 300, StepFunction.fromBounds(1, 5, 0.25F), 200)
                .value(cataclysmic, 300, 360, StepFunction.fromBounds(1, 5, 0.25F), 100));

        this.addMobEffect("ranged", "deathbound", MobEffects.WITHER, MobEffectAffix.Target.ARROW_TARGET, b -> b
                .definition(AffixType.BASIC_EFFECT, d -> d
                        .weights(TieredWeights.onlyFor(WorldTier.PINNACLE, 20, 0.75F))
                        .exclusiveWith(afx("ranged/mob_effect/blighted")))
                .categories(Apoth.LootCategories.BOW, Apoth.LootCategories.TRIDENT)
                .stacking()
                .limit(4)
                .value(legendary, 100, 200, 1, 40)
                .value(ancient, 100, 200, 1, 40)
                .value(forgotten, 100, 200, 1, 40)
                .value(primal, 160, 260, 1, 40)
                .value(stellar, 160, 260, 2, 40)
                .value(divine, 160, 260, 2, 40)
                .value(esoteric, 160, 260, 2, 40)
                .value(cataclysmic, 200, 300, 2, 20));

        // Melee Basic Effects
        this.addMobEffect("melee", "bloodletting", ALObjects.MobEffects.BLEEDING, MobEffectAffix.Target.ATTACK_TARGET, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT)
                .stacking()
                .limit(3)
                .value(legendary, 100, 120, StepFunction.fromBounds(0, 1, 0.125F), 80)
                .value(ancient, 100, 140, StepFunction.fromBounds(0, 1, 0.125F), 80)
                .value(forgotten, 100, 140, StepFunction.fromBounds(0, 1, 0.125F), 80)
                .value(primal, 100, 140, StepFunction.fromBounds(0, 2, 0.125F), 80)
                .value(stellar, 100, 140, StepFunction.fromBounds(0, 2, 0.125F), 80)
                .value(divine, 120, 160, StepFunction.fromBounds(0, 2, 0.125F), 60)
                .value(esoteric, 120, 160, StepFunction.fromBounds(0, 2, 0.125F), 60)
                .value(cataclysmic, 120, 160, StepFunction.fromBounds(0, 3, 0.125F), 60));

        this.addMobEffect("melee", "caustic", ALObjects.MobEffects.SUNDERING, MobEffectAffix.Target.ATTACK_TARGET, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT)
                .stacking()
                .limit(3)
                .value(legendary, 200, 400, StepFunction.fromBounds(0, 1, 0.25F), 60)
                .value(ancient, 200, 400, StepFunction.fromBounds(0, 1, 0.25F), 60)
                .value(forgotten, 300, 500, StepFunction.fromBounds(0, 1, 0.25F), 60)
                .value(primal, 300, 500, StepFunction.fromBounds(1, 2, 0.25F), 60)
                .value(stellar, 300, 500, StepFunction.fromBounds(1, 2, 0.25F), 30)
                .value(divine, 400, 600, StepFunction.fromBounds(1, 2, 0.25F), 30)
                .value(esoteric, 400, 600, StepFunction.fromBounds(1, 2, 0.25F), 30)
                .value(cataclysmic, 400, 600, StepFunction.fromBounds(2, 3, 0.25F), 30));

        this.addMobEffect("melee", "sophisticated", ALObjects.MobEffects.KNOWLEDGE, MobEffectAffix.Target.ATTACK_SELF, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT)
                .stacking()
                .limit(3)
                .value(legendary, 400, 1000, StepFunction.fromBounds(0, 2, 0.25F), 400)
                .value(ancient, 400, 1000, StepFunction.fromBounds(0, 2, 0.25F), 400)
                .value(forgotten, 600, 1200, StepFunction.fromBounds(0, 2, 0.25F), 300)
                .value(primal, 600, 1200, StepFunction.fromBounds(1, 3, 0.25F), 300)
                .value(stellar, 600, 1200, StepFunction.fromBounds(1, 3, 0.25F), 300)
                .value(divine, 600, 1200, StepFunction.fromBounds(1, 3, 0.25F), 300)
                .value(esoteric, 800, 1400, StepFunction.fromBounds(1, 3, 0.25F), 200)
                .value(cataclysmic, 800, 1400, StepFunction.fromBounds(2, 4, 0.25F), 200));

        this.addMobEffect("melee", "omniscient", ALObjects.MobEffects.KNOWLEDGE, MobEffectAffix.Target.ATTACK_SELF, b -> b
                .definition(AffixType.BASIC_EFFECT, d -> d
                        .weights(TieredWeights.onlyFor(WorldTier.PINNACLE, 20, 0.75F))
                        .exclusiveWith(afx("melee/mob_effect/sophisticated")))
                .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT)
                .stacking()
                .limit(8)
                .value(legendary, 100, 160, StepFunction.fromBounds(0, 1, 0.125F), 80)
                .value(ancient, 160, 200, StepFunction.fromBounds(0, 1, 0.125F), 80)
                .value(forgotten, 160, 200, StepFunction.fromBounds(0, 1, 0.125F), 80)
                .value(primal, 200, 260, StepFunction.fromBounds(1, 2, 0.125F), 80)
                .value(stellar, 200, 260, StepFunction.fromBounds(1, 2, 0.125F), 80)
                .value(divine, 260, 300, StepFunction.fromBounds(1, 2, 0.125F), 80)
                .value(esoteric, 260, 300, StepFunction.fromBounds(1, 2, 0.125F), 80)
                .value(cataclysmic, 300, 360, StepFunction.fromBounds(1, 2, 0.125F), 40));

        this.addMobEffect("melee", "weakening", MobEffects.WEAKNESS, MobEffectAffix.Target.ATTACK_TARGET, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT)
                .value(legendary, 80, 220, StepFunction.fromBounds(0, 2, 0.125F), 300)
                .value(ancient, 100, 240, StepFunction.fromBounds(0, 2, 0.125F), 300)
                .value(forgotten, 100, 260, StepFunction.fromBounds(0, 2, 0.125F), 200)
                .value(primal, 100, 280, StepFunction.fromBounds(0, 2, 0.125F), 200)
                .value(stellar, 100, 300, StepFunction.fromBounds(1, 3, 0.125F), 200)
                .value(divine, 100, 320, StepFunction.fromBounds(1, 3, 0.125F), 200)
                .value(esoteric, 120, 340, StepFunction.fromBounds(1, 3, 0.125F), 200)
                .value(cataclysmic, 120, 360, StepFunction.fromBounds(1, 3, 0.125F), 100));

        this.addMobEffect("melee", "elusive", MobEffects.MOVEMENT_SPEED, MobEffectAffix.Target.ATTACK_SELF, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT)
                .stacking()
                .limit(3)
                .value(legendary, 200, 800, StepFunction.fromBounds(0, 2, 0.25F), 300)
                .value(ancient, 400, 1000, StepFunction.fromBounds(0, 2, 0.25F), 200)
                .value(forgotten, 400, 1000, StepFunction.fromBounds(1, 3, 0.25F), 200)
                .value(primal, 600, 1200, StepFunction.fromBounds(1, 3, 0.25F), 200)
                .value(stellar, 600, 1200, StepFunction.fromBounds(1, 3, 0.25F), 200)
                .value(divine, 800, 1400, StepFunction.fromBounds(1, 3, 0.25F), 200)
                .value(esoteric, 800, 1400, StepFunction.fromBounds(1, 3, 0.25F), 100)
                .value(cataclysmic, 1000, 1600, StepFunction.fromBounds(2, 4, 0.25F), 100));

        // Shield basic effects
        this.addMobEffect("shield", "devilish", ALObjects.MobEffects.BLEEDING, MobEffectAffix.Target.BLOCK_ATTACKER, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.SHIELD)
                .stacking()
                .limit(4)
                .value(legendary, 100, 140, StepFunction.fromBounds(0, 1, 0.25F), 40)
                .value(ancient, 120, 160, StepFunction.fromBounds(0, 1, 0.25F), 40)
                .value(forgotten, 120, 160, StepFunction.fromBounds(1, 2, 0.25F), 40)
                .value(primal, 120, 160, StepFunction.fromBounds(1, 2, 0.25F), 40)
                .value(stellar, 140, 180, StepFunction.fromBounds(1, 2, 0.25F), 40)
                .value(divine, 140, 180, StepFunction.fromBounds(1, 2, 0.25F), 40)
                .value(esoteric, 140, 180, StepFunction.fromBounds(2, 3, 0.25F), 40)
                .value(cataclysmic, 160, 200, StepFunction.fromBounds(2, 3, 0.25F), 20));

        this.addMobEffect("shield", "venomous", MobEffects.POISON, MobEffectAffix.Target.BLOCK_ATTACKER, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.SHIELD)
                .stacking()
                .limit(4)
                .value(legendary, 160, 240, StepFunction.fromBounds(0, 1, 0.5F), 120)
                .value(ancient, 180, 260, StepFunction.fromBounds(0, 1, 0.5F), 120)
                .value(forgotten, 180, 260, StepFunction.fromBounds(1, 2, 0.5F), 100)
                .value(primal, 200, 280, StepFunction.fromBounds(1, 2, 0.5F), 100)
                .value(stellar, 200, 280, StepFunction.fromBounds(1, 2, 0.5F), 100)
                .value(divine, 220, 300, StepFunction.fromBounds(2, 3, 0.5F), 80)
                .value(esoteric, 220, 300, StepFunction.fromBounds(2, 3, 0.5F), 80)
                .value(cataclysmic, 240, 320, StepFunction.fromBounds(2, 3, 0.5F), 80));

        this.addMobEffect("shield", "withering", MobEffects.WITHER, MobEffectAffix.Target.BLOCK_ATTACKER, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.SHIELD)
                .value(legendary, 60, 160, StepFunction.fromBounds(0, 2, 0.25F), 0)
                .value(ancient, 80, 180, StepFunction.fromBounds(0, 2, 0.25F), 0)
                .value(forgotten, 80, 180, StepFunction.fromBounds(0, 2, 0.25F), 0)
                .value(primal, 100, 200, StepFunction.fromBounds(1, 3, 0.25F), 0)
                .value(stellar, 100, 200, StepFunction.fromBounds(1, 3, 0.25F), 0)
                .value(divine, 120, 220, StepFunction.fromBounds(1, 3, 0.25F), 0)
                .value(esoteric, 120, 220, StepFunction.fromBounds(1, 3, 0.25F), 0)
                .value(cataclysmic, 140, 240, StepFunction.fromBounds(2, 4, 0.25F), 0));

        this.addMobEffect("shield", "reinforcing", MobEffects.DAMAGE_RESISTANCE, MobEffectAffix.Target.BLOCK_SELF, b -> b
                .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.SHIELD)
                .value(legendary, 160, 240, StepFunction.fromBounds(0, 1, 0.5F), 200)
                .value(ancient, 200, 280, StepFunction.fromBounds(1, 2, 0.5F), 150)
                .value(forgotten, 200, 280, StepFunction.fromBounds(1, 2, 0.5F), 150)
                .value(primal, 240, 320, StepFunction.fromBounds(1, 2, 0.5F), 150)
                .value(stellar, 240, 320, StepFunction.fromBounds(1, 2, 0.5F), 150)
                .value(divine, 240, 320, StepFunction.fromBounds(2, 3, 0.5F), 100)
                .value(esoteric, 280, 460, StepFunction.fromBounds(2, 3, 0.5F), 100)
                .value(cataclysmic, 280, 460, StepFunction.fromBounds(2, 4, 0.5F), 100));

        this.addMobEffect("shield", "galvanizing", MobEffects.DAMAGE_RESISTANCE, MobEffectAffix.Target.BLOCK_SELF, b -> b
                .definition(AffixType.BASIC_EFFECT, d -> d
                        .weights(TieredWeights.onlyFor(WorldTier.PINNACLE, 20, 0.75F))
                        .exclusiveWith(afx("shield/mob_effect/reinforcing")))
                .categories(Apoth.LootCategories.SHIELD)
                .stacking()
                .limit(3)
                .value(legendary, 100, 160, StepFunction.fromBounds(0, 1, 0.125F), 80)
                .value(ancient, 120, 180, StepFunction.fromBounds(0, 1, 0.125F), 80)
                .value(forgotten, 120, 180, StepFunction.fromBounds(1, 2, 0.125F), 80)
                .value(primal, 120, 180, StepFunction.fromBounds(1, 2, 0.125F), 80)
                .value(stellar, 140, 200, StepFunction.fromBounds(1, 2, 0.125F), 80)
                .value(divine, 140, 200, StepFunction.fromBounds(2, 3, 0.125F), 80)
                .value(esoteric, 140, 200, StepFunction.fromBounds(2, 3, 0.125F), 80)
                .value(cataclysmic, 160, 220, StepFunction.fromBounds(2, 3, 0.125F), 40));

        // Breaker Abilities
        this.add(Nadir.loc("breaker/ability/enlightened"),
                AffixBuilder.simple(EnlightenedAffix::new)
                        .definition(AffixType.ABILITY, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                        .step(-1)
                        .value(legendary, 5, 0)
                        .value(ancient, 5, 0)
                        .value(forgotten, 4, 0)
                        .value(primal, 4, 0)
                        .value(stellar, 4, 0)
                        .value(divine, 3, 0)
                        .value(esoteric, 3, 0)
                        .value(cataclysmic, 3, 0)
                        .build());

        //radial is already too big, just change so that it can be obtained when mythic is still useful
        this.add(Apotheosis.loc("breaker/ability/supermassive"),
                new RadialAffix.Builder()
                        .definition(AffixType.ABILITY, c -> c
                                .weights(TieredWeights.forAllTiers(20, 0.75F))
                                .exclusiveWith(afx("breaker/effect/radial")))
                        .categories(Apoth.LootCategories.BREAKER)
                        .value(apothRarity("mythic"), c -> c
                                .radii(7, 7))
                        .build());

        /* theoretically unnecessary, base apoth already adds these
        this.add(Nadir.loc("breaker/ability/stoneforming"), new StoneformingAffix(
                AffixDefinition.builder(AffixType.ABILITY)
                        .weights(TieredWeights.forAllTiers(DEFAULT_WEIGHT, DEFAULT_QUALITY))
                        .exclusiveWith(afx("breaker/ability/sandforming"))
                        .build(),
                Set.of(Apoth.LootCategories.BREAKER),
                blockSet(Apoth.Tags.STONEFORMING_CANDIDATES)));

        this.add(Nadir.loc("breaker/ability/sandforming"), new StoneformingAffix(
                AffixDefinition.builder(AffixType.ABILITY)
                        .weights(TieredWeights.forAllTiers(DEFAULT_WEIGHT, DEFAULT_QUALITY))
                        .exclusiveWith(afx("breaker/ability/stoneforming"))
                        .build(),
                Set.of(Apoth.LootCategories.BREAKER),
                blockSet(Apoth.Tags.SANDFORMING_CANDIDATES)));
        */

        // Ranged Abilities
        this.add(Nadir.loc("ranged/magical"), new MagicalArrowAffix(
                AffixDefinition.builder(AffixType.ABILITY)
                        .weights(TieredWeights.forAllTiers(DEFAULT_WEIGHT, DEFAULT_QUALITY))
                        .build(),
                linkedSet(legendary, ancient, forgotten, primal, stellar, divine, esoteric, cataclysmic)));

        this.add(Nadir.loc("ranged/spectral"),
                AffixBuilder.simple(SpectralShotAffix::new)
                        .definition(AffixType.ABILITY, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                        .value(legendary, 0.3F, 0.8F)
                        .value(ancient, 0.4F, 0.8F)
                        .value(forgotten, 0.4F, 0.9F)
                        .value(primal, 0.5F, 0.9F)
                        .value(stellar, 0.5F, 1)
                        .value(divine, 0.6F, 1)
                        .value(esoteric, 0.6F, 1.1F)
                        .value(cataclysmic, 0.7F, 1.1F)
                        .build());

        Holder<Enchantment> looting = enchants.getOrThrow(Enchantments.LOOTING);
        this.addEnchantment("ranged", "prosperous", looting, EnchantmentAffix.Mode.SINGLE, b -> b
                .definition(AffixType.ABILITY, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .categories(Apoth.LootCategories.BOW)
                .step(0.25F)
                .value(legendary, 10, 12)
                .value(ancient, 12, 14)
                .value(forgotten, 14, 16)
                .value(primal, 16, 18)
                .value(stellar, 18, 20)
                .value(divine, 20, 22)
                .value(esoteric, 22, 24)
                .value(cataclysmic, 24, 26));

        // Melee Abilities
        this.add(Nadir.loc("melee/festive"),
                FestiveAffix.builder()
                        .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT)
                        .definition(AffixType.BASIC_EFFECT, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                        .value(legendary, StepFunction.fromBounds(0.04F, 0.07F, 0.005F), 20)
                        .value(ancient, StepFunction.fromBounds(0.05F, 0.08F, 0.005F), 20)
                        .value(forgotten, StepFunction.fromBounds(0.06F, 0.09F, 0.005F), 20)
                        .value(primal, StepFunction.fromBounds(0.07F, 0.1F, 0.005F), 20)
                        .value(stellar, StepFunction.fromBounds(0.08F, 0.11F, 0.005F), 20)
                        .value(divine, StepFunction.fromBounds(0.09F, 0.12F, 0.005F), 20)
                        .value(esoteric, StepFunction.fromBounds(0.1F, 0.13F, 0.005F), 20)
                        .value(cataclysmic, StepFunction.fromBounds(0.1F, 0.14F, 0.005F), 20)
                        .build());

        this.add(Nadir.loc("melee/thunderstruck"),
                AffixBuilder.categorized(ThunderstruckAffix::new)
                        .definition(AffixType.ABILITY, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                        .categories(Apoth.LootCategories.MELEE_WEAPON, Apoth.LootCategories.TRIDENT)
                        .step(1)
                        .value(legendary, 4, 8)
                        .value(ancient, 5, 10)
                        .value(forgotten, 5, 10)
                        .value(primal, 6, 12)
                        .value(stellar, 6, 12)
                        .value(divine, 7, 14)
                        .value(esoteric, 7, 14)
                        .value(cataclysmic, 8, 16)
                        .build());

        this.add(Nadir.loc("melee/cleaving"),
                new CleavingAffix.Builder()
                        .definition(AffixType.ABILITY, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                        .value(legendary, 0.5F, 0.7F, 4, 7)
                        .value(ancient, 0.6F, 0.8F, 5, 8)
                        .value(forgotten, 0.7F, 0.9F, 6, 9)
                        .value(primal, 0.8F, 1, 7, 10)
                        .value(stellar, 0.9F, 1.1F, 8, 11)
                        .value(divine, 1, 1.2F, 9, 12)
                        .value(esoteric, 1.1F, 1.3F, 10, 13)
                        .value(cataclysmic, 1.2F, 1.4F, 11, 14)
                        .build());

        this.add(Nadir.loc("melee/executing"),
                AffixBuilder.simple(ExecutingAffix::new)
                        .definition(AffixType.ABILITY, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                        .value(legendary, 0.15F, 0.25F)
                        .value(ancient, 0.15F, 0.25F)
                        .value(forgotten, 0.20F, 0.30F)
                        .value(primal, 0.20F, 0.30F)
                        .value(stellar, 0.20F, 0.30F)
                        .value(divine, 0.25F, 0.35F)
                        .value(esoteric, 0.25F, 0.35F)
                        .value(cataclysmic, 0.25F, 0.35F)
                        .build());

        // Shield Abilities
        this.add(Nadir.loc("shield/retreating"), new RetreatingAffix(
                AffixDefinition.builder(AffixType.ABILITY).weights(TieredWeights.forAllTiers(DEFAULT_WEIGHT, DEFAULT_QUALITY)).build(),
                linkedSet(legendary, ancient, forgotten, primal, stellar, divine, esoteric, cataclysmic)));

        this.add(Nadir.loc("shield/psychic"), AffixBuilder.simple(PsychicAffix::new)
                .definition(AffixType.ABILITY, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .value(legendary, 0.7F, 1.3F)
                .value(ancient, 0.8F, 1.4F)
                .value(forgotten, 0.9F, 1.5F)
                .value(primal, 1, 1.6F)
                .value(stellar, 1.1F, 1.7F)
                .value(divine, 1.2F, 1.8F)
                .value(esoteric, 1.3F, 1.9F)
                .value(cataclysmic, 1.4F, 2)
                .build());

        this.add(Nadir.loc("shield/catalyzing"), AffixBuilder.simple(CatalyzingAffix::new)
                .definition(AffixType.ABILITY, DEFAULT_WEIGHT, DEFAULT_QUALITY)
                .step(20)
                .value(legendary, 400, 700)
                .value(ancient, 500, 800)
                .value(forgotten, 600, 900)
                .value(primal, 700, 1000)
                .value(stellar, 800, 1100)
                .value(divine, 900, 1200)
                .value(esoteric, 1000, 1300)
                .value(cataclysmic, 1100, 1400)
                .build());

        // Shear Effects
        this.add(Nadir.loc("shears/effect/leafforming"), new StoneformingAffix(
                AffixDefinition.builder(AffixType.BASIC_EFFECT)
                        .weights(TieredWeights.forAllTiers(DEFAULT_WEIGHT, DEFAULT_QUALITY))
                        .exclusiveWith(afx("shears/effect/gardening"))
                        .build(),
                Set.of(Apoth.LootCategories.SHEARS),
                blockSet(Apoth.Tags.LEAFFORMING_CANDIDATES)));

        this.add(Nadir.loc("shears/effect/gardening"), new StoneformingAffix(
                AffixDefinition.builder(AffixType.BASIC_EFFECT)
                        .weights(TieredWeights.forAllTiers(DEFAULT_WEIGHT, DEFAULT_QUALITY))
                        .exclusiveWith(afx("shears/effect/leafforming"))
                        .build(),
                Set.of(Apoth.LootCategories.SHEARS),
                blockSet(Apoth.Tags.GARDENING_CANDIDATES)));

        this.futures.add(CompletableFuture.runAsync(RarityRegistry.INSTANCE::validateExistingHolders));
        this.futures.add(CompletableFuture.runAsync(AffixRegistry.INSTANCE::validateExistingHolders));

    }

    private HolderSet<Block> blockSet(TagKey<Block> tag) {
        return BuiltInRegistries.BLOCK.getOrCreateTag(tag);
    }

    private void addEnchantment(String type, String name, Holder<Enchantment> enchantment, EnchantmentAffix.Mode mode, UnaryOperator<EnchantmentAffix.Builder> config) {
        var builder = new EnchantmentAffix.Builder(enchantment, mode);
        config.apply(builder);
        this.add(Nadir.loc(type + "/enchantment/" + name), builder.build());
    }

    private void addMobEffect(String type, String name, Holder<MobEffect> effect, MobEffectAffix.Target target, UnaryOperator<MobEffectAffix.Builder> config) {
        var builder = new MobEffectAffix.Builder(effect, target);
        config.apply(builder);
        this.add(Nadir.loc(type + "/mob_effect/" + name), builder.build());
    }

    private void addDamageReduction(String type, String name, DamageReductionAffix.DamageType dType, UnaryOperator<DamageReductionAffix.Builder> config) {
        var builder = new DamageReductionAffix.Builder(dType);
        config.apply(builder);
        this.add(Nadir.loc(type + "/dmg_reduction/" + name), builder.build());
    }

    private void addAttribute(String type, String name, Holder<Attribute> attribute, AttributeModifier.Operation op, UnaryOperator<AttributeAffix.Builder> config) {
        var builder = new AttributeAffix.Builder(attribute, op);
        config.apply(builder);
        this.add(Nadir.loc(type + "/attribute/" + name), builder.build());
        //this.addConditionally(Nadir.loc(type + "/attribute/" + name), builder.build(), new rarityRework());
    }

    private void addMultiAttribute(String type, String name, UnaryOperator<MultiAttrAffix.Builder> config) {
        var builder = config.apply(MultiAttrAffix.builder());
        this.add(Nadir.loc(type + "/multi_attribute/" + name), builder.build());
    }

    private static LootRarity rarity(String path) {
        return RarityRegistry.INSTANCE.getValue(Nadir.loc(path));
    }

    private static LootRarity apothRarity(String path) {
        return RarityRegistry.INSTANCE.getValue(Apotheosis.loc(path));
    }

    private static DynamicHolder<Affix> afx(String path) {
        return AffixRegistry.INSTANCE.holder(Nadir.loc(path));
    }

    private static Set<LootRarity> linkedSet(LootRarity... rarities) {
        return ApothMiscUtil.linkedSet(rarities);
    }
}
