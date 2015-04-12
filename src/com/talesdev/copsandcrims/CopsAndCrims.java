package com.talesdev.copsandcrims;

import com.comphenix.protocol.ProtocolLibrary;
import com.talesdev.copsandcrims.arena.DefaultArena;
import com.talesdev.copsandcrims.guns.*;
import com.talesdev.copsandcrims.player.PlayerBulletTask;
import com.talesdev.copsandcrims.player.PlayerEquipmentListener;
import com.talesdev.copsandcrims.weapon.WeaponFactory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

/**
 * Plugin main class
 * @author MoKunz
 */
public class CopsAndCrims extends JavaPlugin {
    private boolean debug = true;
    private BukkitTask recoilTask;
    private BukkitTask scopeTask;
    private WeaponFactory weaponFactory;
    private ServerCvCPlayer serverCvCPlayer;
    @Override
    public void onEnable() {
        weaponFactory = new WeaponFactory(this);
        serverCvCPlayer = new ServerCvCPlayer();
        // arena
        System.out.println(DefaultArena.getInstance().getArenaName() + " starting...");
        // save config
        saveDefaultConfig();
        // listener
        getServer().getPluginManager().registerEvents(new CopsAndCrimsListener(this), this);
        // command
        getCommand("cvc").setExecutor(new CopsAndCrimsCommand());
        // item
        getWeaponFactory().addWeapon(new DesertEagle());
        getWeaponFactory().addWeapon(new Knife());
        getWeaponFactory().addWeapon(new Glock18());
        getWeaponFactory().addWeapon(new HK45());
        getWeaponFactory().addWeapon(new M1911());
        // protocol
        ProtocolLibrary.getProtocolManager().addPacketListener(new MyPacketAdapter(this));
        ProtocolLibrary.getProtocolManager().addPacketListener(new PlayerEquipmentListener(this));
        // task
        recoilTask = getServer().getScheduler().runTaskTimerAsynchronously(this, new PlayerBulletTask(this), 0, 1);
        //scopeTask = getServer().getScheduler().runTaskTimer(this, new PlayerScopeTask(this), 0, 10);
    }

    @Override
    public void onDisable() {
        recoilTask.cancel();
        //scopeTask.cancel();
        getServerCvCPlayer().getAllPlayers().forEach(getServerCvCPlayer()::saveUserData);
        saveConfig();
    }
    public static CopsAndCrims getPlugin(){
        return CopsAndCrims.getPlugin(CopsAndCrims.class);
    }

    public WeaponFactory getWeaponFactory() {
        return weaponFactory;
    }

    public ServerCvCPlayer getServerCvCPlayer() {
        return serverCvCPlayer;
    }
}
