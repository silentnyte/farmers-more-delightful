package com.nytz.mc.farmersmoredelightful.fabric;

import com.nytz.mc.farmersmoredelightful.FarmersMoreDelightful;
import com.nytz.mc.farmersmoredelightful.fabric.registry.BlockEntityTypesRegistry;
import com.nytz.mc.farmersmoredelightful.fabric.registry.BlocksRegistry;
import com.nytz.mc.farmersmoredelightful.fabric.registry.ExtendedScreenTypesRegistry;
import com.nytz.mc.farmersmoredelightful.fabric.registry.ItemsRegistry;
import com.nytz.mc.farmersmoredelightful.fabric.registry.RecipeTypesRegistry;
import com.nytz.mc.farmersmoredelightful.fabric.registry.SoundsRegistry;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Set;

public class FarmersMoreDelightfulFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FarmersMoreDelightful.init();
        BlocksRegistry.registerAll();
        ItemsRegistry.registerAll();
        //EffectsRegistry.registerAll();
        BlockEntityTypesRegistry.registerAll();
        SoundsRegistry.registerAll();
        //AdvancementsRegistry.registerAll();
        RecipeTypesRegistry.registerAll();
        //LootFunctionsRegistry.registerAll();
        ExtendedScreenTypesRegistry.registerAll();
        //FRYING_PAN("frying_pan", FryingPanScreenHandler.class, FryingPanScreenHandler::new);
        //BOX_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(BOX, BoxScreenHandler::new);
        //ParticleTypesRegistry.registerAll();
        //EnchantmentsRegistry.registerAll();
        //ConfiguredFeaturesRegistry.registerAll();

        //registerEventListeners();
        registerLootTable();
        //registerDispenserBehavior();

    }

    protected void registerLootTable() {
        Set<Identifier> villageHouseChestsId = Set.of(
                LootTables.VILLAGE_PLAINS_CHEST,
                LootTables.VILLAGE_SAVANNA_HOUSE_CHEST,
                LootTables.VILLAGE_SNOWY_HOUSE_CHEST,
                LootTables.VILLAGE_TAIGA_HOUSE_CHEST,
                LootTables.VILLAGE_DESERT_HOUSE_CHEST);
        Set<Identifier> scavengingEntityIdList = Set.of(
                EntityType.PIG.getLootTableId(),
                EntityType.HOGLIN.getLootTableId(),
                EntityType.CHICKEN.getLootTableId(),
                EntityType.COW.getLootTableId(),
                EntityType.DONKEY.getLootTableId(),
                EntityType.HORSE.getLootTableId(),
                EntityType.LLAMA.getLootTableId(),
                EntityType.MULE.getLootTableId(),
                EntityType.RABBIT.getLootTableId(),
                EntityType.SHULKER.getLootTableId(),
                EntityType.SPIDER.getLootTableId());
        Set<Identifier> addItemLootBlockIdList = Set.of(
                Blocks.GRASS.getLootTableId(),
                Blocks.TALL_GRASS.getLootTableId(),
                Blocks.WHEAT.getLootTableId());

        LootTableLoadingCallback.EVENT.register((resourceManager, manager, id, supplier, setter) -> {
            Identifier injectId = new Identifier(FarmersMoreDelightful.MOD_ID, "inject/" + id.getPath());
            if (scavengingEntityIdList.contains(id)) {
                supplier.withPool(LootPool.builder().with(LootTableEntry.builder(injectId)).build());
            }

            if (addItemLootBlockIdList.contains(id)) {
                supplier.withPool(LootPool.builder().with(LootTableEntry.builder(injectId)).build());
            }

            if (villageHouseChestsId.contains(id)) {
                supplier.withPool(LootPool.builder().with(LootTableEntry.builder(injectId).weight(1).quality(0)).build());
            }

            if (LootTables.SHIPWRECK_SUPPLY_CHEST.equals(id)) {
                supplier.withPool(LootPool.builder().with(LootTableEntry.builder(injectId).weight(1).quality(0)).build());
            }
        });
    }

}
