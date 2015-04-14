package com.talesdev.copsandcrims;

import com.comphenix.protocol.ProtocolLibrary;
import com.talesdev.copsandcrims.arena.DefaultArena;
import com.talesdev.copsandcrims.arena.TDMArenaController;
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
    private ServerCvCArena serverCvCArena;
    @Override
    public void onEnable() {
        weaponFactory = new WeaponFactory(this);
        serverCvCPlayer = new ServerCvCPlayer(this);
        serverCvCArena = new ServerCvCArena(this);
        // arena
        System.out.println(DefaultArena.getInstance().getArenaName() + " starting...");
        // save config
        saveDefaultConfig();
        // listener
        getServer().getPluginManager().registerEvents(new CopsAndCrimsListener(this), this);
        // command
        getCommand("cvc").setExecutor(new CopsAndCrimsCommand());
        // item
        getWeaponFactory().addWeapon(new AK47());
        getWeaponFactory().addWeapon(new DesertEagle());
        getWeaponFactory().addWeapon(new Knife());
        getWeaponFactory().addWeapon(new Glock18());
        getWeaponFactory().addWeapon(new HK45());
        getWeaponFactory().addWeapon(new M4A1());
        getWeaponFactory().addWeapon(new P250());
        // arena controller
        getServerCvCArena().addController(new TDMArenaController());
        // protocol
        ProtocolLibrary.getProtocolManager().addPacketListener(new PlayerEquipmentListener(this));
        // task
        recoilTask = getServer().getScheduler().runTaskTimerAsynchronously(this, new PlayerBulletTask(this), 0, 1);
    }

    @Override
    public void onDisable() {
        // cancel task
        recoilTask.cancel();
        // shutdown system
        getServerCvCPlayer().shutdown();
        getServerCvCArena().shutDown();
        saveConfig();
        getLogger().info("Plugin has been disabled!");
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

    public ServerCvCArena getServerCvCArena() {
        return serverCvCArena;
    }
}
