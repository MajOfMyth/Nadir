package com.MajOfMyth.Nadir.data;

import com.MajOfMyth.Nadir.Nad;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class NLootProvider  extends LootTableProvider {

    public NLootProvider(PackOutput output, Set<ResourceKey<LootTable>> requiredTables, List<SubProviderEntry> subProviders, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, requiredTables, subProviders, registries);
    }

    public static NLootProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        return new NLootProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK)
        ), registries);
    }

    public static class BlockLoot extends BlockLootSubProvider {
        private final Set<Block> generatedLootTables = new HashSet();

        public BlockLoot(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        protected void generate () {
            this.dropSelf(Nad.Blocks.ANCIENT_REFORGING_TABLE.value());
            this.dropSelf(Nad.Blocks.DIVINE_REFORGING_TABLE.value());
            this.dropSelf(Nad.Blocks.CATACLYSMIC_REFORGING_TABLE.value());
        }

        protected void add (Block block, LootTable.Builder builder){
            this.generatedLootTables.add(block);
            this.map.put(block.getLootTable(), builder);
        }

        protected Iterable<Block> getKnownBlocks () {
            return this.generatedLootTables;
        }
    }
}