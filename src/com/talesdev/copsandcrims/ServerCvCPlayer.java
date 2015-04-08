package com.talesdev.copsandcrims;

import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.copsandcrims.weapon.WeaponBullet;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Represent all CvCPlayer in current server
 *
 * @author MoKunz
 */
public class ServerCvCPlayer {
    private File configFile;
    private FileConfiguration configuration;
    private List<CvCPlayer> playerList = new ArrayList<>();

    public ServerCvCPlayer() {
        this.configFile = new File("plugins/CopsAndCrims/userdata.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Error while creating config file!");
                e.printStackTrace();
            }
        }
        this.configuration = new YamlConfiguration();
        try {
            configuration.load(configFile);
        } catch (IOException e) {
            System.out.println("Error : IOException while loading file");
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            System.out.println("Error : Invalid config file");
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return configuration;
    }

    public void addNewPlayer(CvCPlayer cvcPlayer) {
        if (!getPlayerList().contains(cvcPlayer)) {
            getPlayerList().add(cvcPlayer);
        }
    }

    public void removePlayer(CvCPlayer player) {
        getPlayerList().remove(player);
    }

    public boolean contains(Player player) {
        return getPlayer(player) != null;
    }

    public CvCPlayer getPlayer(Player player) {
        for (CvCPlayer cvCPlayer : getPlayerList()) {
            if (cvCPlayer.getPlayerName().equalsIgnoreCase(player.getName())) {
                return cvCPlayer;
            }
        }
        return null;
    }

    private List<CvCPlayer> getPlayerList() {
        return playerList;
    }

    public Set<CvCPlayer> getAllPlayers() {
        return new HashSet<>(getPlayerList());
    }

    public CvCPlayer createPlayer(Player player) {
        return new CvCPlayer(player);
    }

    public CvCPlayer loadUserData(Player player) {
        CvCPlayer cvCPlayer = createPlayer(player);
        // all weapons
        List<String> weapons = CopsAndCrims.getPlugin().getWeaponFactory().getAllWeaponName();
        // bullet
        for (String weaponName : weapons) {
            String bulletCountPath = player.getName() + "." + weaponName + "." + "bullet";
            int bulletCount = 0;
            if (getConfig().isSet(bulletCountPath)) {
                bulletCount = getConfig().getInt(bulletCountPath);
            }
            cvCPlayer.getPlayerBullet().getBullet(weaponName).setBulletCount(bulletCount);
        }
        return cvCPlayer;
    }

    public void saveUserData(CvCPlayer player) {
        Map<String, WeaponBullet> bulletMap = player.getPlayerBullet().getBulletMap();
        for (Map.Entry<String, WeaponBullet> bullet : bulletMap.entrySet()) {
            String bulletCountPath = player.getPlayerName() + "." + bullet.getKey() + "." + "bullet";
            getConfig().set(bulletCountPath, bullet.getValue().getBulletCount());
        }
        try {
            getConfig().save(configFile);
        } catch (IOException e) {
            System.out.println("Error while saving user data!");
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            getConfig().save(configFile);
        } catch (IOException e) {
            System.out.println("Error while saving user data!");
            e.printStackTrace();
        }
    }
}
