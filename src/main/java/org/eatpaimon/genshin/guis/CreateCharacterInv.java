package org.eatpaimon.genshin.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eatpaimon.genshin.save.DataManager;

import java.util.List;

public class CreateCharacterInv {
    DataManager dataManager = new DataManager();
    public void createInventory(Player player){
        Inventory inv = Bukkit.createInventory(player, 9, "角色队伍");
        ItemStack itemStack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("分割线");
        itemStack.setItemMeta(meta);

        List<ItemStack> list = dataManager.loadGenshinTeam(player);
        if (list.size() == 4) {
            ItemStack character1 = list.get(0);
            ItemStack character2 = list.get(1);
            ItemStack character3 = list.get(2);
            ItemStack character4 = list.get(3);

            inv.setItem(0, itemStack);
            inv.setItem(1, character1);
            inv.setItem(2, itemStack);
            inv.setItem(3, character2);
            inv.setItem(4, itemStack);
            inv.setItem(5, character3);
            inv.setItem(6, itemStack);
            inv.setItem(7, character4);
            inv.setItem(8, itemStack);
            player.openInventory(inv);
        } else if (list.size() == 3) {
            ItemStack character1 = list.get(0);
            ItemStack character2 = list.get(1);
            ItemStack character3 = list.get(2);

            inv.setItem(0, itemStack);
            inv.setItem(1, character1);
            inv.setItem(2, itemStack);
            inv.setItem(3, character2);
            inv.setItem(4, itemStack);
            inv.setItem(5, character3);
            inv.setItem(6, itemStack);
            inv.setItem(7, new ItemStack(Material.AIR));
            inv.setItem(8, itemStack);
            player.openInventory(inv);
        } else if (list.size() == 2) {
            ItemStack character1 = list.get(0);
            ItemStack character2 = list.get(1);

            inv.setItem(0, itemStack);
            inv.setItem(1, character1);
            inv.setItem(2, itemStack);
            inv.setItem(3, character2);
            inv.setItem(4, itemStack);
            inv.setItem(5, new ItemStack(Material.AIR));
            inv.setItem(6, itemStack);
            inv.setItem(7, new ItemStack(Material.AIR));
            inv.setItem(8, itemStack);
            player.openInventory(inv);
        } else if (list.size() == 1) {
            ItemStack character1 = list.get(0);

            inv.setItem(0, itemStack);
            inv.setItem(1, character1);
            inv.setItem(2, itemStack);
            inv.setItem(3, new ItemStack(Material.AIR));
            inv.setItem(4, itemStack);
            inv.setItem(5, new ItemStack(Material.AIR));
            inv.setItem(6, itemStack);
            inv.setItem(7, new ItemStack(Material.AIR));
            inv.setItem(8, itemStack);
            player.openInventory(inv);
        }
    }
}
