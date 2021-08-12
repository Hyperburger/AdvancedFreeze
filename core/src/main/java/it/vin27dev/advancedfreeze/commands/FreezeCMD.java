package it.vin27dev.advancedfreeze.commands;

import it.vin27dev.advancedfreeze.AdvancedFreezePlugin;
import it.vin27dev.advancedfreeze.interfaces.ILocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import it.vin27dev.advancedfreeze.inventories.InventoriesManager;
import it.vin27dev.advancedfreeze.manager.LocationManager;
import it.vin27dev.advancedfreeze.utilities.ChatUtility;
import it.vin27dev.advancedfreeze.utilities.FileManager;

public class FreezeCMD implements CommandExecutor {

    private final AdvancedFreezePlugin plugin;

    private final ILocation iLocation;

    public FreezeCMD(AdvancedFreezePlugin plugin) {
        this.plugin = plugin;

        this.iLocation = new LocationManager(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        /* I'm always returning true because otherwise the plugin sends the usage message set in the plugin.yml */

        if (!(sender instanceof Player)) {
            plugin.getServer().getConsoleSender().sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.only-players")));
            return true;
        }

        Player player = (Player) sender;
        if (args.length != 1) {
            player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.freeze-usage")));
            return true;
        }

        if(!player.hasPermission("af.freeze")) {
            player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.no-perms")));
            return true;
        }

        if(FileManager.getLocsCfg().getConfigurationSection("locations.freeze") == null || FileManager.getLocsCfg().getConfigurationSection("locations.unfreeze") == null) {
            player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.loc-not-found")));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.not-online")));
            return true;
        }

        if(player == target) {
            player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.cant-yourself")));
            return true;
        }

        if(plugin.getFrozenPlayersManager().isFrozen(target.getUniqueId()) && plugin.getStaffPlayersManager().isInMap(target.getUniqueId())) {
            player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.already-frozen")));
            return true;
        }

        plugin.getFrozenPlayersManager().addToFrozenPlayers(target.getUniqueId());
        plugin.getStaffPlayersManager().addToStaffPlayers(target.getUniqueId(), player.getUniqueId());

        Location teleportLoc = iLocation.getLocation(FileManager.getLocsCfg().getConfigurationSection("locations.freeze"));

        target.teleport(teleportLoc);
        player.teleport(teleportLoc);

        for (Player p : Bukkit.getWorld(teleportLoc.getWorld().getName()).getPlayers())
            target.hidePlayer(p);

        target.showPlayer(player);

        InventoriesManager inventoriesManager = new InventoriesManager(plugin);
        inventoriesManager.getInventory().open(target);

        PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 5);

        target.addPotionEffect(slowness);

        target.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.target-frozen")));

        player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.frozen-success")));

        ConfigurationSection titlesSection = FileManager.getLangCfg().getConfigurationSection("lang.freeze-titles");

        plugin.getTitle().buildTitle(player,
                titlesSection.getString("title"),
                titlesSection.getString("subTitle"),
                titlesSection.getInt("fadeIn"),
                titlesSection.getInt("stay"),
                titlesSection.getInt("fadeOut")
        );

        return true;
    }
}
