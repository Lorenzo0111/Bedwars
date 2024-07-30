package me.lorenzo0111.bedwars.game;

import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.game.AbstractGame;
import me.lorenzo0111.bedwars.api.game.GameState;
import me.lorenzo0111.bedwars.api.game.config.GameConfiguration;
import me.lorenzo0111.bedwars.hooks.WorldsHook;
import me.lorenzo0111.bedwars.tasks.Countdown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;

public class Game extends AbstractGame {
    private World world;
    private Countdown countdown = null;

    public Game(GameConfiguration config) {
        super(config);

        WorldsHook.createWorld(uuid, config.getId())
                .thenAccept(ignored -> WorldsHook.loadWorld(uuid)
                        .thenAccept(world -> this.world = Bukkit.getWorld(world.getName())));
    }

    @Override
    public void startCountdown() {
        BedwarsPlugin plugin = BedwarsPlugin.getInstance();

        this.setState(GameState.STARTING);

        this.countdown = new Countdown(5, seconds -> players.forEach(player -> player.sendTitle(
                plugin.getMessage("titles.start-countdown.title")
                        .replace("%seconds%", String.valueOf(seconds)),
                plugin.getMessage("titles.start-countdown.subtitle")
                        .replace("%seconds%", String.valueOf(seconds)),
                0, 20, 0
        )), this::start).start();
    }

    @Override
    public void abortCountdown() {
        if (countdown != null) countdown.cancel();

        this.countdown = null;
        this.setState(GameState.WAITING);
    }

    @Override
    public void start() {
        teams.clear();

        Map<Player, ChatColor> assignedPlayers = BedwarsPlugin.getInstance()
                .getTeamAssigner()
                .assignAll(players, new ArrayList<>(config.getTeams().keySet()),
                        config.getPlayersPerTeam());

        assignedPlayers.forEach((player, color) ->
                teams.computeIfAbsent(color, ignored -> new ArrayList<>()).add(player));

        teams.forEach((color, players) -> players.forEach(player ->
                player.teleport(config.getTeams()
                        .get(color).getSpawn().toLocation(world))));

        players.forEach(player -> player.setGameMode(GameMode.SURVIVAL));

        setState(GameState.PLAYING);
    }

    @Override
    public void stop() {
        players.forEach(player -> player.getInventory().clear());

        setState(GameState.SHUTDOWN);
        WorldsHook.deleteWorld(uuid);
    }

    @Override
    public boolean isLoading() {
        return world == null;
    }

    @Override
    public void join(Player player) {
        if (this.canJoin()) return;
        this.abortCountdown();

        players.add(player);
        player.getInventory().clear();
        player.teleport(config.getSpectatorSpawn().toLocation(world));
        player.setGameMode(GameMode.ADVENTURE);

        if (players.size() >= config.getMinPlayers()) startCountdown();
    }
}
