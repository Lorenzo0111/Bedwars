package me.lorenzo0111.bedwars.gui.menus.setup;

import me.lorenzo0111.bedwars.game.setup.SetupSession;
import me.lorenzo0111.bedwars.gui.items.setup.TeamColorItem;
import me.lorenzo0111.bedwars.gui.menus.BaseMenu;
import org.bukkit.ChatColor;

public class TeamsSetupMenu extends BaseMenu {

    public TeamsSetupMenu(SetupSession session) {
        super("setup-team", false);

        for (ChatColor color : ChatColor.values()) {
           if (!color.isColor()) continue;

           this.addItem(new TeamColorItem("setup-team-color", session, color));
        }
    }

}
