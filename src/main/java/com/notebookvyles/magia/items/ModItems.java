package com.notebookvyles.magia.items;

import com.notebookvyles.magia.MagiaMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import java.util.ArrayList;
import java.util.List;

/** Registro dos itens. Os modelos vêm dos .bbmodel originais convertidos por tools/bbmodel_to_json.py. */
public final class ModItems {
    private ModItems() {}

    public static final List<Item> ALL = new ArrayList<>();

    public static final Item AMETHYST_WAND = wand("amethyst_wand");
    public static final Item BONE_WAND = wand("bone_wand");
    public static final Item CELESTIAL_WAND = wand("celestial_wand");
    public static final Item DRAGON_WAND = wand("dragon_wand");
    public static final Item EMERALD_WAND = wand("emerald_wand");
    public static final Item MAGMA_WAND = wand("magma_wand");
    public static final Item WIDOW_WAND = wand("widow_wand");
    public static final Item WITCH_BROOM = register("witch_broom", new Item.Settings().maxCount(1));
    public static final Item WITCH_HAT = register("witch_hat", new Item.Settings().maxCount(1)
            .component(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(EquipmentSlot.HEAD).build()));

    private static Item wand(String id) { return register(id, new Item.Settings().maxCount(1)); }

    private static Item register(String id, Item.Settings settings) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MagiaMod.MOD_ID, id));
        Item item = Registry.register(Registries.ITEM, key, new Item(settings.registryKey(key)));
        ALL.add(item);
        return item;
    }

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> ALL.forEach(entries::add));
    }
}
