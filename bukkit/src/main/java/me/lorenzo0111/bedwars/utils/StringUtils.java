package me.lorenzo0111.bedwars.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class StringUtils {
    private static final Pattern PATTERN = Pattern.compile("&#[a-fA-F0-9]{6}");

    public static String color(String message) {
        Matcher matcher = PATTERN.matcher(message);

        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, ChatColor.of(color.replace("&", "")) + "");
            matcher = PATTERN.matcher(message);
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> color(List<String> messages) {
        return messages.stream()
                .map(StringUtils::color)
                .collect(Collectors.toList());
    }

    public static String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

}
