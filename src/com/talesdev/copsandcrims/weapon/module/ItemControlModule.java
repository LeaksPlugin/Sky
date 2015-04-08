package com.talesdev.copsandcrims.weapon.module;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.core.entity.MetaData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Module for controlling item in inventory
 *
 * @author MoKunz
 */
public class ItemControlModule extends WeaponModule {
    public ItemControlModule() {
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

    @EventHandler
    public void onSlotChange(PlayerItemHeldEvent event) {
        CopsAndCrims plugin = CopsAndCrims.getPlugin();
        CvCPlayer player = plugin.getServerCvCPlayer().getPlayer(event.getPlayer());
        Weapon weapon = plugin.getWeaponFactory().getWeapon(player.getPlayer().getInventory().getItem(event.getPreviousSlot()));
        if (weapon != null && player.getPlayerBullet().getBullet(weapon.getName()).isReloading()) {
            player.getPlayerBullet().getBullet(weapon.getName()).cancel();
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (getWeapon().isWeapon(player.getItemInHand())) {
                event.setCancelled(true);
            }
        }
    }
}
