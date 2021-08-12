package it.vin27dev.advancedfreeze.utilities;

import it.vin27dev.advancedfreeze.AdvancedFreezePlugin;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private static File lang;
    @Getter private static FileConfiguration langCfg;

    private static File anvilGui;
    @Getter private static FileConfiguration anvilCfg;

    private static File gui;
    @Getter private static FileConfiguration guiCfg;

    private static File locs;
    @Getter private static FileConfiguration locsCfg;

    private final AdvancedFreezePlugin plugin;

    public FileManager(AdvancedFreezePlugin plugin) {
        this.plugin = plugin;
    }

    public void setupFiles() {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        lang = new File(plugin.getDataFolder(), "lang.yml");
        if(!lang.exists()) {
            plugin.saveResource("lang.yml", false);
        }
        langCfg = YamlConfiguration.loadConfiguration(lang);

        anvilGui = new File(plugin.getDataFolder(), "anvilgui.yml");
        if(!anvilGui.exists()) {
            plugin.saveResource("anvilgui.yml", false);
        }
        anvilCfg = YamlConfiguration.loadConfiguration(anvilGui);

        gui = new File(plugin.getDataFolder(), "gui.yml");
        if(!gui.exists()) {
            plugin.saveResource("gui.yml", false);
        }
        guiCfg = YamlConfiguration.loadConfiguration(gui);

        locs = new File(plugin.getDataFolder(), "location.yml");
        if(!locs.exists()) {
            plugin.saveResource("location.yml", false);
        }
        locsCfg = YamlConfiguration.loadConfiguration(locs);
    }

    public void saveConfig() {
        try {
            langCfg.save(lang);
            anvilCfg.save(anvilGui);
            guiCfg.save(gui);
            locsCfg.save(locs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadConfigs() {
        langCfg = YamlConfiguration.loadConfiguration(lang);
        anvilCfg = YamlConfiguration.loadConfiguration(anvilGui);
        guiCfg = YamlConfiguration.loadConfiguration(gui);
        locsCfg = YamlConfiguration.loadConfiguration(locs);
    }
}
