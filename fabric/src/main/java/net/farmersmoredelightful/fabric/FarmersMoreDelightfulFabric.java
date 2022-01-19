package net.farmersmoredelightful.fabric;

import net.farmersmoredelightful.FarmersMoreDelightful;
import net.fabricmc.api.ModInitializer;

public class FarmersMoreDelightfulFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FarmersMoreDelightful.init();
    }
}
