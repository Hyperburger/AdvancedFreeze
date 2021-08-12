package it.vin27dev.advancedfreeze.listeners;

import it.vin27dev.advancedfreeze.AdvancedFreezePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;
import it.vin27dev.advancedfreeze.inventories.InventoriesManager;
import it.vin27dev.advancedfreeze.utilities.ChatUtility;
import it.vin27dev.advancedfreeze.utilities.FileManager;

public class InventoryListeners implements Listener {

    private final AdvancedFreezePlugin plugin;

    private final InventoriesManager inventoriesManager;

    public InventoryListeners(AdvancedFreezePlugin plugin) {
        this.plugin = plugin;
        this.inventoriesManager = new InventoriesManager(plugin);
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent e) {

        Player player = (Player) e.getPlayer();

        if(e.getView().getTitle().equalsIgnoreCase(ChatUtility.color(FileManager.getGuiCfg().getString("gui.inv-name")))) {
            if(!plugin.getTempAnvilManager().isInAnvil(player.getUniqueId()) && plugin.getFrozenPlayersManager().isFrozen(player.getUniqueId())) {

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        inventoriesManager.getInventory().open(player);
                    }
                }.runTaskLater(plugin, 1L);
            }
        }
    }
}
