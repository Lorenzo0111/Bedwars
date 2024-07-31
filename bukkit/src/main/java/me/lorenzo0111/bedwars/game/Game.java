package me.lorenzo0111.bedwars.game;

import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.game.AbstractGame;
import me.lorenzo0111.bedwars.api.game.GameState;
import me.lorenzo0111.bedwars.api.game.config.GameConfiguration;
import me.lorenzo0111.bedwars.api.game.config.TeamConfig;
import me.lorenzo0111.bedwars.api.hologram.WrappedHologram;
import me.lorenzo0111.bedwars.api.scoreboard.WrappedScoreboard;
import me.lorenzo0111.bedwars.hooks.WorldsHook;
import me.lorenzo0111.bedwars.hooks.hologram.HologramHookWrapper;
import me.lorenzo0111.bedwars.hooks.scoreboard.ScoreboardHookWrapper;
import me.lorenzo0111.bedwars.tasks.CountdownTask;
import me.lorenzo0111.bedwars.tasks.GeneratorDropTask;
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
    private static final BedwarsPlugin plugin = BedwarsPlugin.getInstance();
    private final List<GeneratorDropTask> generatorTasks = new ArrayList<>();
    private final List<WrappedHologram> generatorHolograms = new ArrayList<>();
    private CountdownTask countdown = null;
    private WrappedScoreboard scoreboard;

    public Game(GameConfiguration config) {
        super(config);

        WorldsHook.createWorld(uuid, config.getId())
                .thenAccept(ignored -> WorldsHook.loadWorld(uuid)
                        .thenAccept(world -> this.world = Bukkit.getWorld(world.getName())));
    }

    @Override
    public void startCountdown() {
        this.setState(GameState.STARTING);

        this.countdown = new CountdownTask(5, seconds -> players.forEach(player -> player.sendTitle(
                plugin.getMessage("titles.start-countdown.title")
                        .replace("%seconds%", String.valueOf(seconds)),
                plugin.getMessage("titles.start-countdown.subtitle")
                        .replace("%seconds%", String.valueOf(seconds)),
                0, 20, 0
        )), this::start);
    }

    @Override
    public void abortCountdown() {
        if (countdown != null) countdown.cancel();

        this.countdown = null;
        this.setState(GameState.WAITING);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start() {
        setState(GameState.PLAYING);

        teams.clear();

        Map<Player, ChatColor> assignedPlayers = plugin.getTeamAssigner()
                .assignAll(players, new ArrayList<>(config.getTeams().keySet()),
                        config.getPlayersPerTeam());

        assignedPlayers.forEach((player, color) ->
                teams.computeIfAbsent(color, ignored -> new ArrayList<>()).add(player));

        teams.forEach((color, players) -> players.forEach(player ->
                player.teleport(config.getTeams()
                        .get(color).getSpawn().toLocation(world))));

        players.forEach(player -> player.setGameMode(GameMode.SURVIVAL));

        config.getGenerators().forEach((material, locations) ->
                locations.forEach(location -> {
                    generatorTasks.add(new GeneratorDropTask(
                            material,
                            location.toLocation(world))
                    );

                    List<String> lines = plugin.getMessages("generators." + material.name().toLowerCase());
                    if (lines.isEmpty()) return;

                    generatorHolograms.add(HologramHookWrapper.getHook().create(
                            location.toLocation(world),
                            lines,
                            List.of(material)
                    ));
                }));

        this.scoreboard = ScoreboardHookWrapper.getHook()
                .create(
                        plugin.getMessage("scoreboards.game.title"),
                        () -> {
                            List<String> lines = plugin.getMessages("scoreboards.game.lines");

                            for (int i = 0; i < lines.size(); i++) {
                                if (!lines.get(i).equalsIgnoreCase("%teams%")) continue;

                                for (int j = 0; j < teams.size(); j++) {
                                    Map.Entry<ChatColor, List<Player>> team = (Map.Entry<ChatColor, List<Player>>) teams.entrySet().toArray()[j];

                                    lines.add(i + j, plugin.getMessage("scoreboards.game.team-format")
                                            .replace("%team%", team.getKey().name())
                                            .replace("%color%", team.getKey().toString())
                                            .replace("%players%", String.valueOf(team.getValue().size())));
                                }
                            }

                            return lines;
                        }
                );

        players.forEach(scoreboard::show);
    }

    @Override
    public void stop() {
        generatorHolograms.forEach(WrappedHologram::remove);
        generatorHolograms.clear();

        generatorTasks.forEach(GeneratorDropTask::cancel);
        generatorTasks.clear();

        players.forEach(player -> player.getInventory().clear());
        players.clear();

        if (this.scoreboard != null)
            this.scoreboard.destroy();
        this.scoreboard = null;

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
    public void spectate(Player player) {
        if (players.contains(player)) return;

        player.getInventory().clear();
        player.teleport(config.getSpectatorSpawn().toLocation(world));
        player.setGameMode(GameMode.SPECTATOR);
    }

    @Override
    public void onWin(ChatColor winner) {
        this.broadcast(plugin.getPrefixed("win")
                .replace("%team%", winner.name())
                .replace("%color%", winner.toString()));

        this.stop();
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void onDeath(PlayerDeathEvent event) {
        ChatColor team = this.getTeam(event.getEntity());
        if (team == null) return;

        if (event.getDamageSource().getCausingEntity() instanceof Player killer) {
            ChatColor killerColor = this.getTeam(killer);

            this.broadcast(plugin.getPrefixed("kill")
                    .replace("%killer_color%", killerColor != null ? killerColor.toString() : ChatColor.GRAY.toString())
                    .replace("%killer%", killer.getName())
                    .replace("%player_color%", team.toString())
                    .replace("%player%", event.getEntity().getName()));
        } else {
            this.broadcast(plugin.getPrefixed("death")
                    .replace("%player_color%", team.toString())
                    .replace("%player%", event.getEntity().getName()));
        }

        event.setDroppedExp(0);
        event.getDrops().clear();

        Bukkit.getScheduler().runTaskLater(plugin, () -> event.getEntity().spigot().respawn(), 1L);
        event.getEntity().setGameMode(GameMode.SPECTATOR);
        event.getEntity().teleport(config.getSpectatorSpawn().toLocation(world));

        TeamConfig teamConfig = config.getTeam(team);
        if (teamConfig.getBed().toLocation(world).getBlock().getType().isAir()) {
            players.remove(event.getEntity());
            teams.get(team).remove(event.getEntity());

            if (teams.get(team).isEmpty()) {
                this.broadcast(plugin.getPrefixed("team-elimination")
                        .replace("%team%", team.name())
                        .replace("%color%", team.toString()));

                teams.entrySet()
                        .stream()
                        .filter(entry -> !entry.getValue().isEmpty())
                        .findFirst()
                        .ifPresent(winner -> this.onWin(winner.getKey()));
            }
            return;
        }

        new CountdownTask(5, seconds -> event.getEntity().sendTitle(
                plugin.getMessage("titles.respawn.title")
                        .replace("%time%", String.valueOf(seconds)),
                plugin.getMessage("titles.respawn.subtitle")
                        .replace("%time%", String.valueOf(seconds)),
                0, 20, 0
        ), () -> {
            event.getEntity().teleport(teamConfig.getSpawn().toLocation(world));
            event.getEntity().setGameMode(GameMode.SURVIVAL);
        });
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
                .ifPresent(entry -> this.broadcast(plugin.getPrefixed("bed-destroyed")
                        .replace("%team%", entry.getKey().name())
                        .replace("%color%", entry.getKey().toString())
                        .replace("%player%", event.getPlayer().getName())
                        .replace("%player_color%", team.toString())));
    }

    private void broadcast(String message) {
        players.forEach(player -> player.sendMessage(message));
    }
}
