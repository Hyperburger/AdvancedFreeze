package it.vin27dev.advancedfreeze.inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import it.vin27dev.advancedfreeze.AdvancedFreezePlugin;
import it.vin27dev.advancedfreeze.utilities.InventoryUtility;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import it.vin27dev.advancedfreeze.utilities.ChatUtility;
import it.vin27dev.advancedfreeze.utilities.FileManager;

import java.util.List;

public class InventoriesManager implements InventoryProvider {

    private final AdvancedFreezePlugin plugin;

    public InventoriesManager(AdvancedFreezePlugin plugin) {
        this.plugin = plugin;
    }

    public SmartInventory getInventory() {
        return SmartInventory.builder()
                .id("freezeGUI")
                .provider(new InventoriesManager(plugin))
                .size(FileManager.getGuiCfg().getInt("gui.rows"), FileManager.getGuiCfg().getInt("gui.columns"))
                .title(ChatUtility.color(FileManager.getGuiCfg().getString("gui.inv-name")))
                .manager(AdvancedFreezePlugin.inventoryManager)
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        for(String item : FileManager.getGuiCfg().getConfigurationSection("gui.items").getKeys(false)) {

            String material = FileManager.getGuiCfg().getString("gui.items." + item + ".material");

            int amount = FileManager.getGuiCfg().getInt("gui.items." + item + ".amount");

            int row = FileManager.getGuiCfg().getInt("gui.items." + item + ".row");
            int column = FileManager.getGuiCfg().getInt("gui.items." + item + ".column");

            List<String> lore = ChatUtility.colorList(FileManager.getGuiCfg().getStringList("gui.items." + item + ".lore"));

            String name = ChatUtility.color(FileManager.getGuiCfg().getString("gui.items." + item + ".name"));

            ItemStack itemStack = InventoryUtility.createItem(material, amount, name, lore);

            contents.set(row, column, ClickableItem.of(itemStack, e -> {

                boolean isForAdmit = FileManager.getGuiCfg().getBoolean("gui.items." + item + ".isForAdmit");
                boolean isForAnvil = FileManager.getGuiCfg().getBoolean("gui.items." + item + ".isForAnvil");

                if(isForAnvil) {

                    plugin.getTempAnvilManager().addToTempAnvilPlayers(player.getUniqueId());
                    player.closeInventory();

                    openAnvilGUI(player);
                } else if(isForAdmit) {

                    plugin.getFrozenPlayersManager().removeFromFrozenPlayers(player.getUniqueId());
                    plugin.getStaffPlayersManager().removeFromStaffPlayers(player.getUniqueId());

                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), FileManager.getGuiCfg().getString("gui.items." + item + ".admitCommand").replaceAll("%target%", player.getName()));
                }
            }));
        }
    }

    @Override
    public void update(Player player, InventoryContents contents) {}

    private void openAnvilGUI(Player target) {

        AnvilGUI.Builder builder = new AnvilGUI.Builder();

        builder.onClose(player -> {

            plugin.getTempAnvilManager().removeFromTempAnvilPlayers(player.getUniqueId());

            player.closeInventory();

            getInventory().open(player);
        });

        builder.onComplete((player, text) -> {

            Player staffPlayer = Bukkit.getPlayer(plugin.getStaffPlayersManager().getStaffPlayers().get(player.getUniqueId()));
            staffPlayer.sendMessage(ChatUtility.PREFIX + ChatUtility.color(FileManager.getLangCfg().getString("lang.discord-sent").replaceAll("%tag%", text)));

            plugin.getTempAnvilManager().removeFromTempAnvilPlayers(player.getUniqueId());

            return AnvilGUI.Response.openInventory(getInventory().open(player));
        });

        builder.text(ChatUtility.color(FileManager.getAnvilCfg().getString("anvil.default-text")));
        builder.title(ChatUtility.color(FileManager.getAnvilCfg().getString("anvil.title")));

        builder.itemLeft(InventoryUtility.createItem(FileManager.getAnvilCfg().getString("anvil.item-mat"),
                FileManager.getAnvilCfg().getInt("anvil.item-amount"),
                ChatUtility.color(FileManager.getAnvilCfg().getString("anvil.item-name")),
                ChatUtility.colorList(FileManager.getAnvilCfg().getStringList("anvil.item-lore"))));

        builder.plugin(plugin);
        builder.open(target);
    }
}
