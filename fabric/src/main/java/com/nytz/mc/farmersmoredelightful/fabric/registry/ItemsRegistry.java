package com.nytz.mc.farmersmoredelightful.fabric.registry;

import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import com.nhoryzon.mc.farmersdelight.item.ConsumableItem;
import com.nhoryzon.mc.farmersdelight.item.DogFoodItem;
import com.nhoryzon.mc.farmersdelight.item.HorseFeedItem;
import com.nhoryzon.mc.farmersdelight.item.HotCocoaItem;
import com.nhoryzon.mc.farmersdelight.item.KnifeItem;
import com.nhoryzon.mc.farmersdelight.item.MilkBottleItem;
import com.nhoryzon.mc.farmersdelight.item.ModBlockItem;
import com.nhoryzon.mc.farmersdelight.item.ModItemSettings;
import com.nhoryzon.mc.farmersdelight.item.Foods;
import com.nhoryzon.mc.farmersdelight.item.MushroomColonyBlockItem;
import com.nhoryzon.mc.farmersdelight.item.RopeItem;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public enum ItemsRegistry {
    /* Block Items */

    COOKING_POT("cooking_pot", () -> new ModBlockItem(BlocksRegistry.COOKING_POT.get()));

    /* Items */


    private final String pathName;
    private final Supplier<Item> itemSupplier;
    private final Integer burnTime;
    private Item item;

    ItemsRegistry(String pathName, Supplier<Item> itemSupplier) {
        this(pathName, itemSupplier, null);
    }

    ItemsRegistry(String pathName, Supplier<Item> itemSupplier, Integer burnTime) {
        this.pathName = pathName;
        this.itemSupplier = itemSupplier;
        this.burnTime = burnTime;
    }

    public static void registerAll() {
        for (ItemsRegistry value : values()) {
            Registry.register(Registry.ITEM, new Identifier(FarmersDelightMod.MOD_ID, value.pathName), value.get());
            if (value.burnTime != null) {
                FuelRegistry.INSTANCE.add(value.get(), value.burnTime);
            }
        }
    }

    public Item get() {
        if (item == null) {
            item = itemSupplier.get();
        }
        return item;
    }
}