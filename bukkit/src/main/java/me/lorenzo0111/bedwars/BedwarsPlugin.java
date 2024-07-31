package me.lorenzo0111.bedwars;

import lombok.Getter;
import lombok.Setter;
import me.lorenzo0111.bedwars.api.BedwarsAPI;
import me.lorenzo0111.bedwars.api.game.TeamAssigner;
import me.lorenzo0111.bedwars.api.game.config.ConfigLocation;
import me.lorenzo0111.bedwars.api.game.config.GameConfiguration;
import me.lorenzo0111.bedwars.api.game.config.TeamConfig;
import me.lorenzo0111.bedwars.api.hologram.HologramHook;
import me.lorenzo0111.bedwars.api.scoreboard.ScoreboardHook;
import me.lorenzo0111.bedwars.commands.BedwarsCommand;
import me.lorenzo0111.bedwars.data.SQLHandler;
import me.lorenzo0111.bedwars.game.GameManager;
import me.lorenzo0111.bedwars.game.setup.SetupManager;
import me.lorenzo0111.bedwars.game.assign.RandomTeamAssigner;
import me.lorenzo0111.bedwars.hooks.hologram.HologramHookWrapper;
import me.lorenzo0111.bedwars.hooks.WorldsHook;
import me.lorenzo0111.bedwars.hooks.scoreboard.ScoreboardHookWrapper;
import me.lorenzo0111.bedwars.listeners.GameListener;
import me.lorenzo0111.bedwars.listeners.LobbyListener;
import me.lorenzo0111.bedwars.tasks.BukkitScheduler;
import me.lorenzo0111.bedwars.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

@Getter
public final class BedwarsPlugin extends JavaPlugin implements BedwarsAPI {
    @Getter private static BedwarsPlugin instance;
    private boolean firstRun = true;
    private YamlConfiguration messages;
    private BukkitScheduler scheduler;
    private SQLHandler database;
    private GameManager gameManager;
    private SetupManager setupManager;
    @Setter private TeamAssigner teamAssigner;

    @Override
    public void onLoad() {
        ConfigurationSerialization.registerClass(ConfigLocation.class);
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

        if (!new File(this.getDataFolder(), "messages.yml").exists()) {
            this.saveResource("messages.yml", false);
        }

        this.scheduler = new BukkitScheduler(this);
        this.database = new SQLHandler(this);
        this.teamAssigner = new RandomTeamAssigner();
        this.gameManager = new GameManager(this);
        this.setupManager = new SetupManager(this);

        this.reload();
        this.firstRun = false;

        // ******** Commands ********
        this.getCommand("bedwars").setExecutor(new BedwarsCommand(this));

        // ******** Listeners ********
        this.getServer().getPluginManager().registerEvents(new GameListener(this), this);
        this.getServer().getPluginManager().registerEvents(new LobbyListener(this), this);

        // ******** Tasks ********

    }


    @Override
    public void onDisable() {
        try {
            HologramHookWrapper.unload();
            ScoreboardHookWrapper.unload();

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

    public String getMessage(String path, boolean messagesFile) {
        String message = messagesFile ?
                this.messages.getString(path, "&cUnable to find the following key: &7" + path + "&c.") :
                this.getConfig().getString(path, "&cUnable to find the following key: &7" + path + "&c.");

        return StringUtils.color(message);
    }

    public List<String> getMessages(String path, boolean messagesFile) {
        List<String> messages = messagesFile ?
                this.messages.getStringList(path) :
                this.getConfig().getStringList(path);

        return StringUtils.color(messages);
    }

    public String getMessage(String path) {
        return getMessage(path, true);
    }

    public List<String> getMessages(String path) {
        return getMessages(path, true);
    }

    public String getPrefixed(String path) {
        return getMessage("prefix") + getMessage(path);
    }

    public void reload() {
        this.reloadConfig();
        this.messages = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "messages.yml"));

        WorldsHook.init();
        HologramHookWrapper.init();
        ScoreboardHookWrapper.init();

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

    @Override
    public void setHologramHook(HologramHook hook) {
        HologramHookWrapper.setHook(hook);
    }

    @Override
    public void setScoreboardHook(ScoreboardHook hook) {
        ScoreboardHookWrapper.setHook(hook);
    }
}
