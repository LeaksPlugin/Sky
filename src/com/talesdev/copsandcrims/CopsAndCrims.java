package com.talesdev.copsandcrims;

import com.talesdev.copsandcrims.arena.DefaultArena;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Plugin main class
 * @author MoKunz
 */
public class CopsAndCrims extends JavaPlugin {
    private boolean debug = true;
    @Override
    public void onEnable() {
        System.out.println(DefaultArena.getInstance().getArenaName() + " starting...");
        // save config
        saveDefaultConfig();
        // listener
        getServer().getPluginManager().registerEvents(new CopsAndCrimsListener(), this);
        // command
        getCommand("cvc").setExecutor(new CopsAndCrimsCommand());
    }

    @Override
    public void onDisable() {

    }
    public static CopsAndCrims getPlugin(){
        return CopsAndCrims.getPlugin(CopsAndCrims.class);
    }
}
