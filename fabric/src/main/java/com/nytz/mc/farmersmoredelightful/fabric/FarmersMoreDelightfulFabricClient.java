package com.nytz.mc.farmersmoredelightful.fabric;

import com.nytz.mc.farmersmoredelightful.fabric.client.screen.FryingPanScreen;
import com.nytz.mc.farmersmoredelightful.fabric.registry.ExtendedScreenTypesRegistry;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(value= EnvType.CLIENT)
public class FarmersMoreDelightfulFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		// Screen register
		ScreenRegistry.register(ExtendedScreenTypesRegistry.FRYING_PAN.get(), FryingPanScreen::new);

	}
}