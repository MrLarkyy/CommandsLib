package cz.larkyy.commandslib;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandBuilder {

    private final String command;
    private final String onlyPlayerError;
    private final String noPermissionError;

    private String permission;
    private Boolean canSendConsole;
    private List<String> aliases;

    public CommandBuilder(String command, String onlyPlayerError, String noPermissionError) {
        this.command = command;
        this.onlyPlayerError = onlyPlayerError;
        this.noPermissionError = noPermissionError;
        this.aliases = new ArrayList<>();
        this.canSendConsole = true;
    }

    public CommandBuilder addAlias(String... alias) {
        aliases.addAll(Arrays.asList(alias));
        return this;
    }

    public CommandBuilder setAliases(List<String> aliases) {
        this.aliases = aliases;
        return this;
    }

    public CommandBuilder removeAlias(String... alias) {
        aliases.removeAll(Arrays.asList(alias));
        return this;
    }

    public CommandBuilder setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public CommandBuilder setCanSendConsole(Boolean bool) {
        this.canSendConsole = bool;
        return this;
    }

    public CustomCommand build() {
        CustomCommand command = new CustomCommand(this.command,permission,canSendConsole,aliases,onlyPlayerError,noPermissionError);

        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap commandMap = (CommandMap) field.get(Bukkit.getServer());

            command.setAliases(aliases);
            ((BukkitCommand) command).setPermission(permission);

            commandMap.register(this.command, command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return command;
    }
}
