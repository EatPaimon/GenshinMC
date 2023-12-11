package org.eatpaimon.genshin.save;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {
    public void saveTeams(List<ItemStack> list, Player player){
        File teamFile = new File("plugins/Genshin/Teams", player + ".yml");
        FileConfiguration teamData = YamlConfiguration.loadConfiguration(teamFile);

        int i = list.size();
        if (i == 4) {
            Map<String, Object> map1 = list.get(0).serialize();
            Map<String, Object> map2 = list.get(1).serialize();
            Map<String, Object> map3 = list.get(2).serialize();
            Map<String, Object> map4 = list.get(3).serialize();

            teamData.set("one", map1);
            teamData.set("two", map2);
            teamData.set("three", map3);
            teamData.set("four", map4);
        } else if (i == 3) {
            Map<String, Object> map1 = list.get(0).serialize();
            Map<String, Object> map2 = list.get(1).serialize();
            Map<String, Object> map3 = list.get(2).serialize();

            teamData.set("one", map1);
            teamData.set("two", map2);
            teamData.set("three", map3);
            teamData.set("four", null);
        } else if (i == 2) {
            Map<String, Object> map1 = list.get(0).serialize();
            Map<String, Object> map2 = list.get(1).serialize();

            teamData.set("one", map1);
            teamData.set("two", map2);
            teamData.set("three", null);
            teamData.set("four", null);
        } else if (i == 1) {
            Map<String, Object> map1 = list.get(0).serialize();

            teamData.set("one", map1);
            teamData.set("two", null);
            teamData.set("three", null);
            teamData.set("four", null);
        } else if (i == 0) {
            teamData.set("one", null);
            teamData.set("two", null);
            teamData.set("three", null);
            teamData.set("four", null);
        }
        try {
            teamData.save(teamFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void saveFirstTeams(Player player){
        File teamFile = new File("plugins/Genshin/Teams", player + ".yml");
        FileConfiguration teamData = YamlConfiguration.loadConfiguration(teamFile);

        ItemStack traveler = Character("旅行者", 1);

        Map<String, Object> map2 =  traveler.serialize();

        teamData.set("one", map2);
        teamData.set("two", null);
        teamData.set("three", null);
        teamData.set("four", null);

        try {
            teamData.save(teamFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<ItemStack> loadGenshinTeam(Player player) {
        File teamFile = new File("plugins/Genshin/Teams", player + ".yml");
        FileConfiguration teamData = YamlConfiguration.loadConfiguration(teamFile);

        ConfigurationSection section1 = teamData.getConfigurationSection("one");
        ConfigurationSection section2 = teamData.getConfigurationSection("two");
        ConfigurationSection section3 = teamData.getConfigurationSection("three");
        ConfigurationSection section4 = teamData.getConfigurationSection("four");

        Map<String, Object> map1 = section1 != null ? section1.getValues(false) : null;
        Map<String, Object> map2 = section2 != null ? section2.getValues(false) : null;
        Map<String, Object> map3 = section3 != null ? section3.getValues(false) : null;
        Map<String, Object> map4 = section4 != null ? section4.getValues(false) : null;
        if (map1 != null && map2 != null && map3 != null && map4 != null ) {
            List<ItemStack> list = new ArrayList<>();
            list.add(ItemStack.deserialize(map1));
            list.add(ItemStack.deserialize(map2));
            list.add(ItemStack.deserialize(map3));
            list.add(ItemStack.deserialize(map4));
            return list;
        } else if (map1 != null && map2 != null && map3 != null) {
            List<ItemStack> list = new ArrayList<>();
            list.add(ItemStack.deserialize(map1));
            list.add(ItemStack.deserialize(map2));
            list.add(ItemStack.deserialize(map3));
            list.add(new ItemStack(Material.AIR));
            return list;
        } else if (map1 != null && map2 != null) {
            List<ItemStack> list = new ArrayList<>();
            list.add(ItemStack.deserialize(map1));
            list.add(ItemStack.deserialize(map2));
            list.add(new ItemStack(Material.AIR));
            return list;
        } else if (map1 != null) {
            List<ItemStack> list = new ArrayList<>();
            list.add(ItemStack.deserialize(map1));
            list.add(new ItemStack(Material.AIR));
            return list;
        } else {
            return null;
        }
    }
    public Map<String, Object> stringToMap(String json){
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        try {
            yamlConfiguration.loadFromString(json);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> map;
        map = yamlConfiguration.getValues(false);
        return map;
    }
    public ItemStack Character(String name, int level){
        ItemStack characterPai = new ItemStack(Material.PAPER);
        ItemMeta meta = characterPai.getItemMeta();
        meta.setCustomModelData(0);
        List<String> list = new ArrayList<>();
        list.add("角色牌");
        list.add("等级：" + level);
        meta.setDisplayName(name);
        meta.setLore(list);
        characterPai.setItemMeta(meta);
        return characterPai;
    }
    public List<ItemStack> characterTeam(ItemStack character1, ItemStack character2, ItemStack character3, ItemStack character4){
        List<ItemStack> list = new ArrayList<>();
        list.add(character1);
        list.add(character2);
        list.add(character3);
        list.add(character4);
        return list;
    }
}
