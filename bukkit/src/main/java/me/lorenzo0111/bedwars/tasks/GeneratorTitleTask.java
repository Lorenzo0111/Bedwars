package me.lorenzo0111.bedwars.tasks;

import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.game.GameState;
import me.lorenzo0111.bedwars.game.Game;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public class GeneratorTitleTask extends BukkitRunnable {
    private final Game game;

    public GeneratorTitleTask(Game game) {
        this.game = game;

        this.runTaskTimerAsynchronously(BedwarsPlugin.getInstance(), 0, 20L);
    }


    @Override
    public void run() {
        if (!game.getState().equals(GameState.PLAYING)) {
            this.cancel();
            return;
        }

        game.updateHolograms();
    }

}
