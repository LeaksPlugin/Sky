package com.talesdev.copsandcrims.armor;

import org.bukkit.entity.Player;

/**
 * Armor Container
 *
 * @author MoKunz
 */
public class ArmorContainer {
    private Armor helmet;
    private Armor kevlar;
    private Player player;

    public ArmorContainer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Armor getKevlar() {
        return kevlar;
    }

    public void setKevlar(Armor kevlar) {
        this.kevlar = kevlar;
    }

    public Armor getHelmet() {
        return helmet;
    }

    public void setHelmet(Armor helmet) {
        this.helmet = helmet;
    }

    public boolean hasHelmet() {
        return helmet != null;
    }

    public boolean hasKevlar() {
        return kevlar != null;
    }

    public void clearAll() {
        kevlar = null;
        helmet = null;
    }

    public void update() {
        player.getEquipment().setHelmet(helmet.asItem());
        player.getEquipment().setChestplate(kevlar.asItem());
    }

    public String toString() {
        return (hasHelmet() ? "\u927f" : "") + (hasKevlar() ? "\u927e" : "");
    }
}
