package com.talesdev.core.player.data;

import com.talesdev.core.config.Savable;
import com.talesdev.core.player.CorePlayer;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

/**
 * Equipment cache
 *
 * @author MoKunz
 */
public class EquipmentCache implements Savable {
    private CorePlayer corePlayer;
    private ItemStack helmet, chestplate, legging, boots;
    private long lastAccessTime;

    public EquipmentCache(CorePlayer corePlayer) {
        this.corePlayer = corePlayer;
        this.corePlayer.autoSave(this);
        EntityEquipment entityEquipment = corePlayer.getPlayer().getEquipment();
        setHelmet(entityEquipment.getHelmet());
        setChestplate(entityEquipment.getChestplate());
        setLegging(entityEquipment.getLeggings());
        setBoots(entityEquipment.getBoots());
        lastAccessTime = System.currentTimeMillis();
    }

    private void access() {
        lastAccessTime = System.currentTimeMillis();
    }

    public CorePlayer getCorePlayer() {
        return corePlayer;
    }

    public long lastAccessTime() {
        return lastAccessTime;
    }

    public ItemStack getHelmet() {
        access();
        return helmet;
    }

    public void setHelmet(ItemStack helmet) {
        access();
        this.helmet = helmet;
    }

    public ItemStack getChestplate() {
        access();
        return chestplate;
    }

    public void setChestplate(ItemStack chestplate) {
        access();
        this.chestplate = chestplate;
    }

    public ItemStack getLegging() {
        access();
        return legging;
    }

    public void setLegging(ItemStack legging) {
        access();
        this.legging = legging;
    }

    public ItemStack getBoots() {
        access();
        return boots;
    }

    public void setBoots(ItemStack boots) {
        access();
        this.boots = boots;
    }

    private ItemStack air() {
        return new ItemStack(Material.AIR);
    }

    private String sectionOf(String itemName) {
        return getName() + "." + itemName;
    }

    @Override
    public void loadFrom(FileConfiguration configuration) {
        setHelmet(configuration.getItemStack(sectionOf("helmet"), air()));
        setChestplate(configuration.getItemStack(sectionOf("chestplate"), air()));
        setLegging(configuration.getItemStack(sectionOf("legging"), air()));
        setBoots(configuration.getItemStack(sectionOf("boots"), air()));
    }

    @Override
    public void saveTo(FileConfiguration configuration) {
        configuration.set(sectionOf("helmet"), getHelmet());
        configuration.set(sectionOf("chestplate"), getChestplate());
        configuration.set(sectionOf("legging"), getLegging());
        configuration.set(sectionOf("boots"), getBoots());
    }

    @Override
    public String getName() {
        return "EquipmentCache";
    }
}
