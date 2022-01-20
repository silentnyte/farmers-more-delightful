package com.nytz.mc.farmersmoredelightful.fabric.registry;

import com.nytz.mc.farmersmoredelightful.fabric.entity.block.screen.FryingPanScreenHandler;

import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.impl.screenhandler.ExtendedScreenHandlerType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public enum ExtendedScreenTypesRegistry {
    FRYING_PAN("frying_pan", FryingPanScreenHandler.class, FryingPanScreenHandler::new);

    private final String pathName;
    private final Class<? extends ScreenHandler> screenHandlerClass;
    private final ScreenHandlerRegistry.ExtendedClientHandlerFactory<? extends ScreenHandler> screenHandlerFactory;
    private ScreenHandlerType<? extends ScreenHandler> screenHandlerType;

    ExtendedScreenTypesRegistry(String pathName, Class<? extends ScreenHandler> screenHandlerClass,
            ScreenHandlerRegistry.ExtendedClientHandlerFactory<? extends ScreenHandler> screenHandlerFactory) {
        this.pathName = pathName;
        this.screenHandlerClass = screenHandlerClass;
        this.screenHandlerFactory = screenHandlerFactory;
    }

    public static void registerAll() {
        for (ExtendedScreenTypesRegistry value : values()) {
            Registry.register(Registry.SCREEN_HANDLER, new Identifier(FarmersDelightMod.MOD_ID, value.pathName), value.get());
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends ScreenHandler> ScreenHandlerType<T> get() {
        return (ScreenHandlerType<T>) get(screenHandlerClass);
    }

    @SuppressWarnings({"unchecked","unused"})
    private <T extends ScreenHandler> ScreenHandlerType<T> get(Class<T> clazz) {
        if (screenHandlerType == null) {
            screenHandlerType = new ExtendedScreenHandlerType<>((ScreenHandlerRegistry.ExtendedClientHandlerFactory<T>) screenHandlerFactory);
        }

        return (ScreenHandlerType<T>) screenHandlerType;
    }
}