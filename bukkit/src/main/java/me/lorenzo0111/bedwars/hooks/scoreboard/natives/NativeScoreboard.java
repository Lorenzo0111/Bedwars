package me.lorenzo0111.bedwars.hooks.scoreboard.natives;

import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.scoreboard.ScoreboardHook;
import me.lorenzo0111.bedwars.api.scoreboard.WrappedScoreboard;
import me.lorenzo0111.bedwars.hooks.scoreboard.ScoreboardHookWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;

public class NativeScoreboard extends WrappedScoreboard {
    private final List<Player> players = new ArrayList<>();
    private final Scoreboard scoreboard;
    private final Objective objective;
    private boolean destroyed = false;

    @SuppressWarnings("ConstantConditions")
    public NativeScoreboard(ScoreboardHook hook, String title, Callable<List<String>> lines) {
        super(hook, title, lines);

        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("bedwars", Criteria.DUMMY, "bedwars");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(title);

        this.update();
    }


    @Override
    public void show(Player player) {
        if (destroyed) return;

        player.setScoreboard(scoreboard);
        players.add(player);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void hide(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        players.remove(player);
    }

    @Override
    public void destroy() {
        for (Player player : players)
            hide(player);

        players.clear();
        objective.unregister();
        destroyed = true;

        ScoreboardHookWrapper.getHook().remove(this);
    }

    @Override
    public void update() {
        if (destroyed) return;

        try {
            List<String> currentLines = lines.call();
            for (int i = 0; i < currentLines.size(); i++) {
                objective.getScore(currentLines.get(i)).setScore(i);
            }
        } catch (Exception e) {
            BedwarsPlugin.getInstance().getLogger().log(Level.SEVERE, "An error occurred while updating the scoreboard", e);
        }
    }
}
