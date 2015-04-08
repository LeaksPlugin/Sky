package com.talesdev.copsandcrims.weapon.module;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.core.entity.MetaData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Module for controlling item in inventory
 *
 * @author MoKunz
 */
public class ItemControlModule extends WeaponModule {
    public ItemControlModule() {
        super("ItemControl");
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        if (getWeapon().isWeapon(event.getCurrentItem()) ||
                getWeapon().isWeapon(event.getCursor()) ||
                getWeapon().isWeapon(event.getInventory().getItem(event.getHotbarButton())) ||
                event.getClick().equals(ClickType.NUMBER_KEY)
                ) {
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
            CvCPlayer player = getPlugin().getServerCvCPlayer().getPlayer(event.getPlayer());
            if (player.getWeapon(getWeapon().getClass()).getType() != Material.AIR) {
                event.setCancelled(true);
                return;
            }
            int bulletAmount = 0;
            if (event.getItem().hasMetadata("bulletAmount")) {
                MetaData metaData = new MetaData(event.getItem(), getPlugin());
                bulletAmount = (int) metaData.getMetadata("bulletAmount");
            } else {
                bulletAmount = player.getPlayerBullet().getBullet(getWeapon().getName()).getMaxBullet();
            }
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

    @EventHandler
    public void onItemMove(InventoryMoveItemEvent event) {
        if (getWeapon().isWeapon(event.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        for (Iterator<ItemStack> it = event.getDrops().iterator(); it.hasNext(); ) {
            ItemStack itemStack = it.next();
            if (!getPlugin().getWeaponFactory().isWeapon(itemStack)) {
                it.remove();
            } else {
                itemStack.setAmount(1);
                List<String> lores = new ArrayList<>();
                lores.add(event.getEntity().getName());
                itemStack.getItemMeta().setLore(lores);
            }
        }
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        ItemStack itemStack = event.getEntity().getItemStack();
        if (itemStack.hasItemMeta() && getPlugin().getWeaponFactory().isWeapon(itemStack)) {
            String player = itemStack.getItemMeta().getLore().get(0);
            CvCPlayer cvCPlayer = getPlugin().getServerCvCPlayer().getPlayer(Bukkit.getPlayer(player));
            MetaData metaData = new MetaData(event.getEntity(), getPlugin());
            metaData.setMetadata("bulletAmount", cvCPlayer.getPlayerBullet().getBullet(getWeapon().getName()).getBulletCount());
        }
    }
}
