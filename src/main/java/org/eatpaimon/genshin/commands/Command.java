package org.eatpaimon.genshin.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.eatpaimon.genshin.guis.CreateCharacterInv;

public class Command implements CommandExecutor {

    CreateCharacterInv characterInv = new CreateCharacterInv();

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage("原神MC：");
            commandSender.sendMessage("/gs c -- 角色界面");
            return true;
        }
        if (commandSender instanceof Player) {
            if (strings.length == 1 && strings[0].equals("c")) {
                Player p = (Player) commandSender;
                characterInv.createInventory(p);
                return true;
            }
        }
        return false;
    }
}
