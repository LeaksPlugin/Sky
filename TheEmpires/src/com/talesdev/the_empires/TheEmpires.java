package com.talesdev.the_empires;

import com.talesdev.the_empires.game.TheEmpiresGame;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author MoKunz
 */
public class TheEmpires extends JavaPlugin {
    private CoreConfig coreConfig;
    private TheEmpiresGame game;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        coreConfig = new CoreConfig(this);
        game = new TheEmpiresGame(this);
        coreConfig.load();
        getLogger().info("Plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        game.destroy();
        coreConfig.save();
        saveConfig();
        getLogger().info("Plugin has been disabled!");
    }

    public CoreConfig getCoreConfig() {
        return coreConfig;
    }

    public TheEmpiresGame getGame() {
        return game;
    }
}
