package it.vin27dev.advancedfreeze.titles;

import it.vin27dev.advancedfreeze.utilities.ChatUtility;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class v1_16_R3 extends TitlesAbstraction {

    @Override
    public void buildTitle(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;

        IChatBaseComponent titleComponent = new ChatComponentText(ChatUtility.color(title));
        IChatBaseComponent subTitleComponent = new ChatComponentText(ChatUtility.color(subTitle));

        PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleComponent, fadeIn, stay, fadeOut);
        PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subTitleComponent, fadeIn, stay, fadeOut);

        playerConnection.sendPacket(packetPlayOutTitle);
        playerConnection.sendPacket(packetPlayOutSubTitle);
    }
}
