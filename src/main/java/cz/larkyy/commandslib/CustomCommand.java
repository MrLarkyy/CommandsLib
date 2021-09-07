package cz.larkyy.commandslib;

import cz.larkyy.commandslib.events.CustomCommandEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.List;

public class CustomCommand extends BukkitCommand {

    private final String command;
    private final String permission;
    private final Boolean canSendConsole;
    private final List<String> aliases;

    private final String onlyPlayerError;
    private final String noPermissionError;

    public CustomCommand(String command, String permission, boolean canSendConsole, List<String> aliases, String onlyPlayerError, String noPermissionError) {
        super(command);
        this.command = command;
        this.permission = permission;
        this.canSendConsole = canSendConsole;
        this.aliases = aliases;
        this.onlyPlayerError = onlyPlayerError;
        this.noPermissionError = noPermissionError;
    }

    private boolean isCmdAlright(CustomCommand cmd, CommandSender sender) {
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

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {

        if (!isCmdAlright(this,sender)) {
            return false;
        }

        CustomCommandEvent e = new CustomCommandEvent(this,sender,commandLabel,args);
        Bukkit.getPluginManager().callEvent(e);

        return false;
    }

    public String getCommand() {
        return command;
    }

    public String getOnlyPlayerError() {
        return onlyPlayerError;
    }

    public String getNoPermissionError() {
        return noPermissionError;
    }

    public String getPermission() {
        return permission;
    }

    public Boolean getCanSendConsole() {
        return canSendConsole;
    }

    public List<String> getAliases() {
        return aliases;
    }
}
