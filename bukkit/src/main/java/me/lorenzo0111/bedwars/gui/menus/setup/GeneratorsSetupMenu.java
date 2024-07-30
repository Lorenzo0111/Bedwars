package me.lorenzo0111.bedwars.gui.menus.setup;

import me.lorenzo0111.bedwars.game.setup.SetupSession;
import me.lorenzo0111.bedwars.gui.items.setup.GeneratorItem;
import me.lorenzo0111.bedwars.gui.items.setup.LocationEditItem;
import me.lorenzo0111.bedwars.gui.items.setup.TeamColorItem;
import me.lorenzo0111.bedwars.gui.menus.BaseMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;

public class GeneratorsSetupMenu extends BaseMenu {

    public GeneratorsSetupMenu(SetupSession session) {
        super("setup-generators", true);

        for (Material material : Arrays.asList(Material.IRON_INGOT, Material.GOLD_INGOT, Material.DIAMOND, Material.EMERALD)) {
           this.addItem(new GeneratorItem(session, material));
        }
    }

}
