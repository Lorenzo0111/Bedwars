package me.lorenzo0111.bedwars.tasks;

import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.hooks.scoreboard.ScoreboardHookWrapper;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardTickTask extends BukkitRunnable {

    public ScoreboardTickTask(BedwarsPlugin plugin) {
        this.runTaskTimerAsynchronously(plugin, 0, 10 * 20L);
    }

    @Override
    public void run() {
        ScoreboardHookWrapper.getHook().tick();
    }
}
