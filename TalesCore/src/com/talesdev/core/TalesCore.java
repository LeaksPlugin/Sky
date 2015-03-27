package com.talesdev.core;

import com.talesdev.core.system.PluginUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * TalesCore
 * @author MoKunz
 */
public class TalesCore extends JavaPlugin {
    @Override
    public void onEnable(){
        getLogger().info("TalesCore has been enabled!");

    }
    @Override
    public void onDisable(){
        getLogger().info("TalesCore has been disabled!");
    }
    public static TalesCore getPlugin(){
        return getPlugin(TalesCore.class);
    }
}
