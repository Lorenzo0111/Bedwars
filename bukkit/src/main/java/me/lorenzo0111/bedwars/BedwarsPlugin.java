package me.lorenzo0111.bedwars;

import lombok.Getter;
import me.lorenzo0111.bedwars.api.BedwarsAPI;
import me.lorenzo0111.bedwars.api.game.config.GameConfiguration;
import me.lorenzo0111.bedwars.api.game.config.TeamConfig;
import me.lorenzo0111.bedwars.commands.BedwarsCommand;
import me.lorenzo0111.bedwars.data.SQLHandler;
import me.lorenzo0111.bedwars.game.GameManager;
import me.lorenzo0111.bedwars.hooks.WorldsHook;
import me.lorenzo0111.bedwars.utils.BukkitScheduler;
import me.lorenzo0111.bedwars.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

@Getter
public final class BedwarsPlugin extends JavaPlugin implements BedwarsAPI {
    @Getter private static BedwarsPlugin instance;
    private boolean firstRun = true;
    private BukkitScheduler scheduler;
    private SQLHandler database;
    private GameManager gameManager;

    @Override
    public void onLoad() {
        ConfigurationSerialization.registerClass(TeamConfig.class);
        ConfigurationSerialization.registerClass(GameConfiguration.class);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onEnable() {
        instance = this;

        if (new File(this.getDataFolder(), "config.yml").exists())
            this.firstRun = false;

        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        WorldsHook.init();

        this.scheduler = new BukkitScheduler(this);
        this.database = new SQLHandler(this);
        this.gameManager = new GameManager(this);

        this.reload();
        this.firstRun = false;

        // ******** Commands ********
        this.getCommand("bedwars").setExecutor(new BedwarsCommand(this));


        // ******** Listeners ********


        // ******** Tasks ********

    }


    @Override
    public void onDisable() {
        try {
            this.gameManager.stop();
            this.database.close();
        } catch (Exception e) {
            this.getLogger().log(Level.SEVERE, "An error occurred while disabling the plugin", e);
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
        this.database.close();
        this.log("&c&m---------------------------------------------------");
        this.log("             &c&lBed&f&lWars &7v" + this.getDescription().getVersion());
        this.log(" ");

        if (firstRun) {
            this.log(" &7Thanks for installing the plugin.");
            this.log(" &7We detected that this is the first time you run the plugin.");
            this.log(" &7Please, configure the plugin in the config.yml file.");
            this.log(" &7When you are done, run &c&n/bedwars reload&7 to reload the plugin.");
        } else {
            try {
                this.database.init();
                this.log(" &cDatabase connection&7: &a&lSUCCESS");
                this.gameManager.reload();
                this.log(" &cGame manager&7: &a&lSUCCESS");
            } catch (Exception e) {
                this.log(" &cAn error occurred while loading the plugin.");
                this.log(" &cPlease, check your configuration and try again.");
                this.log(" &cIf the problem persists, contact the developer.");
                this.log(" &cError: " + e.getMessage());
                this.database.close();
            }
        }

        this.log("&c&m---------------------------------------------------");
    }
}
