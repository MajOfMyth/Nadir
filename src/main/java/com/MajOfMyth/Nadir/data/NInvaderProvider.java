package com.MajOfMyth.Nadir.data;

import com.MajOfMyth.Nadir.Nadir;
import com.google.common.base.Preconditions;
import dev.shadowsoffire.apotheosis.Apoth;
import dev.shadowsoffire.apotheosis.data.InvaderProvider;
import dev.shadowsoffire.apotheosis.loot.LootRarity;
import dev.shadowsoffire.apotheosis.loot.RarityRegistry;
import dev.shadowsoffire.apotheosis.mobs.types.Invader;
import dev.shadowsoffire.apotheosis.mobs.util.BasicBossData;
import dev.shadowsoffire.apotheosis.tiers.Constraints;
import dev.shadowsoffire.apotheosis.tiers.TieredWeights;
import dev.shadowsoffire.apotheosis.tiers.WorldTier;
import dev.shadowsoffire.apothic_attributes.api.ALObjects;
import dev.shadowsoffire.placebo.util.StepFunction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import net.minecraft.core.HolderLookup.Provider;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

public class NInvaderProvider extends InvaderProvider {


    public static final int DEFAULT_WEIGHT = 25;
    public static final int DEFAULT_QUALITY = 1;

    public NInvaderProvider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries);
    }

    @Override
    public String getName() {
        return "Nadir Apothic Invaders";
    }

    @Override
    public void generate() {
        HolderLookup.Provider registries = this.lookupProvider.join();
        HolderLookup.RegistryLookup<Biome> biomes = registries.lookup(Registries.BIOME).get();

        LootRarity rare = rarity("rare");
        LootRarity epic = rarity("epic");
        LootRarity mythic = rarity("mythic");
        LootRarity legendary = nadirRarity("legendary");
        LootRarity ancient = nadirRarity("ancient");
        LootRarity forgotten = nadirRarity("forgotten");
        LootRarity primal = nadirRarity("primal");
        LootRarity stellar = nadirRarity("stellar");
        LootRarity divine = nadirRarity("divine");
        LootRarity esoteric = nadirRarity("esoteric");
        LootRarity cataclysmic = nadirRarity("cataclysmic");

        // Overworld

        addNadirBoss("overworld/zombie", b -> nadirMeleeStats(b)
                .entity(EntityType.ZOMBIE)
                .size(0.75, 2.45)
                .basicData(c -> meleeGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.FRONTIER, DEFAULT_WEIGHT, DEFAULT_QUALITY))
                        .constraints(Constraints.forDimension(Level.OVERWORLD))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS)));

        addNadirBoss("overworld/husk", b -> nadirMeleeStats(b)
                .entity(EntityType.HUSK)
                .size(0.75, 2.45)
                .basicData(c -> meleeGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.FRONTIER, 50, DEFAULT_QUALITY))
                        .constraints(Constraints.forDimension(Level.OVERWORLD))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS)));

        addNadirBoss("overworld/husk_in_dry", b -> nadirMeleeStats(b)
                .entity(EntityType.HUSK)
                .size(0.75, 2.45)
                .basicData(c -> meleeGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.FRONTIER, 120, DEFAULT_QUALITY))
                        .constraints(Constraints.forBiomes(biomes, Tags.Biomes.IS_DRY_OVERWORLD))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS)));

        addNadirBoss("overworld/enderman", b -> nadirMeleeStats(b)
                .entity(EntityType.ENDERMAN)
                .size(0.75, 3.7)
                .basicData(c -> meleeGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.ASCENT, 50, 1.5F))
                        .constraints(Constraints.forDimension(Level.OVERWORLD))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS, Apoth.LootTables.BONUS_RARE_BOSS_DROPS)));

        addNadirBoss("overworld/vindicator", b -> nadirMeleeStats(b)
                .entity(EntityType.VINDICATOR)
                .size(0.75, 2.45)
                .basicData(c -> meleeGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.ASCENT, DEFAULT_WEIGHT, 1.5F))
                        .constraints(Constraints.forDimension(Level.OVERWORLD))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS, Apoth.LootTables.BONUS_RARE_BOSS_DROPS)));

        addNadirBoss("overworld/wolf", b -> b
                .entity(EntityType.WOLF)
                .size(1.5, 2.5)
                .basicData(c -> meleeGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.FRONTIER, DEFAULT_WEIGHT, 1.5F))
                        .constraints(x -> x
                                .dimensions(Level.OVERWORLD)
                                .biomes(biomes, Tags.Biomes.IS_SNOWY))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS, Apoth.LootTables.BONUS_RARE_BOSS_DROPS)
                        .nbt(t -> t.putInt("AngerTime", 99999999)))
                .stats(legendary, c -> c
                        .enchantChance(0.9F)
                        .enchLevels(35, 25)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 120, 170)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.35F, 0.65F)
                        .modifier(Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1, 1.40F)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(15F))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(25F))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, StepFunction.constant(1.25F)))
                .stats(ancient, c -> c
                        .enchantChance(1)
                        .enchLevels(45, 35)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 150, 200)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.35F, 0.65F)
                        .modifier(Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1.3F, 1.70F)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(18F))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(30F))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, StepFunction.constant(1.25F)))
                .stats(forgotten, c -> c
                        .enchantChance(1)
                        .enchLevels(45, 35)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 180, 230)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.35F, 0.65F)
                        .modifier(Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1.6F, 2)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(21F))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(35F))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, StepFunction.constant(1.25F)))
                .stats(primal, c -> c
                        .enchantChance(1)
                        .enchLevels(45, 35)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 210, 260)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.35F, 0.65F)
                        .modifier(Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1.9F, 2.30F)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(24F))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(40F))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, StepFunction.constant(1.25F)))
                .stats(stellar, c -> c
                        .enchantChance(1)
                        .enchLevels(55, 45)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 240, 290)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.35F, 0.65F)
                        .modifier(Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2.2F, 2.60F)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(27F))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(45F))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, StepFunction.constant(1.25F)))
                .stats(divine, c -> c
                        .enchantChance(1)
                        .enchLevels(55, 45)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 270, 320)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.35F, 0.65F)
                        .modifier(Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2.5F, 2.90F)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(30F))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(50F))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, StepFunction.constant(1.25F)))
                .stats(esoteric, c -> c
                        .enchantChance(1)
                        .enchLevels(55, 45)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 300, 350)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.35F, 0.65F)
                        .modifier(Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2.8F, 3.20F)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(33F))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(55F))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, StepFunction.constant(1.25F)))
                .stats(cataclysmic, c -> c
                        .enchantChance(1)
                        .enchLevels(65, 55)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 330, 380)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.35F, 0.65F)
                        .modifier(Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 3.1F, 3.5F)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(36F))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(60F))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, StepFunction.constant(1.25F))));

        addNadirBoss("overworld/skeleton", b -> nadirRangedStats(b)
                .entity(EntityType.SKELETON)
                .size(0.75, 2.45)
                .basicData(c -> rangedGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.FRONTIER, DEFAULT_WEIGHT, DEFAULT_QUALITY))
                        .constraints(Constraints.forDimension(Level.OVERWORLD))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS)));

        addNadirBoss("overworld/stray", b -> nadirRangedStats(b)
                .entity(EntityType.STRAY)
                .size(0.75, 2.45)
                .basicData(c -> rangedGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.FRONTIER, 50, DEFAULT_QUALITY))
                        .constraints(Constraints.forDimension(Level.OVERWORLD))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS)));

        addNadirBoss("overworld/bogged", b -> nadirRangedStats(b)
                .entity(EntityType.BOGGED)
                .size(0.75, 2.45)
                .basicData(c -> rangedGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.FRONTIER, 50, DEFAULT_QUALITY))
                        .constraints(Constraints.forDimension(Level.OVERWORLD))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS)));

        addNadirBoss("overworld/stray_in_cold", b -> nadirRangedStats(b)
                .entity(EntityType.STRAY)
                .size(0.75, 2.45)
                .basicData(c -> rangedGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.FRONTIER, 120, DEFAULT_QUALITY))
                        .constraints(Constraints.forBiomes(biomes, Tags.Biomes.IS_COLD_OVERWORLD))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS)));

        addNadirBoss("overworld/bogged_in_wet", b -> nadirRangedStats(b)
                .entity(EntityType.BOGGED)
                .size(0.75, 2.45)
                .basicData(c -> rangedGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.FRONTIER, 120, DEFAULT_QUALITY))
                        .constraints(Constraints.forBiomes(biomes, Tags.Biomes.IS_WET_OVERWORLD))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS)));

        addNadirBoss("overworld/breeze", b -> b
                .entity(EntityType.BREEZE)
                .size(1.2, 3.6)
                .basicData(c -> rangedGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.SUMMIT, 40, 1.5F))
                        .constraints(Constraints.forDimension(Level.OVERWORLD))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS, Apoth.LootTables.BONUS_RARE_BOSS_DROPS))
                .stats(legendary, c -> c
                        .enchantChance(0.85F)
                        .enchLevels(45, 35)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 140, 160)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 0.9F, 1.3F)
                        .modifier(ALObjects.Attributes.COLD_DAMAGE, Operation.ADD_VALUE, 35, 45)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(15))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(25))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(ancient, c -> c
                        .enchantChance(0.95F)
                        .enchLevels(55, 45)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 170, 190)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1.2F, 1.5F)
                        .modifier(ALObjects.Attributes.COLD_DAMAGE, Operation.ADD_VALUE, 40, 50)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(20))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(30))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(forgotten, c -> c
                        .enchantChance(1)
                        .enchLevels(65, 55)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 200, 220)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1.5F, 1.8F)
                        .modifier(ALObjects.Attributes.COLD_DAMAGE, Operation.ADD_VALUE, 45, 55)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(25))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(35))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(primal, c -> c
                        .enchantChance(1)
                        .enchLevels(75, 65)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 230, 250)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1.8F, 2.1F)
                        .modifier(ALObjects.Attributes.COLD_DAMAGE, Operation.ADD_VALUE, 50, 60)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(30))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(40))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(stellar, c -> c
                        .enchantChance(1)
                        .enchLevels(85, 75)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 260, 280)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2.1F, 2.4F)
                        .modifier(ALObjects.Attributes.COLD_DAMAGE, Operation.ADD_VALUE, 55, 65)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(35))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(45))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(divine, c -> c
                        .enchantChance(1)
                        .enchLevels(95, 85)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 290, 310)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2.4F, 2.7F)
                        .modifier(ALObjects.Attributes.COLD_DAMAGE, Operation.ADD_VALUE, 60, 70)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(40))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(50))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(esoteric, c -> c
                        .enchantChance(1)
                        .enchLevels(110, 100)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 320, 340)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2.7F, 3)
                        .modifier(ALObjects.Attributes.COLD_DAMAGE, Operation.ADD_VALUE, 65, 75)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(45))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(55))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(cataclysmic, c -> c
                        .enchantChance(1)
                        .enchLevels(120, 110)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 350, 370)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2, 2.5F)
                        .modifier(ALObjects.Attributes.COLD_DAMAGE, Operation.ADD_VALUE, 70, 80)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(50))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(60))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F)));

        // Nether

        addNadirBoss("the_nether/zombified_piglin", b -> nadirMeleeStats(b)
                .entity(EntityType.ZOMBIFIED_PIGLIN)
                .size(0.75, 2.45)
                .basicData(c -> meleeGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.FRONTIER, DEFAULT_WEIGHT, DEFAULT_QUALITY))
                        .constraints(Constraints.forDimension(Level.NETHER))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS)));

        addNadirBoss("the_nether/piglin", b -> nadirMeleeStats(b)
                .entity(EntityType.PIGLIN)
                .size(0.75, 2.45)
                .basicData(c -> meleeGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.FRONTIER, DEFAULT_WEIGHT, DEFAULT_QUALITY))
                        .constraints(Constraints.forDimension(Level.NETHER))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS)
                        .nbt(t -> {
                            t.putBoolean("CannotHunt", true);
                            t.putBoolean("ImmuneToZombification", true);
                        })));

        addNadirBoss("the_nether/piglin_brute", b -> nadirMeleeStats(b)
                .entity(EntityType.PIGLIN_BRUTE)
                .size(0.75, 2.45)
                .basicData(c -> meleeGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.ASCENT, DEFAULT_WEIGHT, 1.5F))
                        .constraints(Constraints.forDimension(Level.NETHER))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS, Apoth.LootTables.BONUS_RARE_BOSS_DROPS)
                        .nbt(t -> {
                            t.putBoolean("ImmuneToZombification", true);
                        })));

        addNadirBoss("the_nether/zoglin", b -> nadirMeleeStats(b)
                .entity(EntityType.ZOGLIN)
                .size(2, 2)
                .basicData(c -> meleeGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.FRONTIER, DEFAULT_WEIGHT, 2))
                        .constraints(Constraints.forDimension(Level.NETHER))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS)));

        addNadirBoss("the_nether/wither_skeleton", b -> b
                .entity(EntityType.WITHER_SKELETON)
                .size(0.75, 3.7)
                .basicData(c -> rangedGear(meleeGear(c))
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.ASCENT, DEFAULT_WEIGHT, 1.5F))
                        .constraints(Constraints.forDimension(Level.NETHER))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS, Apoth.LootTables.BONUS_RARE_BOSS_DROPS))
                .stats(legendary, c -> c
                        .enchantChance(0.85F)
                        .enchLevels(45, 35)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 140, 160)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 0.9F, 1.3F)
                        .modifier(ALObjects.Attributes.FIRE_DAMAGE, Operation.ADD_VALUE, 20, 25)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(15))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(25))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(ancient, c -> c
                        .enchantChance(0.95F)
                        .enchLevels(55, 45)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 170, 190)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1.2F, 1.5F)
                        .modifier(ALObjects.Attributes.FIRE_DAMAGE, Operation.ADD_VALUE, 30, 40)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(20))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(30))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(forgotten, c -> c
                        .enchantChance(1)
                        .enchLevels(65, 55)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 200, 220)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1.5F, 1.8F)
                        .modifier(ALObjects.Attributes.FIRE_DAMAGE, Operation.ADD_VALUE, 40, 50)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(25))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(35))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(primal, c -> c
                        .enchantChance(1)
                        .enchLevels(75, 65)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 230, 250)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1.8F, 2.1F)
                        .modifier(ALObjects.Attributes.FIRE_DAMAGE, Operation.ADD_VALUE, 50, 60)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(30))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(40))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(stellar, c -> c
                        .enchantChance(1)
                        .enchLevels(85, 75)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 260, 280)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2.1F, 2.4F)
                        .modifier(ALObjects.Attributes.FIRE_DAMAGE, Operation.ADD_VALUE, 60, 70)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(35))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(45))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(divine, c -> c
                        .enchantChance(1)
                        .enchLevels(95, 85)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 290, 310)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2.4F, 2.7F)
                        .modifier(ALObjects.Attributes.FIRE_DAMAGE, Operation.ADD_VALUE, 70, 80)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(40))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(50))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(esoteric, c -> c
                        .enchantChance(1)
                        .enchLevels(110, 100)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 320, 340)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2.7F, 3)
                        .modifier(ALObjects.Attributes.FIRE_DAMAGE, Operation.ADD_VALUE, 80, 90)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(45))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(55))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(cataclysmic, c -> c
                        .enchantChance(1)
                        .enchLevels(120, 110)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 350, 370)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2, 2.5F)
                        .modifier(ALObjects.Attributes.FIRE_DAMAGE, Operation.ADD_VALUE, 90, 100)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(50))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(60))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F)));

        addNadirBoss("the_nether/blaze", b -> b
                .entity(EntityType.BLAZE)
                .size(1.2, 3.6)
                .basicData(c -> rangedGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.SUMMIT, DEFAULT_WEIGHT, 1.5F))
                        .constraints(Constraints.forDimension(Level.NETHER))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS, Apoth.LootTables.BONUS_RARE_BOSS_DROPS))
                .stats(legendary, c -> c
                        .enchantChance(0.85F)
                        .enchLevels(45, 35)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 140, 160)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 0.9F, 1.3F)
                        .modifier(ALObjects.Attributes.FIRE_DAMAGE, Operation.ADD_VALUE, 40, 50)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(15))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(25))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(ancient, c -> c
                        .enchantChance(0.95F)
                        .enchLevels(55, 45)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 170, 190)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1.2F, 1.5F)
                        .modifier(ALObjects.Attributes.FIRE_DAMAGE, Operation.ADD_VALUE, 50, 60)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(20))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(30))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(forgotten, c -> c
                        .enchantChance(1)
                        .enchLevels(65, 55)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 200, 220)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1.5F, 1.8F)
                        .modifier(ALObjects.Attributes.FIRE_DAMAGE, Operation.ADD_VALUE, 60, 70)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(25))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(35))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(primal, c -> c
                        .enchantChance(1)
                        .enchLevels(75, 65)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 230, 250)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1.8F, 2.1F)
                        .modifier(ALObjects.Attributes.FIRE_DAMAGE, Operation.ADD_VALUE, 60, 70)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(30))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(40))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(stellar, c -> c
                        .enchantChance(1)
                        .enchLevels(85, 75)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 260, 280)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2.1F, 2.4F)
                        .modifier(ALObjects.Attributes.FIRE_DAMAGE, Operation.ADD_VALUE, 70, 80)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(35))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(45))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(divine, c -> c
                        .enchantChance(1)
                        .enchLevels(95, 85)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 290, 310)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2.4F, 2.7F)
                        .modifier(ALObjects.Attributes.FIRE_DAMAGE, Operation.ADD_VALUE, 80, 90)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(40))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(50))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(esoteric, c -> c
                        .enchantChance(1)
                        .enchLevels(110, 100)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 320, 340)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2.7F, 3)
                        .modifier(ALObjects.Attributes.FIRE_DAMAGE, Operation.ADD_VALUE, 90, 100)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(45))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(55))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(cataclysmic, c -> c
                        .enchantChance(1)
                        .enchLevels(120, 110)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 350, 370)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2, 2.5F)
                        .modifier(ALObjects.Attributes.FIRE_DAMAGE, Operation.ADD_VALUE, 100, 110)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(50))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(60))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F)));

        addNadirBoss("the_end/enderman", b -> nadirMeleeStats(b)
                .entity(EntityType.ENDERMAN)
                .size(0.75, 3.7)
                .basicData(c -> meleeGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.FRONTIER, DEFAULT_WEIGHT, DEFAULT_QUALITY))
                        .constraints(Constraints.forDimension(Level.END))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS)));

        addNadirBoss("the_end/endermite", b -> nadirMeleeStats(b)
                .entity(EntityType.ENDERMITE)
                .size(0.5, 0.5)
                .basicData(c -> meleeGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.FRONTIER, 5, 2))
                        .constraints(Constraints.forDimension(Level.END))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS)));

        addNadirBoss("the_end/shulker", b -> nadirRangedStats(b)
                .entity(EntityType.SHULKER)
                .size(1.25, 1.25)
                .basicData(c -> rangedGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.FRONTIER, 20, 2))
                        .constraints(Constraints.forDimension(Level.END))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS)));

        addNadirBoss("the_end/phantom", b -> nadirMeleeStats(b)
                .entity(EntityType.PHANTOM)
                .size(1.2, 0.75)
                .basicData(c -> meleeGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.ASCENT, 50, DEFAULT_QUALITY))
                        .constraints(Constraints.forDimension(Level.END))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS, Apoth.LootTables.BONUS_RARE_BOSS_DROPS)));

        addNadirBoss("the_end/evoker", b -> nadirRangedStats(b)
                .entity(EntityType.EVOKER)
                .size(0.75, 2.45)
                .basicData(c -> rangedGear(c)
                        .name(Component.literal(BasicBossData.NAME_GEN))
                        .weights(TieredWeights.forTiersAbove(WorldTier.SUMMIT, DEFAULT_WEIGHT, 1.5F))
                        .constraints(Constraints.forDimension(Level.END))
                        .bonusLoot(Apoth.LootTables.BONUS_BOSS_DROPS, Apoth.LootTables.BONUS_RARE_BOSS_DROPS)));

    }

    private Invader.Builder nadirMeleeStats(Invader.Builder builder) {
        LootRarity legendary = nadirRarity("legendary");
        LootRarity ancient = nadirRarity("ancient");
        LootRarity forgotten = nadirRarity("forgotten");
        LootRarity primal = nadirRarity("primal");
        LootRarity stellar = nadirRarity("stellar");
        LootRarity divine = nadirRarity("divine");
        LootRarity esoteric = nadirRarity("esoteric");
        LootRarity cataclysmic = nadirRarity("cataclysmic");

        return builder
                .stats(legendary, c -> c
                        .enchantChance(0.95F)
                        .enchLevels(45, 35)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 140, 210)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1.1F, 1.45F)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(15))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(30F))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(ancient, c -> c
                        .enchantChance(1)
                        .enchLevels(55, 45)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 170, 240)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1.5F, 2)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(20))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(35))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(forgotten, c -> c
                        .enchantChance(1)
                        .enchLevels(65, 55)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 200, 270)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1.9F, 2.4F)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(25))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(40))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(primal, c -> c
                        .enchantChance(1)
                        .enchLevels(75, 65)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 230, 300)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2.5F, 3)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(30))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(45))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(stellar, c -> c
                        .enchantChance(1)
                        .enchLevels(85, 75)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 260, 330)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 3, 3.5F)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(35))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(50))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(divine, c -> c
                        .enchantChance(1)
                        .enchLevels(95, 85)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 290, 360)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 3.5F, 4)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(40))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(55))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(esoteric, c -> c
                        .enchantChance(1)
                        .enchLevels(110, 100)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 320, 390)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 4, 4.5F)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(45))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(60))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(cataclysmic, c -> c
                        .enchantChance(1)
                        .enchLevels(120, 110)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 350, 420)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 4.5F, 5)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(50))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(65))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F));
    }

    private Invader.Builder nadirRangedStats(Invader.Builder builder) {
        LootRarity legendary = nadirRarity("legendary");
        LootRarity ancient = nadirRarity("ancient");
        LootRarity forgotten = nadirRarity("forgotten");
        LootRarity primal = nadirRarity("primal");
        LootRarity stellar = nadirRarity("stellar");
        LootRarity divine = nadirRarity("divine");
        LootRarity esoteric = nadirRarity("esoteric");
        LootRarity cataclysmic = nadirRarity("cataclysmic");

        return builder
                .stats(legendary, c -> c
                        .enchantChance(0.85F)
                        .enchLevels(45, 35)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 140, 160)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 0.9F, 1.3F)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(15))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(25))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(ancient, c -> c
                        .enchantChance(0.95F)
                        .enchLevels(55, 45)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 170, 190)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1.2F, 1.5F)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(20))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(30))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(forgotten, c -> c
                        .enchantChance(1)
                        .enchLevels(65, 55)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 200, 220)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1.5F, 1.8F)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(25))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(35))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(primal, c -> c
                        .enchantChance(1)
                        .enchLevels(75, 65)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 230, 250)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 1.8F, 2.1F)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(30))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(40))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(stellar, c -> c
                        .enchantChance(1)
                        .enchLevels(85, 75)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 260, 280)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2.1F, 2.4F)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(35))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(45))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(divine, c -> c
                        .enchantChance(1)
                        .enchLevels(95, 85)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 290, 310)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2.4F, 2.7F)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(40))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(50))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(esoteric, c -> c
                        .enchantChance(1)
                        .enchLevels(110, 100)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 320, 340)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2.7F, 3)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(45))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(55))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F))
                .stats(cataclysmic, c -> c
                        .enchantChance(1)
                        .enchLevels(120, 110)
                        .effect(1, MobEffects.FIRE_RESISTANCE)
                        .modifier(Attributes.MAX_HEALTH, Operation.ADD_VALUE, 350, 370)
                        .modifier(Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.3F, 0.65F)
                        .modifier(ALObjects.Attributes.PROJECTILE_DAMAGE, Operation.ADD_MULTIPLIED_BASE, 2, 2.5F)
                        .modifier(Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_VALUE, StepFunction.constant(1))
                        .modifier(Attributes.ARMOR, Operation.ADD_VALUE, StepFunction.constant(50))
                        .modifier(Attributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, StepFunction.constant(60))
                        .modifier(Attributes.SCALE, Operation.ADD_MULTIPLIED_TOTAL, -0.15F, 0.25F));
    }

    public static BasicBossData.Builder meleeGear(BasicBossData.Builder builder) {
        builder.gearSets(WorldTier.HAVEN, "#haven_melee");
        builder.gearSets(WorldTier.FRONTIER, "#frontier_melee");
        builder.gearSets(WorldTier.ASCENT, "#ascent_melee");
        builder.gearSets(WorldTier.SUMMIT, "#summit_melee");
        builder.gearSets(WorldTier.PINNACLE, "#apotheosis_melee");
        return builder;
    }

    public static BasicBossData.Builder rangedGear(BasicBossData.Builder builder) {
        builder.gearSets(WorldTier.HAVEN, "#haven_ranged");
        builder.gearSets(WorldTier.FRONTIER, "#frontier_ranged");
        builder.gearSets(WorldTier.ASCENT, "#ascent_ranged");
        builder.gearSets(WorldTier.SUMMIT, "#summit_ranged");
        builder.gearSets(WorldTier.PINNACLE, "#apotheosis_ranged");
        return builder;
    }

    private void addNadirBoss(String name, UnaryOperator<Invader.Builder> builder) {
        this.add(Nadir.loc(name), builder.apply(Invader.builder()).build());
    }

    private static LootRarity nadirRarity(String path) {
        return Preconditions.checkNotNull(RarityRegistry.INSTANCE.getValue(Nadir.loc(path)));
    }
}
