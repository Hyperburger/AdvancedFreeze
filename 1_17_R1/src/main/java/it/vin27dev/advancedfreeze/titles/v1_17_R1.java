package it.vin27dev.advancedfreeze.titles;

import it.vin27dev.advancedfreeze.utilities.ChatUtility;
import org.bukkit.entity.Player;

public class v1_17_R1 extends TitlesAbstraction {

    @Override
    public void buildTitle(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(ChatUtility.color(title), ChatUtility.color(subTitle), fadeIn, stay, fadeOut);
    }
}
