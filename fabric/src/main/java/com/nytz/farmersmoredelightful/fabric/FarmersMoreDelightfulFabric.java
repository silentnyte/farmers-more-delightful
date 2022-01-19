package com.nytz.farmersmoredelightful.fabric;

import com.nytz.farmersmoredelightful.FarmersMoreDelightful;
import net.fabricmc.api.ModInitializer;

public class FarmersMoreDelightfulFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FarmersMoreDelightful.init();
    }
}
