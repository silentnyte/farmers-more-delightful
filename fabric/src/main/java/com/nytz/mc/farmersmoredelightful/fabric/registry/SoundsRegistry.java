package com.nytz.mc.farmersmoredelightful.fabric.registry;

import com.nytz.mc.farmersmoredelightful.FarmersMoreDelightful;

import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public enum SoundsRegistry {
    BLOCK_FRYING_PAN_BOIL("block.frying_pan.boil");

    private final String pathName;
    private final SoundEvent soundEvent;

    SoundsRegistry(String pathName) {
        this.pathName = pathName;
        this.soundEvent = new SoundEvent(new Identifier(FarmersMoreDelightful.MOD_ID, this.pathName));
    }

    public static void registerAll() {
        for (SoundsRegistry value : values()) {
            Registry.register(Registry.SOUND_EVENT, new Identifier(FarmersMoreDelightful.MOD_ID, value.pathName), value.soundEvent);
        }
    }

    public SoundEvent get() {
        return soundEvent;
    }
}