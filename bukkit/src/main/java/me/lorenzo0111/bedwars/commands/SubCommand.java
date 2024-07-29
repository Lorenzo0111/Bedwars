package me.lorenzo0111.bedwars.commands;

import me.lorenzo0111.bedwars.BedwarsPlugin;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class SubCommand {
    protected final BedwarsPlugin plugin;
    protected final BedwarsCommand command;

    @Contract(pure = true)
    public SubCommand(@NotNull BedwarsCommand command) {
        this.command = command;
        this.plugin = command.getPlugin();
    }

    public void handle(CommandSender sender, String[] args) {
        throw new UnsupportedOperationException("This command is not implemented yet.");
    }

    public void handle(CommandSender sender, String label, String[] args) {
        this.handle(sender, args);
    }

    public List<String> handleTabCompletion(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

    public abstract String getName();

    public abstract String getDescription();

    public String getPermission() {
        return null;
    }

    public String getUsage() {
        return this.getName();
    }

    public int getMinArgs() {
        return 0;
    }

    public String[] getAliases() {
        return new String[0];
    }
}
