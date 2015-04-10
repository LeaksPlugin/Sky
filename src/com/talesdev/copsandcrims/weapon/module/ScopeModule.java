package com.talesdev.copsandcrims.weapon.module;

import com.talesdev.copsandcrims.player.CvCPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Scope Module
 *
 * @author MoKunz
 */
public class ScopeModule extends WeaponModule {

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
                        cPlayer.setScoping(true);
                        player.getEquipment().setHelmet(new ItemStack(Material.PUMPKIN));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 30, true, false));
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
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        CvCPlayer cPlayer = getPlugin().getServerCvCPlayer().getPlayer(event.getPlayer());
        if (getWeapon().isWeapon(event.getItemDrop().getItemStack())) {
            cancelZoom(cPlayer);
        }
    }

    private void cancelZoom(CvCPlayer cPlayer) {
        cPlayer.setScoping(false);
        cPlayer.getPlayer().getEquipment().setHelmet(new ItemStack(Material.AIR));
        cPlayer.getPlayer().removePotionEffect(PotionEffectType.SLOW);
    }

}
