package it.vin27dev.advancedfreeze.listeners;

import it.vin27dev.advancedfreeze.AdvancedFreezePlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import it.vin27dev.advancedfreeze.utilities.FileManager;

public class FreezeListeners implements Listener {

    private final AdvancedFreezePlugin plugin;

    public FreezeListeners(AdvancedFreezePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player player =  e.getPlayer();
        if(plugin.getFrozenPlayersManager().isFrozen(player.getUniqueId())) e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player =  e.getPlayer();
        if(plugin.getFrozenPlayersManager().isFrozen(player.getUniqueId())) e.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player =  e.getPlayer();
        if(plugin.getFrozenPlayersManager().isFrozen(player.getUniqueId())) {

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), FileManager.getLangCfg().getString("lang.cmd-on-quit").replaceAll("%target%", e.getPlayer().getName()));

            plugin.getFrozenPlayersManager().removeFromFrozenPlayers(player.getUniqueId());
            plugin.getStaffPlayersManager().removeFromStaffPlayers(player.getUniqueId());
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(e.getEntity().getType() == EntityType.PLAYER) {
            if(plugin.getFrozenPlayersManager().isFrozen(e.getEntity().getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }
}
