package com.talesdev.core;

import com.talesdev.core.system.UnicodeCommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * TalesCore
 * @author MoKunz
 */
public class TalesCore extends JavaPlugin {
    @Override
    public void onEnable(){
        getLogger().info("TalesCore has been enabled!");
        getCommand("unicode").setExecutor(new UnicodeCommandExecutor());
    }
    @Override
    public void onDisable(){
        getLogger().info("TalesCore has been disabled!");
    }
    public static TalesCore getPlugin(){
        return getPlugin(TalesCore.class);
    }

}
