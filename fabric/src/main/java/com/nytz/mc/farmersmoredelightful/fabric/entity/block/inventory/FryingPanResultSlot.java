package com.nytz.mc.farmersmoredelightful.fabric.entity.block.inventory;

import com.nhoryzon.mc.farmersdelight.item.inventory.ItemHandler;
import com.nhoryzon.mc.farmersdelight.item.inventory.SlotItemHandler;
import net.minecraft.item.ItemStack;

public class FryingPanResultSlot extends SlotItemHandler {
    public FryingPanResultSlot(ItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }
}