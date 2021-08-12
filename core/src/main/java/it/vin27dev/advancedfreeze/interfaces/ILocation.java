package it.vin27dev.advancedfreeze.interfaces;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public interface ILocation {

    void setLocation(Location loc, ConfigurationSection section);
    Location getLocation(ConfigurationSection section);
}
