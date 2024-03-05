package com.unnamedtemp.xuan;

import com.unnamedtemp.xuan.world.EnvNaturity;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.*;

import java.util.function.Supplier;

//注册表母类
public final class Register {
    public static void register(IEventBus modEventBus) {
        Blocks.register(modEventBus);
        Items.register(modEventBus);
        CreativeModeTabs.register(modEventBus);
        AttachmentTypes.register(modEventBus);
    }

    //方块和方块物品
    public final static class Blocks {
        private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Xuan.MODID);
        private static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(Xuan.MODID);
        public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
        public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = BLOCK_ITEMS.registerSimpleBlockItem("example_block", Blocks.EXAMPLE_BLOCK);

        private static void register(IEventBus modEventBus) {
            BLOCKS.register(modEventBus);
            BLOCK_ITEMS.register(modEventBus);
        }
    }

    //一般物品
    public final static class Items {
        private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Xuan.MODID);
        public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
                .alwaysEat().nutrition(1).saturationMod(2f).build()));

        private static void register(IEventBus modEventBus) {
            ITEMS.register(modEventBus);
        }
    }

    //创造模式选项卡
    @SuppressWarnings("unused")
    public final static class CreativeModeTabs {
        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Xuan.MODID);
        public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.xuan")) //The language key for the title of your CreativeModeTab
                .withTabsBefore(net.minecraft.world.item.CreativeModeTabs.COMBAT)
                .icon(() -> Items.EXAMPLE_ITEM.get().getDefaultInstance())
                .displayItems((parameters, output) -> {
                    output.accept(Items.EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
                }).build());

        // Add items to the vanilla tab
        private static void addCreative(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == net.minecraft.world.item.CreativeModeTabs.BUILDING_BLOCKS)
                event.accept(Blocks.EXAMPLE_BLOCK_ITEM);
        }

        private static void register(IEventBus modEventBus) {
            CREATIVE_MODE_TABS.register(modEventBus);
            modEventBus.addListener(CreativeModeTabs::addCreative);
        }
    }

    //附加数据类型
    @SuppressWarnings("unused")
    public final static class AttachmentTypes {
        private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Xuan.MODID);
        public static final Supplier<AttachmentType<EnvNaturity>> ENV_NATURITY =
                ATTACHMENT_TYPES.register("env_naturity", () -> AttachmentType.builder(EnvNaturity::new).serialize(EnvNaturity.CODEC).build());

        private static void register(IEventBus modEventBus) {
            ATTACHMENT_TYPES.register(modEventBus);
        }
    }
}
