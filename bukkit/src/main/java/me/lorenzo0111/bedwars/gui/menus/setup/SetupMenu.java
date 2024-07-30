package me.lorenzo0111.bedwars.gui.menus.setup;

import me.lorenzo0111.bedwars.api.game.config.ConfigLocation;
import me.lorenzo0111.bedwars.game.setup.SetupSession;
import me.lorenzo0111.bedwars.gui.items.SimpleItem;
import me.lorenzo0111.bedwars.gui.items.setup.IntegerEditItem;
import me.lorenzo0111.bedwars.gui.items.setup.LocationEditItem;
import me.lorenzo0111.bedwars.gui.items.setup.SaveItem;
import me.lorenzo0111.bedwars.gui.menus.BaseMenu;

public class SetupMenu extends BaseMenu {

    public SetupMenu(SetupSession session) {
        super("setup.main", false);

        this.setItem('A', new IntegerEditItem("setup.arenas", value -> {
            if (value < 0) {
                value = 0;
            }

            session.config().setArenas(value);
        }));
        this.setItem('M', new IntegerEditItem("min-players", value -> {
            if (value < 0) {
                value = 0;
            }

            session.config().setMinPlayers(value);
        }));
        this.setItem('T', new IntegerEditItem("setup.team-players", value -> {
            if (value < 1) {
                value = 1;
            }

            session.config().setPlayersPerTeam(value);
        }));
        this.setItem('E', new SimpleItem("setup.teams", player -> new TeamsSetupMenu(session).open(player)));
        this.setItem('G', new SimpleItem("setup.generators", player -> new GeneratorsSetupMenu(session).open(player)));
        this.setItem('L', new LocationEditItem("setup.spectators", location -> session.config().setSpectatorSpawn(new ConfigLocation(location))));
        this.setItem('S', new SaveItem());
    }

}
