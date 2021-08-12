package it.vin27dev.advancedfreeze.utilities;

import com.cryptomorin.xseries.XMaterial;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Optional;

@UtilityClass
public class InventoryUtility {

    public ItemStack createItem(String materialName, int amount, String name, List<String> lore) {
        Optional<XMaterial> xMaterial = XMaterial.matchXMaterial(materialName);
        ItemStack itemStack = new ItemStack(xMaterial.get().parseMaterial(), amount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
