package com.nytz.mc.farmersmoredelightful.fabric;

import com.nytz.mc.farmersmoredelightful.FarmersMoreDelightful;
import net.fabricmc.api.ModInitializer;

public class FarmersMoreDelightfulFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FarmersMoreDelightful.init();
    }
}
