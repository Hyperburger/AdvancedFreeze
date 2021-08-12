package it.vin27dev.advancedfreeze.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import it.vin27dev.advancedfreeze.AdvancedFreezePlugin;
import it.vin27dev.advancedfreeze.interfaces.ILocation;
import it.vin27dev.advancedfreeze.manager.LocationManager;
import it.vin27dev.advancedfreeze.utilities.ChatUtility;
import it.vin27dev.advancedfreeze.utilities.FileManager;

import java.util.List;

public class AdvancedFreezeCMD implements CommandExecutor {


    private final AdvancedFreezePlugin plugin;
    private final ILocation iLocation;

    public AdvancedFreezeCMD(AdvancedFreezePlugin plugin) {
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

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!player.hasPermission("af.reload")) {

                    player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.no-perms")));
                    return true;
                }

                FileManager.reloadConfigs();

                player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.reloaded")));
                return true;
            } else {

                List<String> messageList = FileManager.getLangCfg().getStringList("lang.af-usage");

                for (String s : messageList) {
                    player.sendMessage(ChatUtility.color(s));
                }
                return true;
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("setloc")) {
                if (!player.hasPermission("af.setloc")) {

                    player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.no-perms")));
                    return true;
                }
                if (args[1].equalsIgnoreCase("freeze")) {

                    Location freezeLocation = player.getLocation();

                    iLocation.setLocation(freezeLocation, FileManager.getLocsCfg().createSection("locations.freeze"));

                    player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.loc-set")));
                    return true;
                } else if (args[1].equalsIgnoreCase("unfreeze")) {

                    Location unfreezeLocation = player.getLocation();

                    iLocation.setLocation(unfreezeLocation, FileManager.getLocsCfg().createSection("locations.unfreeze"));

                    player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.loc-set")));
                    return true;
                } else {

                    List<String> messageList = FileManager.getLangCfg().getStringList("lang.af-usage");

                    for (String s : messageList) {
                        player.sendMessage(ChatUtility.color(s));
                    }
                }
            }
        } else {

            List<String> messageList = FileManager.getLangCfg().getStringList("lang.af-usage");

            for (String s : messageList) {
                player.sendMessage(ChatUtility.color(s));
            }
        }
        return true;
    }
}
