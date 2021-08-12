package it.vin27dev.advancedfreeze.manager;

import it.vin27dev.advancedfreeze.AdvancedFreezePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import it.vin27dev.advancedfreeze.interfaces.ILocation;
import it.vin27dev.advancedfreeze.utilities.FileManager;
import org.bukkit.configuration.ConfigurationSection;

public class LocationManager implements ILocation {

    private final FileManager fileManager;

    public LocationManager(AdvancedFreezePlugin plugin) {
        this.fileManager = new FileManager(plugin);
    }

    @Override
    public void setLocation(Location location, ConfigurationSection section) {
        section.set("x", location.getX());
        section.set("y", location.getY());
        section.set("z", location.getZ());

        section.set("pitch", location.getPitch());

        section.set("yaw", location.getYaw());

        section.set("world", location.getWorld().getName());

        fileManager.saveConfig();
    }

    @Override
    public Location getLocation(ConfigurationSection section) {
        World world = Bukkit.getWorld(section.getString("world"));

        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");

        float yaw = (float) section.getDouble("yaw");
        float pitch = (float) section.getDouble("pitch");

        return new Location(world, x, y, z, yaw, pitch);
    }
}
