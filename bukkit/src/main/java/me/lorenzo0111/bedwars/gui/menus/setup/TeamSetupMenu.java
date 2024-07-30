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
        super("setup.team", false);

        this.setItem('S', new LocationEditItem("setup.team-spawn", location ->
                session.config().getTeam(team).setSpawn(new ConfigLocation(location))));
        this.setItem('B', new LocationEditItem("setup.team-bed", location -> {
            for (int x = -2; x <= 2; x++) {
                for (int y = -2; y <= 2; y++) {
                    if (location.getWorld().getBlockAt(
                                    location.getBlockX() + x,
                                    location.getBlockY() + y, location.getBlockZ())
                            .getType().name().contains("_BED")) {
                        session.config().getTeam(team).setBed(new ConfigLocation(location));
                        return;
                    }
                }
            }
        }));
        this.setItem('s', new LocationEditItem("setup.team-shop", location ->
                session.config().getTeam(team).setShop(new ConfigLocation(location))));
        this.setItem('U', new LocationEditItem("setup.team-upgrades", location ->
                session.config().getTeam(team).setUpgrades(new ConfigLocation(location))));
        this.setItem('F', new SimpleItem("setup.save", HumanEntity::closeInventory));
    }

}
