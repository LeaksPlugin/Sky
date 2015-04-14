package com.talesdev.copsandcrims.weapon.module;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.event.PlayerDropWeaponEvent;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.core.entity.MetaData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
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
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Module for controlling item in inventory
 *
 * @author MoKunz
 */
public class ItemControlModule extends WeaponModule {
    private boolean clickActive = true;
    private boolean dropActive = true;
    private boolean pickupActive = true;
    private boolean slotChangeActive = false;
    private boolean damageActive = true;
    private boolean blockBreakActive = true;
    private boolean itemMoveActive = true;
    private boolean playerDeathActive = true;
    public ItemControlModule() {
        super("ItemControl");
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        if (!clickActive) return;
        if (getPlugin().getConfig().getBoolean("strict-inventory-check")) {
            if (event.getClick().equals(ClickType.NUMBER_KEY)) {
                event.setCancelled(true);
            }
        }
        if (getWeapon().isWeapon(event.getCurrentItem()) ||
                getWeapon().isWeapon(event.getCursor())
                ) {
            Bukkit.getPlayer(event.getWhoClicked().getName()).updateInventory();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        if (!dropActive) return;
        if (getWeapon().isWeapon(event.getItemDrop().getItemStack())) {
            // call event before
            PlayerDropWeaponEvent weaponEvent = new PlayerDropWeaponEvent(
                    event.getPlayer(), getWeapon(), event.getItemDrop().getItemStack()
            );
            getPlugin().getServer().getPluginManager().callEvent(weaponEvent);
            if (weaponEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }
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
            player.getPlayerBullet().getBullet(getWeapon().getName()).setBulletCount(bulletAmount);
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
        doUpdateInventory(player.getPlayer());
        Weapon weapon = plugin.getWeaponFactory().getWeapon(player.getPlayer().getInventory().getItem(event.getPreviousSlot()));
        if (weapon != null && player.getPlayerBullet().getBullet(weapon.getName()).isReloading()) {
            player.getPlayerBullet().getBullet(weapon.getName()).cancel();
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            doUpdateInventory(player);
            CvCPlayer cPlayer = getPlugin().getServerCvCPlayer().getPlayer(player);
            if (getWeapon().isWeapon(player.getItemInHand())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        doUpdateInventory(event.getPlayer());
    }

    @EventHandler
    public void onItemMove(InventoryMoveItemEvent event) {
        if (getWeapon().isWeapon(event.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        doUpdateInventory(event.getEntity());
        for (Iterator<ItemStack> it = event.getDrops().iterator(); it.hasNext(); ) {
            ItemStack itemStack = it.next();
            if (!getPlugin().getWeaponFactory().isWeapon(itemStack)) {
                it.remove();
            } else {
                itemStack.setAmount(1);
                List<String> lores = new ArrayList<>();
                lores.add(event.getEntity().getName());
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setLore(lores);
                itemStack.setItemMeta(itemMeta);
            }
        }
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        ItemStack itemStack = event.getEntity().getItemStack();
        if (itemStack.hasItemMeta() && getPlugin().getWeaponFactory().isWeapon(itemStack)) {
            Weapon weapon = getPlugin().getWeaponFactory().getWeapon(itemStack);
            // check if spawned item is correct weapon
            if (weapon.getName().equals(getWeapon().getName())) {
                if (itemStack.getItemMeta().hasLore()) {
                    String player = itemStack.getItemMeta().getLore().get(0);
                    CvCPlayer cvCPlayer = getPlugin().getServerCvCPlayer().getPlayer(Bukkit.getPlayer(player));
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setLore(new ArrayList<>());
                    itemStack.setItemMeta(itemMeta);
                    event.getEntity().setItemStack(itemStack);
                    MetaData metaData = new MetaData(event.getEntity(), getPlugin());
                    int bulletCount = cvCPlayer.getPlayerBullet().getBullet(getWeapon().getName()).getBulletCount();
                    System.out.println(bulletCount);
                    metaData.setMetadata("bulletAmount", bulletCount);
                }
            }
        }
    }

    public void doUpdateInventory(Player player) {
        getPlugin().getServer().getScheduler().runTaskLater(getPlugin(), new Runnable() {
            @Override
            public void run() {
                CvCPlayer cPlayer = CopsAndCrims.getPlugin().getServerCvCPlayer().getPlayer(player);
                ItemStack itemStack = cPlayer.getWeapon(getWeapon().getClass());
                itemStack.setAmount(itemStack.getAmount());
                itemStack.setDurability(itemStack.getDurability());
                player.updateInventory();
            }
        }, 1);
    }

    public boolean isClickActive() {
        return clickActive;
    }

    public void setClickActive(boolean clickActive) {
        this.clickActive = clickActive;
    }

    public boolean isDropActive() {
        return dropActive;
    }

    public void setDropActive(boolean dropActive) {
        this.dropActive = dropActive;
    }

    public boolean isPickupActive() {
        return pickupActive;
    }

    public void setPickupActive(boolean pickupActive) {
        this.pickupActive = pickupActive;
    }

    public boolean isSlotChangeActive() {
        return !slotChangeActive;
    }

    public void setSlotChangeActive(boolean slotChangeActive) {
        this.slotChangeActive = !slotChangeActive;
    }

    public boolean isDamageActive() {
        return damageActive;
    }

    public void setDamageActive(boolean damageActive) {
        this.damageActive = damageActive;
    }

    public boolean isBlockBreakActive() {
        return blockBreakActive;
    }

    public void setBlockBreakActive(boolean blockBreakActive) {
        this.blockBreakActive = blockBreakActive;
    }

    public boolean isItemMoveActive() {
        return itemMoveActive;
    }

    public void setItemMoveActive(boolean itemMoveActive) {
        this.itemMoveActive = itemMoveActive;
    }

    public boolean isPlayerDeathActive() {
        return playerDeathActive;
    }

    public void setPlayerDeathActive(boolean playerDeathActive) {
        this.playerDeathActive = playerDeathActive;
    }
}
