package com.talesdev.copsandcrims.weapon.module;

import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.core.entity.MetaData;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Module for controlling item in inventory
 *
 * @author MoKunz
 */
public class InventoryControlModule extends WeaponModule {
    public InventoryControlModule() {
        super("InventoryControl");
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        if (getWeapon().isWeapon(event.getCurrentItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        if (getWeapon().isWeapon(event.getItemDrop().getItemStack())) {
            int bullet = 0;
            if (getWeapon().containsModule(ShootingModule.class)) {
                bullet = getWeapon().getModule(ShootingModule.class).getMaxBullet();
            }
            int slot = event.getPlayer().getInventory().getHeldItemSlot();
            MetaData metaData = new MetaData(event.getItemDrop(), getPlugin());
            metaData.setMetadata("bulletAmount", getPlugin().getServerCvCPlayer().getPlayer(event.getPlayer()).getPlayerBullet().getBullet(getWeapon().getName()).getBulletCount());
            event.getPlayer().getInventory().setItem(slot, new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onPickUpItem(PlayerPickupItemEvent event) {
        if (getWeapon().isWeapon(event.getItem().getItemStack())) {
            if (event.getItem().hasMetadata("bulletAmount")) {
                MetaData metaData = new MetaData(event.getItem(), getPlugin());
                int bulletAmount = (int) metaData.getMetadata("bulletAmount");
                CvCPlayer player = getPlugin().getServerCvCPlayer().getPlayer(event.getPlayer());
                player.getWeapon(getWeapon().getClass()).setAmount(bulletAmount);
                ItemStack itemStack = event.getItem().getItemStack();
                getPlugin().getServer().getScheduler().runTaskLater(getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        player.getWeapon(getWeapon().getClass()).setAmount(player.getPlayerBullet().getBullet(getWeapon().getName()).getBulletCount());
                    }
                }, 1);
            }
        }
    }
}
