package com.talesdev.core.world;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.injector.PacketConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * Sound info
 * @author MoKunz
 */
public class Sound {
    private SoundEffectInterface effect;
    private float volume;
    private float pitch;

    public Sound(SoundEffectInterface soundEffect, float volume, float pitch) {
        this.effect = soundEffect;
        this.volume = volume;
        this.pitch = pitch;
    }

    public SoundEffectInterface getSoundEffect() {
        return effect;
    }

    public Sound setSoundEffect(SoundEffect effect) {
        this.effect = effect;
        return this;
    }

    public float getVolume() {
        return volume;
    }

    public Sound setVolume(float volume) {
        this.volume = volume;
        return this;
    }

    public float getPitch() {
        return pitch;
    }

    public Sound setPitch(float pitch){
        this.pitch = pitch;
        return this;
    }

    public void playSound(Player target, Location location) {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        double x = location.getBlockX() + 0.5D;
        double y = location.getBlockY() + 0.5D;
        double z = location.getBlockZ() + 0.5D;

        PacketConstructor soundPacket;
        // This allows us to call one of the constructors of Packet62NamedSoundEffect
        soundPacket = manager.createPacketConstructor(
                PacketType.Play.Server.NAMED_SOUND_EFFECT, effect.toString(), x, y, z, volume, pitch);

        try {
            manager.sendServerPacket(target, soundPacket.createPacket(effect.toString(), x, y, z, volume, pitch));
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Cannot invoke PacketNamedSoundEffect constructor.", e);
        }
    }

    public void playSound(Location location) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            playSound(p, location);
        }
    }

}
