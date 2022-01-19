package com.nytz.mc.farmersmoredelightful.forge;

import dev.architectury.platform.forge.EventBuses;
import com.nytz.mc.farmersmoredelightful.FarmersMoreDelightful;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FarmersMoreDelightful.MOD_ID)
public class FarmersMoreDelightfulForge {
    public FarmersMoreDelightfulForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(FarmersMoreDelightful.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        FarmersMoreDelightful.init();
    }
}
