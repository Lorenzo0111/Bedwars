package me.lorenzo0111.bedwars.gui.menus;

import me.lorenzo0111.bedwars.api.game.AbstractGame;
import me.lorenzo0111.bedwars.gui.items.GameItem;

public class GamesMenu extends BaseMenu {

    public GamesMenu() {
        super("games", true);

        for (AbstractGame game : plugin.getGameManager().getGames())
            this.addItem(new GameItem(game));
    }

}
