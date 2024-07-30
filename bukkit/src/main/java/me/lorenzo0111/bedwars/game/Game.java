package me.lorenzo0111.bedwars.game;

import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.game.AbstractGame;
import me.lorenzo0111.bedwars.api.game.GameState;
import me.lorenzo0111.bedwars.api.game.config.GameConfiguration;
import me.lorenzo0111.bedwars.api.game.config.TeamConfig;
import me.lorenzo0111.bedwars.hooks.WorldsHook;
import me.lorenzo0111.bedwars.tasks.Countdown;
import me.lorenzo0111.bedwars.tasks.GeneratorTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game extends AbstractGame {
    private final List<GeneratorTask> generatorTasks = new ArrayList<>();
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

        config.getGenerators().forEach((material, locations) ->
                locations.forEach(location ->
                        generatorTasks.add(new GeneratorTask(
                                material,
                                location.toLocation(world))
                        )));

        setState(GameState.PLAYING);
    }

    @Override
    public void stop() {
        generatorTasks.forEach(GeneratorTask::cancel);
        generatorTasks.clear();
        players.forEach(player -> player.getInventory().clear());
        players.clear();

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

    @Override
    public void onDeath(PlayerDeathEvent event) {
        ChatColor team = this.getTeam(event.getEntity());
        if (team == null) return;

        event.setDroppedExp(0);
        event.getDrops().clear();

        Bukkit.getScheduler().runTaskLater(BedwarsPlugin.getInstance(), () -> event.getEntity().spigot().respawn(), 1L);

        TeamConfig teamConfig = config.getTeam(team);
        if (teamConfig.getBed().toLocation(world).getBlock().getType().isAir()) {
            players.remove(event.getEntity());
            teams.get(team).remove(event.getEntity());

            event.getEntity().setGameMode(GameMode.SPECTATOR);
            event.getEntity().teleport(config.getSpectatorSpawn().toLocation(world));

            // TODO: Handle team elimination
            return;
        }

        event.getEntity().teleport(teamConfig.getSpawn().toLocation(world));
    }
}
