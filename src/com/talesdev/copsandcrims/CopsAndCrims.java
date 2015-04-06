package com.talesdev.copsandcrims;

import com.comphenix.protocol.ProtocolLibrary;
import com.talesdev.copsandcrims.arena.DefaultArena;
import com.talesdev.copsandcrims.guns.DesertEagle;
import com.talesdev.copsandcrims.player.PlayerRecoilTask;
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
        // protocol
        ProtocolLibrary.getProtocolManager().addPacketListener(new MyPacketAdapter(this));
        // task
        recoilTask = getServer().getScheduler().runTaskTimerAsynchronously(this, new PlayerRecoilTask(this), 0, 1);
    }

    @Override
    public void onDisable() {
        recoilTask.cancel();
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
