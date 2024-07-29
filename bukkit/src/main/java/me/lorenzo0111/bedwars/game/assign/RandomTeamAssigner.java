package me.lorenzo0111.bedwars.game.assign;

import me.lorenzo0111.bedwars.api.game.TeamAssigner;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class RandomTeamAssigner implements TeamAssigner {

    @Override
    public ChatColor assign(Player player, Map<ChatColor, List<Player>> teams, int maxPlayers) {
        for (Map.Entry<ChatColor, List<Player>> entry : teams.entrySet()) {
            if (entry.getValue().size() < maxPlayers) {
                entry.getValue().add(player);
                return entry.getKey();
            }
        }

        return null;
    }

}
