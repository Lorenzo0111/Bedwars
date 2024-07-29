package me.lorenzo0111.bedwars.commands.subcommands;

import me.lorenzo0111.bedwars.commands.BedwarsCommand;
import me.lorenzo0111.bedwars.commands.SubCommand;
import me.lorenzo0111.bedwars.commands.exceptions.OnlyPlayersException;
import me.lorenzo0111.bedwars.gui.menus.SetupMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetupCommand extends SubCommand {

    public SetupCommand(@NotNull BedwarsCommand command) {
        super(command);
    }

    @Override
    public void handle(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) throw new OnlyPlayersException();

        if (plugin.getSetupManager().inSetup(player)) {
            new SetupMenu(plugin.getSetupManager().getSession(player)).open(player);
            return;
        }

        plugin.getSetupManager().startSetup(player, args[0]);
    }

    @Override
    public String getName() {
        return "setup";
    }

    @Override
    public String getDescription() {
        return "Start an arena setup";
    }

    @Override
    public String getPermission() {
        return "bedwars.setup";
    }

    @Override
    public int getMinArgs() {
        return 1;
    }

    @Override
    public String getUsage() {
        return "setup <arena>";
    }
}
