package me.lorenzo0111.bedwars;

import me.lorenzo0111.bedwars.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public final class BedwarsPlugin extends JavaPlugin {
    private static BedwarsPlugin instance;
    private boolean firstRun = true;

    @Override
    public void onEnable() {
        instance = this;

        if (new File(this.getDataFolder(), "config.yml").exists())
            this.firstRun = false;

        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();



        this.reload();
        this.firstRun = false;

        // ******** Commands ********


        // ******** Listeners ********


        // ******** Tasks ********

    }


    @Override
    public void onDisable() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
            this.log("&cAn error occurred while disabling the plugin.");
            this.log("&cError: " + e.getMessage());
        }
    }

    public void log(String message) {
        ConsoleCommandSender logger = Bukkit.getConsoleSender();
        logger.sendMessage(StringUtils.color(getMessage("prefix") + message));
    }

    public String getMessage(String path, boolean messagesSection) {
        return StringUtils.color(
                this.getConfig().getString(messagesSection ? "messages." + path : path, "&cUnable to find the following key: &7" + path + "&c.")
        );
    }

    public List<String> getMessages(String path, boolean messagesSection) {
        return StringUtils.color(
                this.getConfig().getStringList(messagesSection ? "messages." + path : path)
        );
    }

    public String getMessage(String path) {
        return getMessage(path, true);
    }

    public List<String> getMessages(String path) {
        return getMessages(path, true);
    }

    public String getPrefixed(String path) {
        return getMessage("prefix") + StringUtils.color(
                this.getConfig().getString("messages." + path, "&cUnable to find the following key: &7" + path + "&c.")
        );
    }


    public void reload() {
        this.reloadConfig();
        this.log("&c&m---------------------------------------------------");
        this.log("             &c&lBed&f&lWars &7v" + this.getDescription().getVersion());
        this.log(" ");

        if (firstRun) {
            this.log(" &7Thanks for installing the plugin.");
            this.log(" &7We detected that this is the first time you run the plugin.");
            this.log(" &7Please, configure the plugin in the config.yml file.");
            this.log(" &7When you are done, run &c&n/bedwars reload&7 to reload the plugin.");
        }

        this.log("&c&m---------------------------------------------------");
    }

    public static BedwarsPlugin getInstance() {
        return instance;
    }
}
