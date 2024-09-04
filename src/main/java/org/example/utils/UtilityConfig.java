package org.example.utils;

import com.google.common.base.Charsets;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.example.Main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class UtilityConfig {

    private String fileName;
    private File file;
    private FileConfiguration configuration;
    private Main main;
    public UtilityConfig(String fileName) {
        this.fileName = fileName;
    }

    public void setup(boolean downloadFromResources) {
        if (!Main.getInstance().getDataFolder().exists()) {
            Main.getInstance().getDataFolder().mkdir();
        }
        file = new File(Main.getInstance().getDataFolder() + "/"+fileName+".yml");

        if (!file.exists()) {

            if (downloadFromResources) {
                Main.getInstance().saveResource(fileName+".yml", false);
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        configuration = YamlConfiguration.loadConfiguration(file);

        reloadConfig();
    }

    private void reloadConfig() {
        configuration = YamlConfiguration.loadConfiguration(file);
        InputStream defConfigStream = Main.getInstance().getResource(fileName+".yml");
        if (defConfigStream != null) {
            configuration.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
        }
    }


    public FileConfiguration get() {
        return configuration;
    }

    public void save() {
        try {
            configuration.save(file);
        } catch (IOException ignored) {
        }
    }
}
