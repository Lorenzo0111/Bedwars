package me.lorenzo0111.bedwars.api.game;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TeamAssigner {

    ChatColor assign(Player player, Map<ChatColor, List<Player>> teams, int maxPlayers);

    default Map<Player, ChatColor> assignAll(List<Player> player, List<ChatColor> teams, int maxPlayers) {
        Map<Player, ChatColor> assigned = new HashMap<>();
        Map<ChatColor, List<Player>> teamsMap = new HashMap<>();

        for (ChatColor color : teams) {
            teamsMap.putIfAbsent(color, new ArrayList<>());
        }

        for (Player p : player) {
            ChatColor color = assign(p, teamsMap, maxPlayers);
            assigned.put(p, color);
        }

        return assigned;
    }
}
