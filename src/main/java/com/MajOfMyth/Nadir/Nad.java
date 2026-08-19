package com.MajOfMyth.Nadir;

import com.MajOfMyth.Nadir.block.NadirReforgingTableTile;
import com.MajOfMyth.Nadir.block.NadirReforgingTableBlock;
import com.MajOfMyth.Nadir.color.NColors;
import com.MajOfMyth.Nadir.screen.NadirReforgingMenu;
import dev.shadowsoffire.placebo.block_entity.TickingBlockEntityType;
import dev.shadowsoffire.placebo.color.GradientColor;
import dev.shadowsoffire.placebo.registry.DeferredHelper;
import dev.shadowsoffire.apotheosis.affix.salvaging.SalvageItem;
import dev.shadowsoffire.apotheosis.loot.RarityRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;

public class Nad {
    private static final DeferredHelper R = DeferredHelper.create(Nadir.MODID);

    public static class Blocks {
        public static final Holder<Block> ANCIENT_REFORGING_TABLE = R.block("ancient_reforging_table", NadirReforgingTableBlock::new, p -> p.requiresCorrectToolForDrops().strength(4, 1000F));
        public static final Holder<Block> DIVINE_REFORGING_TABLE = R.block("divine_reforging_table", NadirReforgingTableBlock::new, p -> p.requiresCorrectToolForDrops().strength(4, 1000F));
        public static final Holder<Block> CATACLYSMIC_REFORGING_TABLE = R.block("cataclysmic_reforging_table", NadirReforgingTableBlock::new, p -> p.requiresCorrectToolForDrops().strength(4, 1000F));

        private static void bootstrap() {}
    }


    public static class Items {

        public static final Holder<Item> WORTHLESS_MATERIAL = rarityMat("worthless");
        public static final Holder<Item> LEGENDARY_MATERIAL = rarityMat("legendary");
        public static final Holder<Item> ANCIENT_MATERIAL = rarityMat("ancient");
        public static final Holder<Item> FORGOTTEN_MATERIAL = rarityMat("forgotten");
        public static final Holder<Item> PRIMAL_MATERIAL = rarityMat("primal");
        public static final Holder<Item> STELLAR_MATERIAL = rarityMat("stellar");
        public static final Holder<Item> DIVINE_MATERIAL = rarityMat("divine");
        public static final Holder<Item> ESOTERIC_MATERIAL = rarityMat("esoteric");
        public static final Holder<Item> CATACLYSMIC_MATERIAL = rarityMat("cataclysmic");

        public static final Holder<Item> ANCIENT_REFORGING_TABLE = R.blockItem("ancient_reforging_table", Blocks.ANCIENT_REFORGING_TABLE,
                p -> p.component(
                        DataComponents.ITEM_NAME,
                        Component.translatable("block.nadir.ancient_reforging_table").withStyle(
                                s -> s.withColor(0xAA0000))));
        public static final Holder<Item> DIVINE_REFORGING_TABLE = R.blockItem("divine_reforging_table", Blocks.DIVINE_REFORGING_TABLE,
                p -> p.component(
                        DataComponents.ITEM_NAME,
                        Component.translatable("block.nadir.divine_reforging_table").withStyle(
                                s -> s.withColor(NColors.DIVINE))));
        public static final Holder<Item> CATACLYSMIC_REFORGING_TABLE = R.blockItem("cataclysmic_reforging_table", Blocks.CATACLYSMIC_REFORGING_TABLE,
                p -> p.component(
                        DataComponents.ITEM_NAME,
                        Component.translatable("block.nadir.cataclysmic_reforging_table").withStyle(
                                s -> s.withColor(GradientColor.RAINBOW))));

        private static Holder<Item> rarityMat(String id) {
            return R.item(id + "_material", () -> new SalvageItem(RarityRegistry.INSTANCE.holder(Nadir.loc(id)), new Item.Properties()));
        }

        private static void bootstrap() {}
    }


    public static class Tiles {
        public static final BlockEntityType<NadirReforgingTableTile> NADIR_REFORGING_TABLE = R.tickingBlockEntity("nadir_reforging_table", NadirReforgingTableTile::new, TickingBlockEntityType.TickSide.CLIENT,
                Blocks.ANCIENT_REFORGING_TABLE,
                Blocks.DIVINE_REFORGING_TABLE,
                Blocks.CATACLYSMIC_REFORGING_TABLE
        );

        private static void bootstrap() {}
    }

    public static class Menus {
        public static final MenuType<NadirReforgingMenu> NADIR_REFORGING = R.menuWithPos("nadir_reforging", NadirReforgingMenu::new);

        private static void bootstrap() {}
    }

    public static void bootstrap(IEventBus bus) {
        bus.register(R);

        Blocks.bootstrap();
        Items.bootstrap();
        Tiles.bootstrap();
        Menus.bootstrap();
    }
}