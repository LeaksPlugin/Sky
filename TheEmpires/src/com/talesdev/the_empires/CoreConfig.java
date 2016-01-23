package com.talesdev.the_empires;

import com.talesdev.core.config.ConfigFile;
import com.talesdev.core.world.LocationString;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * @author MoKunz
 */
public final class CoreConfig extends ConfigFile {
    private final TheEmpires plugin;

    public CoreConfig(TheEmpires plugin) {
        super(plugin.getDataFolder().getPath() + "/config.yml");
        this.plugin = plugin;
    }

    @Override
    public void load() {
        super.load();
    }

    @Override
    public void save() {
        super.save();
    }

    public Location getTeamSpawn(String teamName) {
        String locStr = getConfig().getString("team." + teamName + ".spawn");
        return LocationString.fromString(locStr).getLocation();
    }

    public Vector getMinBond(String teamName) {
        return getConfig().getVector("team." + teamName + ".min");
    }

    public Vector getMaxBond(String teamName) {
        return getConfig().getVector("team." + teamName + ".max");
    }

    public String getMapName() {
        return getConfig().getString("map-name", "");
    }

    public String getMapAuthor() {
        return getConfig().getString("map-author", "");
    }

    public int getMaxPlayers() {
        return getConfig().getInt("max-players", 24);
    }
}
