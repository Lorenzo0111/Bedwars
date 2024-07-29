package me.lorenzo0111.bedwars.game;

import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.game.AbstractGame;
import me.lorenzo0111.bedwars.api.game.config.GameConfiguration;
import me.lorenzo0111.bedwars.hooks.WorldsHook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;

public class Game extends AbstractGame {
    private World world;

    public Game(GameConfiguration config) {
        super(config);

        WorldsHook.createWorld(uuid, config.getId())
                .thenAccept(ignored -> WorldsHook.loadWorld(uuid)
                        .thenAccept(world -> this.world = Bukkit.getWorld(world.getName())));
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
    }

    @Override
    public void stop() {
        WorldsHook.deleteWorld(uuid);
    }

    @Override
    public boolean isLoading() {
        return world == null;
    }
}
