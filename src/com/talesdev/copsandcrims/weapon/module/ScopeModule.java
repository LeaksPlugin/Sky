package com.talesdev.copsandcrims.weapon.module;

import com.talesdev.copsandcrims.event.WeaponScopeEvent;
import com.talesdev.copsandcrims.player.CvCPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Iterator;

/**
 * Scope Module
 *
 * @author MoKunz
 */
public class ScopeModule extends WeaponModule {

    private int zoomLevel = 6;
    public ScopeModule() {
        super("Scope");
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        if (getWeapon().isWeapon(event.getPlayer().getItemInHand())) {
            getPlugin().getServer().getScheduler().runTaskLater(getPlugin(), new Runnable() {
                @Override
                public void run() {
                    Player player = event.getPlayer();
                    CvCPlayer cPlayer = getPlugin().getServerCvCPlayer().getPlayer(player);
                    boolean isSneaking = player.isSneaking();
                    if (isSneaking) {
                        zoom(cPlayer);
                    } else {
                        cancelZoom(cPlayer);
                    }
                }
            }, 1);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
            if (event.getCurrentItem().getType().equals(Material.PUMPKIN) || event.getCursor().getType().equals(Material.PUMPKIN)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSlotChange(PlayerItemHeldEvent event) {
        CvCPlayer cPlayer = getPlugin().getServerCvCPlayer().getPlayer(event.getPlayer());
        if (!getWeapon().isWeapon(event.getPlayer().getInventory().getItem(event.getNewSlot()))) {
            cancelZoom(cPlayer);
        } else if (getWeapon().isWeapon(event.getPlayer().getInventory().getItem(event.getNewSlot()))) {
            if (cPlayer.getPlayer().isSneaking()) {
                zoom(cPlayer);
            }
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        CvCPlayer cPlayer = getPlugin().getServerCvCPlayer().getPlayer(event.getPlayer());
        if (getWeapon().isWeapon(event.getItemDrop().getItemStack())) {
            cancelZoom(cPlayer);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        CvCPlayer cPlayer = getPlugin().getServerCvCPlayer().getPlayer(event.getEntity());
        cancelZoom(cPlayer);
        for (Iterator<ItemStack> it = event.getDrops().iterator(); it.hasNext(); ) {
            ItemStack itemStack = it.next();
            if (itemStack != null) {
                if (itemStack.getType().equals(Material.PUMPKIN)) {
                    it.remove();
                }
            }
        }
    }

    public void zoom(CvCPlayer cPlayer) {
        WeaponScopeEvent event = new WeaponScopeEvent(cPlayer.getPlayer(), weapon, true);
        getPlugin().getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        cPlayer.setScoping(true);
        cPlayer.setLastHelmet(cPlayer.getPlayer().getEquipment().getHelmet());
        cPlayer.getPlayer().getEquipment().setHelmet(new ItemStack(Material.PUMPKIN));
        cPlayer.getPlayer().updateInventory();
        if (getWeapon().getModule(ShootingModule.class).getFiringMode().equals(FiringMode.BOLT)) {
            cPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
        }
        cPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, zoomLevel, true, false));
    }

    public void cancelZoom(CvCPlayer cPlayer) {
        WeaponScopeEvent event = new WeaponScopeEvent(cPlayer.getPlayer(), weapon, false);
        getPlugin().getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        cPlayer.setScoping(false);
        cPlayer.getPlayer().getEquipment().setHelmet(cPlayer.getLastHelmet());
        cPlayer.getPlayer().updateInventory();
        cPlayer.getPlayer().removePotionEffect(PotionEffectType.SLOW);
    }

    public int getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(int zoomLevel) {
        this.zoomLevel = zoomLevel;
    }
}
