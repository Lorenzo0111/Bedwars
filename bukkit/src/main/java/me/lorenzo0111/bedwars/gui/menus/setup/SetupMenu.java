package me.lorenzo0111.bedwars.gui.menus.setup;

import me.lorenzo0111.bedwars.game.setup.SetupSession;
import me.lorenzo0111.bedwars.gui.items.SimpleItem;
import me.lorenzo0111.bedwars.gui.items.setup.IntegerEditItem;
import me.lorenzo0111.bedwars.gui.items.setup.SaveItem;
import me.lorenzo0111.bedwars.gui.menus.BaseMenu;

public class SetupMenu extends BaseMenu {

    public SetupMenu(SetupSession session) {
        super("setup", false);

        this.setItem('A', new IntegerEditItem("set-arenas", value -> {
            if (value < 0) {
                value = 0;
            }

            session.config().setArenas(value);
        }));
        this.setItem('M', new IntegerEditItem("set-min-players", value -> {
            if (value < 0) {
                value = 0;
            }

            session.config().setMinPlayers(value);
        }));
        this.setItem('T', new IntegerEditItem("set-team-players", value -> {
            if (value < 1) {
                value = 1;
            }

            session.config().setPlayersPerTeam(value);
        }));
        this.setItem('E', new SimpleItem("setup-teams", player -> new TeamsSetupMenu(session).open(player)));
        this.setItem('G', new SimpleItem("setup-generators", player -> new GeneratorsSetupMenu(session).open(player)));
        this.setItem('S', new SaveItem());
    }

}
