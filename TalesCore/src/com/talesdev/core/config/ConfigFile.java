package com.talesdev.core.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Config file
 *
 * @author MoKunz
 */
public class ConfigFile {

    private File configFile;
    private FileConfiguration configuration;

    public ConfigFile(String file) {
        this(new File(file));
    }

    public ConfigFile(File file) {
        this.configFile = file;
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Error while creating config file!");
                e.printStackTrace();
            }
        }
        this.configuration = new YamlConfiguration();
        load();
    }

    public FileConfiguration getConfig() {
        return configuration;
    }

    public void load() {
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

    public void save() {
        try {
            getConfig().save(configFile);
        } catch (IOException e) {
            System.out.println("Error while saving user data!");
            e.printStackTrace();
        }
    }
}
