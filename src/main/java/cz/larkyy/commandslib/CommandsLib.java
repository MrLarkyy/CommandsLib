package cz.larkyy.commandslib;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CommandsLib {

    public static boolean isCmdAlright(CustomCommand cmd, CommandSender sender) {
        if (!sender.hasPermission(cmd.getPermission())) {
            sender.sendMessage(cmd.getNoPermissionError());
            return false;
        } else if (!(sender instanceof Player) && !cmd.getCanSendConsole()) {
            sender.sendMessage(cmd.getOnlyPlayerError());
            return false;
        } else {
            return true;
        }
    }
}
