package com.MajOfMyth.Nadir.data;

import com.MajOfMyth.Nadir.Nad;
import com.MajOfMyth.Nadir.Nadir;
import dev.shadowsoffire.apotheosis.Apoth;
import dev.shadowsoffire.apotheosis.Apotheosis;
import dev.shadowsoffire.apotheosis.affix.reforging.ReforgingRecipe;
import dev.shadowsoffire.apotheosis.affix.salvaging.SalvagingRecipe;
import dev.shadowsoffire.apotheosis.loot.LootRarity;
import dev.shadowsoffire.apotheosis.loot.RarityRegistry;
import dev.shadowsoffire.apotheosis.socket.AddSocketsRecipe;
import dev.shadowsoffire.apotheosis.socket.gem.Purity;
import dev.shadowsoffire.apotheosis.socket.gem.cutting.PurityUpgradeRecipe;
import dev.shadowsoffire.apotheosis.util.AffixItemIngredient;
import dev.shadowsoffire.apotheosis.util.GemIngredient;
import dev.shadowsoffire.placebo.datagen.LegacyRecipeProvider;
import dev.shadowsoffire.placebo.reload.DynamicHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class NRecipeProvider extends LegacyRecipeProvider {
    public NRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Nadir.MODID);
    }

    @Override
    protected void genRecipes(RecipeOutput recipeOutput, HolderLookup.Provider registries) {

        //salvage affix items for rarity materials
        addAffixSalvaging("worthless", Nad.Items.WORTHLESS_MATERIAL);
        addAffixSalvaging("legendary", Nad.Items.LEGENDARY_MATERIAL);
        addAffixSalvaging("ancient", Nad.Items.ANCIENT_MATERIAL);
        addAffixSalvaging("forgotten", Nad.Items.FORGOTTEN_MATERIAL);
        addAffixSalvaging("primal", Nad.Items.PRIMAL_MATERIAL);
        addAffixSalvaging("stellar", Nad.Items.STELLAR_MATERIAL);
        addAffixSalvaging("divine", Nad.Items.DIVINE_MATERIAL);
        addAffixSalvaging("esoteric", Nad.Items.ESOTERIC_MATERIAL);
        addAffixSalvaging("cataclysmic", Nad.Items.CATACLYSMIC_MATERIAL);

        //base apotheosis rarity reforges for nadir tables
        addReforging("common", 1, 0, 2, Nad.Blocks.ANCIENT_REFORGING_TABLE, Nad.Blocks.DIVINE_REFORGING_TABLE, Nad.Blocks.CATACLYSMIC_REFORGING_TABLE);
        addReforging("uncommon", 1, 1, 5, Nad.Blocks.ANCIENT_REFORGING_TABLE, Nad.Blocks.DIVINE_REFORGING_TABLE, Nad.Blocks.CATACLYSMIC_REFORGING_TABLE);
        addReforging("rare", 2, 2, 15, Nad.Blocks.ANCIENT_REFORGING_TABLE, Nad.Blocks.DIVINE_REFORGING_TABLE, Nad.Blocks.CATACLYSMIC_REFORGING_TABLE);
        addReforging("epic", 2, 4, 20, Nad.Blocks.ANCIENT_REFORGING_TABLE, Nad.Blocks.DIVINE_REFORGING_TABLE, Nad.Blocks.CATACLYSMIC_REFORGING_TABLE);
        addReforging("mythic", 2, 5, 25, Nad.Blocks.ANCIENT_REFORGING_TABLE, Nad.Blocks.DIVINE_REFORGING_TABLE, Nad.Blocks.CATACLYSMIC_REFORGING_TABLE);

        //nadir rarity reforges
        addNadirReforging("worthless", 1, 0, 1, Apoth.Blocks.SIMPLE_REFORGING_TABLE, Apoth.Blocks.REFORGING_TABLE, Nad.Blocks.ANCIENT_REFORGING_TABLE, Nad.Blocks.DIVINE_REFORGING_TABLE, Nad.Blocks.CATACLYSMIC_REFORGING_TABLE);
        addNadirReforging("legendary", 3, 5, 30, Apoth.Blocks.REFORGING_TABLE, Nad.Blocks.ANCIENT_REFORGING_TABLE, Nad.Blocks.DIVINE_REFORGING_TABLE, Nad.Blocks.CATACLYSMIC_REFORGING_TABLE);
        addNadirReforging("ancient", 3, 5, 40, Nad.Blocks.ANCIENT_REFORGING_TABLE, Nad.Blocks.DIVINE_REFORGING_TABLE, Nad.Blocks.CATACLYSMIC_REFORGING_TABLE);
        addNadirReforging("forgotten", 3, 5, 50, Nad.Blocks.ANCIENT_REFORGING_TABLE, Nad.Blocks.DIVINE_REFORGING_TABLE, Nad.Blocks.CATACLYSMIC_REFORGING_TABLE);
        addNadirReforging("primal", 4, 6, 60, Nad.Blocks.ANCIENT_REFORGING_TABLE, Nad.Blocks.DIVINE_REFORGING_TABLE, Nad.Blocks.CATACLYSMIC_REFORGING_TABLE);
        addNadirReforging("stellar", 4, 6, 70, Nad.Blocks.DIVINE_REFORGING_TABLE, Nad.Blocks.CATACLYSMIC_REFORGING_TABLE);
        addNadirReforging("divine", 4, 6, 80, Nad.Blocks.DIVINE_REFORGING_TABLE, Nad.Blocks.CATACLYSMIC_REFORGING_TABLE);
        addNadirReforging("esoteric", 6, 8, 90, Nad.Blocks.DIVINE_REFORGING_TABLE, Nad.Blocks.CATACLYSMIC_REFORGING_TABLE);
        addNadirReforging("cataclysmic", 6, 8, 100, Nad.Blocks.CATACLYSMIC_REFORGING_TABLE);

        //gem upgrading

        //table recipes
        addShaped(Nad.Blocks.ANCIENT_REFORGING_TABLE, 3, 3,
                Nad.Items.LEGENDARY_MATERIAL, Items.HEAVY_CORE, Nad.Items.LEGENDARY_MATERIAL,
                Nad.Items.LEGENDARY_MATERIAL, Apoth.Items.REFORGING_TABLE, Nad.Items.LEGENDARY_MATERIAL,
                Nad.Items.LEGENDARY_MATERIAL, Nad.Items.LEGENDARY_MATERIAL, Nad.Items.LEGENDARY_MATERIAL
        );
        addShaped(Nad.Blocks.DIVINE_REFORGING_TABLE, 3, 3,
                Nad.Items.PRIMAL_MATERIAL, Items.NETHER_STAR, Nad.Items.PRIMAL_MATERIAL,
                Nad.Items.PRIMAL_MATERIAL, Nad.Blocks.ANCIENT_REFORGING_TABLE, Nad.Items.PRIMAL_MATERIAL,
                Nad.Items.PRIMAL_MATERIAL, Nad.Items.PRIMAL_MATERIAL, Nad.Items.PRIMAL_MATERIAL
        );
        addShaped(Nad.Blocks.CATACLYSMIC_REFORGING_TABLE, 3, 3,
                Nad.Items.ESOTERIC_MATERIAL, Nad.Items.CATACLYSMIC_MATERIAL, Nad.Items.ESOTERIC_MATERIAL,
                Nad.Items.ESOTERIC_MATERIAL, Nad.Blocks.DIVINE_REFORGING_TABLE, Nad.Items.ESOTERIC_MATERIAL,
                Nad.Items.ESOTERIC_MATERIAL, Nad.Items.ESOTERIC_MATERIAL, Nad.Items.ESOTERIC_MATERIAL
        );
    }

    private void addPurityUpgrade(Purity purity, int gemDust, List<Holder<Item>> materials, int zerothMatCost) {
        SizedIngredient dustIng = SizedIngredient.of(Apoth.Items.GEM_DUST.value(), gemDust);
        List<SizedIngredient> materialIngs = new ArrayList<>();
        int matAmount = zerothMatCost;
        for (Holder<Item> mat : materials) {
            materialIngs.add(SizedIngredient.of(mat.value(), matAmount));
            matAmount /= 3;
        }
        var recipe = new PurityUpgradeRecipe(purity, List.of(dustIng), materialIngs);
        this.recipeOutput.accept(Nadir.loc("gem_cutting/" + purity.name().toLowerCase(Locale.ROOT)), recipe, null);
    }


    @SafeVarargs
    private void addReforging(String rarity, int mats, int sigils, int levels, Holder<Block>... tables) {
        DynamicHolder<LootRarity> lRarity = RarityRegistry.INSTANCE.holder(Apotheosis.loc(rarity));
        this.recipeOutput.accept(Nadir.loc("reforging/" + rarity), new ReforgingRecipe(lRarity, mats, sigils, levels, HolderSet.direct(tables)), null);
    }

    @SafeVarargs
    private void addNadirReforging(String rarity, int mats, int sigils, int levels, Holder<Block>... tables) {
        DynamicHolder<LootRarity> lRarity = RarityRegistry.INSTANCE.holder(Nadir.loc(rarity));
        this.recipeOutput.accept(Nadir.loc("reforging/" + rarity), new ReforgingRecipe(lRarity, mats, sigils, levels, HolderSet.direct(tables)), null);
    }

    private void addGemSalvaging(Purity purity, int min, int max) {
        Ingredient input = new Ingredient(new GemIngredient(purity));
        SalvagingRecipe.OutputData output = new SalvagingRecipe.OutputData(new ItemStack(Apoth.Items.GEM_DUST), min, max);
        addSalvaging("gem/" + purity.getSerializedName(), input, output);
    }

    private void addAffixSalvaging(String rarity, Holder<Item> material) {
        Ingredient input = new Ingredient(new AffixItemIngredient(RarityRegistry.INSTANCE.holder(Nadir.loc(rarity))));
        SalvagingRecipe.OutputData output = new SalvagingRecipe.OutputData(new ItemStack(material), 1, 4);
        addSalvaging("affix_item/" + rarity, input, output);
    }

    private void addOtherSalvaging(String path, Ingredient input, SalvagingRecipe.OutputData output) {
        addSalvaging("salvaging/other/" + path, input, List.of(output));
    }

    private void addSalvaging(String path, Ingredient input, SalvagingRecipe.OutputData output) {
        addSalvaging("salvaging/" + path, input, List.of(output));
    }

    private void addSalvaging(String path, Ingredient input, List<SalvagingRecipe.OutputData> outputs) {
        this.recipeOutput.accept(Nadir.loc(path), new SalvagingRecipe(input, outputs), null);
    }

    private void addSockets(String path, Ingredient input, int maxSockets) {
        this.recipeOutput.accept(Nadir.loc(path), new AddSocketsRecipe(input, maxSockets), null);
    }
}
