package org.eatpaimon.genshin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.eatpaimon.genshin.commands.Command;
import org.eatpaimon.genshin.listener.EventListener;

import java.util.ArrayList;
import java.util.List;

public final class Genshin extends JavaPlugin {

    @Override
    public void onEnable() {

        Bukkit.getPluginCommand("genshin").setExecutor(new Command());
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        Bukkit.getLogger().info("插件加载成功");

        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
