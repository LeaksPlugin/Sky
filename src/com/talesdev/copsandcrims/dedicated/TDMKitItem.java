package com.talesdev.copsandcrims.dedicated;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.armor.*;
import com.talesdev.copsandcrims.guns.Glock18;
import com.talesdev.copsandcrims.guns.Knife;
import com.talesdev.copsandcrims.guns.USP;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.copsandcrims.weapon.RandomWeapon;
import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.WeaponSlot;
import com.talesdev.copsandcrims.weapon.module.ShootingModule;
import com.talesdev.core.arena.GameState;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * TDM Kit Item
 *
 * @author MoKunz
 */
public class TDMKitItem {
    private Player player;
    private String playerTeam;
    private Map<String, Class<? extends Weapon>> weaponClassMap;
    private CopsAndCrims plugin = CopsAndCrims.getPlugin();

    public TDMKitItem(Player player, String playerTeam) {
        this.player = player;
        this.playerTeam = playerTeam;
        this.weaponClassMap = new HashMap<>();
        setSecondaryMap("Terrorist", Glock18.class);
        setSecondaryMap("CounterTerrorist", USP.class);
    }

    public void setSecondaryMap(String team, Class<? extends Weapon> weaponClass) {
        weaponClassMap.put(team, weaponClass);
    }

    public void give() {
        if (plugin.getTdmGameArena().getGameState().equals(GameState.STARTED)) {
            RandomWeapon randomWeapon = new RandomWeapon(getPlugin().getWeaponFactory());
            randomWeapon.filter(Knife.class, Glock18.class, USP.class);
            Weapon firstWeapon = randomWeapon.randomWeapon();
            int firstSlot = 0;
            Weapon secondWeapon = null;
            int secondSlot = 1;
            Weapon melee = getPlugin().getWeaponFactory().getWeapon(Knife.class);
            int meleeSlot = 2;
            if (WeaponSlot.getSlot(firstWeapon).equals(WeaponSlot.PRIMARY)) {
                Class<? extends Weapon> weaponClass = weaponClassMap.get(playerTeam);
                if (weaponClass != null) {
                    secondWeapon = getPlugin().getWeaponFactory().getWeapon(weaponClass);
                }
            } else {
                firstSlot = 1;
            }
            CvCPlayer cPlayer = getPlugin().getServerCvCPlayer().getPlayer(player);
            fillBullet(cPlayer, firstWeapon);
            player.getInventory().setItem(firstSlot, getPlugin().getWeaponFactory().createWeaponItem(firstWeapon.getClass()));
            if (secondWeapon != null) {
                player.getInventory().setItem(secondSlot, getPlugin().getWeaponFactory().createWeaponItem(secondWeapon.getClass()));
                fillBullet(cPlayer, secondWeapon);
            }
            player.getInventory().setItem(meleeSlot, getPlugin().getWeaponFactory().createWeaponItem(melee.getClass()));
            // armor
            Armor helmet = null, kevlar = null;
            if (playerTeam.equalsIgnoreCase("Terrorist")) {
                helmet = new TerroristHelmet();
                kevlar = new TerroristKevlar();
            } else {
                helmet = new CounterTerroristHelmet();
                kevlar = new CounterTerroristKevlar();
            }
            player.getInventory().setHelmet(helmet.asItem());
            player.getInventory().setChestplate(kevlar.asItem());
            cPlayer.getArmorContainer().setHelmet(helmet);
            cPlayer.getArmorContainer().setKevlar(kevlar);
            getPlugin().getServer().getScheduler().runTaskLater(getPlugin(), () -> {
                cPlayer.getArmorContainer().update();
            }, 20);
        }
    }

    public void fillBullet(CvCPlayer cPlayer, Weapon weapon) {
        if (weapon.containsModule(ShootingModule.class)) {
            cPlayer.getPlayerBullet().getBullet(weapon.getName()).setBulletCount(
                    weapon.getModule(ShootingModule.class).getMaxBullet()
            );
        }
    }

    public Player getPlayer() {
        return player;
    }

    public CopsAndCrims getPlugin() {
        return plugin;
    }
}
