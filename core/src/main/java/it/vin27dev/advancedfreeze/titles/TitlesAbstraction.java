package it.vin27dev.advancedfreeze.titles;

import org.bukkit.entity.Player;

public abstract class TitlesAbstraction {

    public abstract void buildTitle(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut);
}
