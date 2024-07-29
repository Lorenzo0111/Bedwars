package me.lorenzo0111.bedwars.conversations;

import me.lorenzo0111.bedwars.BedwarsPlugin;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

public final class ConversationUtil {

    public static void start(Player player, Prompt prompt) {
        new ConversationFactory(BedwarsPlugin.getInstance())
                .withEscapeSequence("cancel")
                .withLocalEcho(false)
                .withFirstPrompt(prompt)
                .buildConversation(player)
                .begin();
    }

}
