package com.nytz.mc.farmersmoredelightful.fabric.entity.block.screen;

import com.nytz.mc.farmersmoredelightful.FarmersMoreDelightful;
import com.nytz.mc.farmersmoredelightful.fabric.entity.block.FryingPanBlockEntity;
import com.nytz.mc.farmersmoredelightful.fabric.entity.block.inventory.FryingPanMealSlot;
import com.nytz.mc.farmersmoredelightful.fabric.entity.block.inventory.FryingPanResultSlot;
import com.nytz.mc.farmersmoredelightful.fabric.registry.BlocksRegistry;
import com.nytz.mc.farmersmoredelightful.fabric.registry.ExtendedScreenTypesRegistry;

import com.mojang.datafixers.util.Pair;
import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import com.nhoryzon.mc.farmersdelight.item.inventory.ItemStackHandler;
import com.nhoryzon.mc.farmersdelight.item.inventory.SlotItemHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class FryingPanScreenHandler extends ScreenHandler {

    public static final Identifier EMPTY_CONTAINER_SLOT_BOWL = new Identifier(FarmersDelightMod.MOD_ID, "item/empty_container_slot_bowl");

    private static final int INV_INDEX_MEAL_DISPLAY = 6;
    private static final int INV_INDEX_CONTAINER_INPUT = INV_INDEX_MEAL_DISPLAY + 1;
    private static final int INV_INDEX_OUTPUT = INV_INDEX_CONTAINER_INPUT + 1;
    private static final int INV_INDEX_START_PLAYER_INV = INV_INDEX_OUTPUT + 1;
    private static final int INV_INDEX_END_PLAYER_INV = INV_INDEX_START_PLAYER_INV + 36;

    public final FryingPanBlockEntity tileEntity;
    public final ItemStackHandler inventoryHandler;
    private final PropertyDelegate fryingPanData;
    private final ScreenHandlerContext canInteractWithCallable;

    public FryingPanScreenHandler(final int windowId, final PlayerInventory playerInventory, final FryingPanBlockEntity blockEntity, PropertyDelegate fryingPanDataIn) {
        super(ExtendedScreenTypesRegistry.FRYING_PAN.get(), windowId);
        this.tileEntity = blockEntity;
        this.inventoryHandler = blockEntity.getInventory();
        this.fryingPanData = fryingPanDataIn;
        this.canInteractWithCallable = ScreenHandlerContext.create(blockEntity.getWorld(), blockEntity.getPos());

        // Ingredient Slots - 2 Rows x 3 Columns
        int startX = 8;
        int startY = 18;
        int inputStartX = 30;
        int inputStartY = 17;
        int borderSlotSize = 18;
        for (int row = 0; row < 2; ++row) {
            for (int column = 0; column < 3; ++column) {
                addSlot(new SlotItemHandler(inventoryHandler, (row * 3) + column,
                        inputStartX + (column * borderSlotSize),
                        inputStartY + (row * borderSlotSize)));
            }
        }

        // Meal Display
        addSlot(new FryingPanMealSlot(inventoryHandler, 6, 124, 26));

        // Bowl Input
        addSlot(new SlotItemHandler(inventoryHandler, 7, 92, 55) {
            @Environment(value= EnvType.CLIENT)
            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, EMPTY_CONTAINER_SLOT_BOWL);
            }
        });

        // Bowl Output
        addSlot(new FryingPanResultSlot(inventoryHandler, 8, 124, 55));

        // Main Player Inventory
        int startPlayerInvY = startY * 4 + 12;
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * borderSlotSize),
                        startPlayerInvY + (row * borderSlotSize)));
            }
        }

        // Hot-bar
        for (int column = 0; column < 9; ++column) {
            addSlot(new Slot(playerInventory, column, startX + (column * borderSlotSize), 142));
        }

        addProperties(fryingPanDataIn);
    }

    public FryingPanScreenHandler(final int windowId, final PlayerInventory playerInventory, final PacketByteBuf data) {
        this(windowId, playerInventory, getBlockEntity(playerInventory, data), new ArrayPropertyDelegate(4));
    }

    private static FryingPanBlockEntity getBlockEntity(final PlayerInventory playerInventory, final PacketByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInventory.player.world.getBlockEntity(data.readBlockPos());

        if (tileAtPos instanceof FryingPanBlockEntity fryingPanBlockEntity) {
            return fryingPanBlockEntity;
        }

        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    @Override
    public boolean canUse(PlayerEntity playerIn) {
        return canUse(canInteractWithCallable, playerIn, BlocksRegistry.FRYING_PAN.get());
    }

    @Override
    public ItemStack transferSlot(PlayerEntity playerIn, int index) {
        Slot slot = slots.get(index);

        if (!slot.hasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack slotItemStack = slot.getStack();
        ItemStack itemStack = slotItemStack.copy();
        if (index == INV_INDEX_OUTPUT) {
            if (!insertItem(slotItemStack, INV_INDEX_START_PLAYER_INV, INV_INDEX_END_PLAYER_INV, true)) {
                return ItemStack.EMPTY;
            }
        } else if (index > INV_INDEX_OUTPUT) {
            if ((slotItemStack.getItem() == Items.BOWL && !insertItem(slotItemStack, INV_INDEX_CONTAINER_INPUT, INV_INDEX_OUTPUT, false))
                    || !insertItem(slotItemStack, 0, INV_INDEX_MEAL_DISPLAY, false)
                    || !insertItem(slotItemStack, INV_INDEX_CONTAINER_INPUT, INV_INDEX_OUTPUT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (!insertItem(slotItemStack, INV_INDEX_START_PLAYER_INV, INV_INDEX_END_PLAYER_INV, false)) {
            return ItemStack.EMPTY;
        }

        if (slotItemStack.isEmpty()) {
            slot.setStack(ItemStack.EMPTY);
        } else {
            slot.markDirty();
        }

        if (slotItemStack.getCount() == itemStack.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTakeItem(playerIn, slotItemStack);

        return itemStack;
    }

    @Environment(value= EnvType.CLIENT)
    public int getCookProgressionScaled() {
        int i = fryingPanData.get(0);
        int j = fryingPanData.get(1);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    @Environment(value= EnvType.CLIENT)
    public boolean isHeated() {
        return tileEntity.isAboveLitHeatSource();
    }

}