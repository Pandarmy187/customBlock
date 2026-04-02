package de.pandarmy.customblock;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class Customblock implements ModInitializer {

    public static final String MOD_ID = "tinmod";

    public static final Block TIN_ORE = registerBlock("tin_ore", settings -> new Block(settings.strength(3.0f).sounds(BlockSoundGroup.STONE).requiresTool()));
    public static final Block CRUCIBLE_FURNACE = registerBlock("crucible_furnace", settings ->
            new CrucibleFurnaceBlock(settings
                    .strength(3.5f)
                    .sounds(BlockSoundGroup.STONE)
                    .requiresTool()
            )
    );

    public static final Item RAW_TIN = registerItem("raw_tin", Item::new);
    public static final Item TIN_INGOT = registerItem("tin_ingot", Item::new);


    @Override
    public void onInitialize() {

        OreGeneration.generateOres();
        //Tab Menu
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(TIN_ORE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.add(CRUCIBLE_FURNACE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(RAW_TIN);
            entries.add(TIN_INGOT);
        });

    }

    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> blockFactory) {
        Identifier id = Identifier.of(MOD_ID, name);
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, id);
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, id);

        Block block = blockFactory.apply(AbstractBlock.Settings.create().registryKey(blockKey));

        Registry.register(Registries.BLOCK, blockKey, block);

        BlockItem blockItem = new BlockItem(block, new Item.Settings()
                .registryKey(itemKey)
                .useBlockPrefixedTranslationKey());

        Registry.register(Registries.ITEM, itemKey, blockItem);

        return block;
    }


    private static Item registerItem(String name, Function<Item.Settings, Item> itemFactory) {
        Identifier id = Identifier.of(MOD_ID, name);
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, id);

        Item item = itemFactory.apply(new Item.Settings().registryKey(itemKey));

        return Registry.register(Registries.ITEM, itemKey, item);
    }
}