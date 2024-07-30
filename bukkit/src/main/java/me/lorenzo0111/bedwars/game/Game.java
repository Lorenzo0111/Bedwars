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
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game extends AbstractGame {
    private final List<GeneratorTask> generatorTasks = new ArrayList<>();
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
    @SuppressWarnings("UnstableApiUsage")
    public void onDeath(PlayerDeathEvent event) {
        ChatColor team = this.getTeam(event.getEntity());
        if (team == null) return;

        if (event.getDamageSource().getCausingEntity() instanceof Player killer) {
            ChatColor killerColor = this.getTeam(killer);

            this.broadcast(BedwarsPlugin.getInstance()
                    .getPrefixed("kill")
                    .replace("%killer_color%", killerColor != null ? killerColor.toString() : ChatColor.GRAY.toString())
                    .replace("%killer%", killer.getName())
                    .replace("%player_color%", team.toString())
                    .replace("%player%", event.getEntity().getName()));
        } else {
            this.broadcast(BedwarsPlugin.getInstance()
                    .getPrefixed("death")
                    .replace("%player_color%", team.toString())
                    .replace("%player%", event.getEntity().getName()));
        }

        event.setDroppedExp(0);
        event.getDrops().clear();

        Bukkit.getScheduler().runTaskLater(BedwarsPlugin.getInstance(), () -> event.getEntity().spigot().respawn(), 1L);

        TeamConfig teamConfig = config.getTeam(team);
        if (teamConfig.getBed().toLocation(world).getBlock().getType().isAir()) {
            players.remove(event.getEntity());
            teams.get(team).remove(event.getEntity());

            event.getEntity().setGameMode(GameMode.SPECTATOR);
            event.getEntity().teleport(config.getSpectatorSpawn().toLocation(world));

            this.broadcast(BedwarsPlugin.getInstance()
                    .getPrefixed("team-elimination")
                    .replace("%team%", team.name())
                    .replace("%color%", team.toString()));
            return;
        }

        event.getEntity().teleport(teamConfig.getSpawn().toLocation(world));
    }

    @Override
    public void onBedBreak(BlockBreakEvent event) {
        ChatColor team = this.getTeam(event.getPlayer());
        if (team == null) return;

        TeamConfig teamConfig = config.getTeam(team);
        if (teamConfig.getBed().toLocation(world).equals(event.getBlock().getLocation())) {
            event.setCancelled(true);
            return;
        }

        event.setDropItems(false);

        config.getTeams().entrySet()
                .stream()
                .filter(entry -> entry.getValue().getBed().toLocation(world).equals(event.getBlock().getLocation()))
                .findFirst()
                .ifPresent(entry -> this.broadcast(BedwarsPlugin.getInstance()
                        .getPrefixed("bed-destroyed")
                        .replace("%team%", entry.getKey().name())
                        .replace("%color%", entry.getKey().toString())
                        .replace("%player%", event.getPlayer().getName())
                        .replace("%player_color%", team.toString())));
    }

    private void broadcast(String message) {
        players.forEach(player -> player.sendMessage(message));
    }
}
