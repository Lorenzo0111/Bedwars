package me.lorenzo0111.bedwars.commands.subcommands;

import me.lorenzo0111.bedwars.commands.SubCommand;
import me.lorenzo0111.bedwars.commands.BedwarsCommand;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends SubCommand {

    public ReloadCommand(BedwarsCommand command) {
        super(command);
    }

    @Override
    public void handle(CommandSender sender, String[] args) {
        this.plugin.reload();
        sender.sendMessage(plugin.getPrefixed("reload"));
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getPermission() {
        return "bedwars.reload";
    }

    @Override
    public String getDescription() {
        return "Reload the plugin";
    }
}
