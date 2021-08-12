package it.vin27dev.advancedfreeze;

import fr.minuskube.inv.InventoryManager;
import it.vin27dev.advancedfreeze.commands.AdvancedFreezeCMD;
import it.vin27dev.advancedfreeze.manager.FrozenPlayersManager;
import it.vin27dev.advancedfreeze.manager.StaffPlayersManager;
import it.vin27dev.advancedfreeze.titles.TitlesAbstraction;
import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import it.vin27dev.advancedfreeze.commands.FreezeCMD;
import it.vin27dev.advancedfreeze.commands.UnfreezeCMD;
import it.vin27dev.advancedfreeze.listeners.FreezeListeners;
import it.vin27dev.advancedfreeze.listeners.InventoryListeners;
import it.vin27dev.advancedfreeze.manager.TempAnvilManager;
import it.vin27dev.advancedfreeze.utilities.ChatUtility;
import it.vin27dev.advancedfreeze.utilities.FileManager;

import java.util.logging.Level;

public class AdvancedFreezePlugin extends JavaPlugin {

    @Getter private FrozenPlayersManager frozenPlayersManager;
    @Getter private StaffPlayersManager staffPlayersManager;

    @Getter private TempAnvilManager tempAnvilManager;

    @Getter private TitlesAbstraction title;

    public static InventoryManager inventoryManager;

    @Override
    public void onEnable() {

        if(!setupManager()) return;

        loadConfigs();
        loadCommands();

        registerEvents();

        this.frozenPlayersManager = new FrozenPlayersManager();
        this.staffPlayersManager = new StaffPlayersManager();

        this.tempAnvilManager = new TempAnvilManager();

        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        getServer().getConsoleSender().sendMessage(ChatUtility.color(ChatUtility.PREFIX + "&7Plugin successfully &cenabled&7!"));

    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
    }

    private void loadConfigs() {
        FileManager fileManager = new FileManager(this);
        fileManager.setupFiles();
    }

    private void loadCommands() {
        getCommand("advancedfreeze").setExecutor(new AdvancedFreezeCMD(this));
        getCommand("freeze").setExecutor(new FreezeCMD(this));
        getCommand("unfreeze").setExecutor(new UnfreezeCMD(this));
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new FreezeListeners(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListeners(this), this);
    }

    private boolean setupManager() {
        try {
            String packageName = TitlesAbstraction.class.getPackage().getName();
            String titleName = getServer().getClass().getPackage().getName().split("\\.")[3];
            title = (TitlesAbstraction) Class.forName(packageName + "." + titleName).newInstance();

            return true;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException exception) {
            getLogger().log(Level.SEVERE, "AdvancedFreeze could not find a valid implementation for this server version.");
            getLogger().log(Level.SEVERE, "Supported server versions: 1.8.8, 1.12.2, 1.16.5, 1.17.x.");
            getServer().getPluginManager().disablePlugin(this);

            return false;
        }
    }
}
