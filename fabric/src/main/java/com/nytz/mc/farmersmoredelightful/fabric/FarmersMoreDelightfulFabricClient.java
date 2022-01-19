package com.nytz.mc.farmersmoredelightful.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

import com.nhoryzon.mc.farmersdelight.registry.ExtendedScreenTypesRegistry;
import com.nhoryzon.mc.farmersdelight.client.screen.CookingPotScreen;

@Environment(value= EnvType.CLIENT)
public class FarmersMoreDelightfulFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		// Screen register
		ScreenRegistry.register(ExtendedScreenTypesRegistry.COOKING_POT.get(), CookingPotScreen::new);

	}
}