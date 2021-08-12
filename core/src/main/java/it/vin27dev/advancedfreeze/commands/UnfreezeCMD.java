package it.vin27dev.advancedfreeze.commands;

import it.vin27dev.advancedfreeze.AdvancedFreezePlugin;
import it.vin27dev.advancedfreeze.interfaces.ILocation;
import it.vin27dev.advancedfreeze.manager.LocationManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import it.vin27dev.advancedfreeze.utilities.ChatUtility;
import it.vin27dev.advancedfreeze.utilities.FileManager;

public class UnfreezeCMD implements CommandExecutor {

    private final AdvancedFreezePlugin plugin;

    private final ILocation iLocation;

    public UnfreezeCMD(AdvancedFreezePlugin plugin) {
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
            player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.unfreeze-usage")));
            return true;
        }

        if(!player.hasPermission("af.unfreeze")) {
            player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.no-perms")));
            return true;
        }

        if(FileManager.getLocsCfg().getConfigurationSection("locations.freeze") == null || FileManager.getLocsCfg().getConfigurationSection("locations.unfreeze") == null) {
            player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.loc-not-found")));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.not-online")));
            return true;
        }

        if(player == target) {
            player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.cant-yourself")));
            return true;
        }

        if(!plugin.getFrozenPlayersManager().isFrozen(target.getUniqueId()) && !plugin.getStaffPlayersManager().isInMap(target.getUniqueId())) {
            player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.not-frozen")));
            return true;
        }

        plugin.getFrozenPlayersManager().removeFromFrozenPlayers(target.getUniqueId());
        plugin.getStaffPlayersManager().removeFromStaffPlayers(target.getUniqueId());

        target.closeInventory();

        Location teleportLoc = iLocation.getLocation(FileManager.getLocsCfg().getConfigurationSection("locations.unfreeze"));

        target.teleport(teleportLoc);
        player.teleport(teleportLoc);

        for (Player p : Bukkit.getOnlinePlayers())
            target.showPlayer(p);

        target.removePotionEffect(PotionEffectType.SLOW);

        target.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.target-unfrozen")));
        player.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.unfrozen-success")));

        ConfigurationSection titlesSection = FileManager.getLangCfg().getConfigurationSection("lang.unfreeze-target-titles");

        plugin.getTitle().buildTitle(target,
                titlesSection.getString("title"),
                titlesSection.getString("subTitle"),
                titlesSection.getInt("fadeIn"),
                titlesSection.getInt("stay"),
                titlesSection.getInt("fadeOut")
        );

        return true;
    }
}
