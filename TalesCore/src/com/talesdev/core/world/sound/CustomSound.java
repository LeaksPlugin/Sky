package com.talesdev.core.world.sound;

/**
 * Represent a custom sound from resource pack
 *
 * @author MoKunz
 */
public class CustomSound implements SoundEffectInterface {

    private String sound;

    public CustomSound(String sound) {
        this.sound = sound;
    }

    @Override
    public String asString() {
        return sound.toLowerCase();
    }
}
