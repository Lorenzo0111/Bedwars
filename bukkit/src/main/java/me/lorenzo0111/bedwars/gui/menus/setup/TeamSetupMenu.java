package me.lorenzo0111.bedwars.gui.menus.setup;

import me.lorenzo0111.bedwars.api.game.config.ConfigLocation;
import me.lorenzo0111.bedwars.game.setup.SetupSession;
import me.lorenzo0111.bedwars.gui.items.SimpleItem;
import me.lorenzo0111.bedwars.gui.items.setup.LocationEditItem;
import me.lorenzo0111.bedwars.gui.menus.BaseMenu;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;

public class TeamSetupMenu extends BaseMenu {

    public TeamSetupMenu(SetupSession session, ChatColor team) {
        super("setup-team", false);

        this.setItem('S', new LocationEditItem("setup-team-spawn", location ->
                session.config().getTeam(team).setSpawn(new ConfigLocation(location))));
        this.setItem('B', new LocationEditItem("setup-team-bed", location ->
                session.config().getTeam(team).setBed(new ConfigLocation(location))));
        this.setItem('s', new LocationEditItem("setup-team-shop", location ->
                session.config().getTeam(team).setShop(new ConfigLocation(location))));
        this.setItem('U', new LocationEditItem("setup-team-upgrades", location ->
                session.config().getTeam(team).setUpgrades(new ConfigLocation(location))));
        this.setItem('G', new LocationEditItem("setup-team-generator", location ->
                session.config().getTeam(team).setGenerator(new ConfigLocation(location))));
        this.setItem('F', new SimpleItem("save", HumanEntity::closeInventory));
    }

}
