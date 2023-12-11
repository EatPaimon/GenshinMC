package org.eatpaimon.genshin.listener;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eatpaimon.genshin.save.DataManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EventListener implements Listener {
    DataManager dataManager = new DataManager();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player)event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        Inventory topInventory = event.getView().getTopInventory();
        if (inventory != null && inventory.getHolder() instanceof Player && inventory.getSize() != 9){
            if (event.getView().getTitle().equals("角色队伍")){
                event.setCancelled(true);
                ItemStack item = event.getCurrentItem();
                if (item != null) {
                    ItemMeta meta = item.getItemMeta();
                    List<String> loreList = meta.getLore();
                    if (item.getType() != Material.AIR && item.getItemMeta().hasLore() && loreList.get(0).equals("角色牌")) {
                        for (int i = 0; i < 9; i++) {
                            ItemStack itemStack = topInventory.getItem(i);
                            if (itemStack == null || itemStack.getType() == Material.AIR) {
                                topInventory.setItem(i, item);
                                int slot = event.getRawSlot();
                                if (slot >= 36) {
                                    player.getInventory().setItem(slot - 36, new ItemStack(Material.AIR));
                                } else {
                                    player.getInventory().setItem(slot, new ItemStack(Material.AIR));
                                }
                                player.updateInventory();
                                break;
                            }
                        }
                    }
                }
            }
        } else if (inventory != null && event.getView().getTitle().equals("角色队伍")) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (!(item == null || item.getType() == Material.AIR || item.getType() == Material.GRAY_STAINED_GLASS_PANE)){

                boolean b1 = false;
                boolean b2 = false;
                boolean b3 = false;
                boolean b4 = false;

                for (int i = 1;i < 9;i = i + 2){
                    ItemStack itemStack = inventory.getItem(i);
                    if (itemStack != null && itemStack.getType() != Material.AIR){
                        if (i == 1){
                            b1 = true;
                        }
                        if (i == 3){
                            b2 = true;
                        }
                        if (i == 5){
                            b3 = true;
                        }
                        if (i == 7){
                            b4 = true;
                        }
                    }
                }
                if ((!b1 && !b2 && !b3) || (!b2 && !b3 && !b4) || (!b1 && !b3 && !b4) || (!b1 && !b2 && !b4)){
                    player.sendMessage("队伍中至少留一名角色");
                }else {
                    for (int i = 0; i < 36; i++) {
                        ItemStack itemStack = player.getInventory().getItem(i);
                        if (itemStack == null || itemStack.getType() == Material.AIR) {
                            player.getInventory().setItem(i, item);
                            topInventory.setItem(event.getRawSlot(), new ItemStack(Material.AIR));
                            player.updateInventory();
                            break;
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        Inventory topInventory = event.getView().getTopInventory();
        if (event.getView().getTitle().equals("角色队伍")) {
            List<ItemStack> team = new ArrayList<>();
            for (int i = 1; i < 9;i = i + 2) {
                ItemStack itemStack = topInventory.getItem(i);
                if (itemStack != null && itemStack.getType() != Material.AIR){
                    team.add(itemStack);
                }
            }
            dataManager.saveTeams(team, player);
        }
    }
    @EventHandler
    public void onNewJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()){
            dataManager.saveFirstTeams(player);
        }
        File teamFile = new File("plugins/Genshin/Teams", player + ".yml");
        FileConfiguration teamData = YamlConfiguration.loadConfiguration(teamFile);
        String s = teamData.getString("one");
        if (s == null){
            dataManager.saveFirstTeams(player);
        }
    }
}
