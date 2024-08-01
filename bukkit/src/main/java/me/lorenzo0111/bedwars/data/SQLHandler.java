package me.lorenzo0111.bedwars.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.data.PlayerStats;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.*;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class SQLHandler {
    private final BedwarsPlugin plugin;
    private final Executor executor;
    private HikariDataSource dataSource;

    public SQLHandler(BedwarsPlugin plugin) {
        this.plugin = plugin;
        this.executor = plugin.getScheduler()::runAsync;
    }

    public void init() throws SQLException {
        ConfigurationSection cs = this.plugin.getConfig().getConfigurationSection("mysql");
        Objects.requireNonNull(cs, "Unable to find the following key: mysql");
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://" + cs.getString("host") + ":" + cs.getString("port") + "/" + cs.getString("database"));
        config.setUsername(cs.getString("username"));
        config.setPassword(cs.getString("password"));
        config.setConnectionTimeout(10000);
        config.setLeakDetectionThreshold(10000);
        config.setMaximumPoolSize(10);
        config.setMaxLifetime(60000);
        config.setPoolName("BedwarsPool");
        config.addDataSourceProperty("useSSL", cs.getBoolean("ssl"));

        this.dataSource = new HikariDataSource(config);
        this.createTables();
    }

    private void createTables() throws SQLException {
        Connection connection = this.getConnection();

        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS players (" +
                "uuid VARCHAR(36) PRIMARY KEY," +
                "name VARCHAR(16) NOT NULL," +
                "kills INT NOT NULL DEFAULT 0," +
                "deaths INT NOT NULL DEFAULT 0," +
                "wins INT NOT NULL DEFAULT 0," +
                "losses INT NOT NULL DEFAULT 0," +
                "beds INT NOT NULL DEFAULT 0," +
                "games INT NOT NULL DEFAULT 0);"
        );

        connection.close();
    }

    public CompletableFuture<PlayerStats> getStats(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = this.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM players WHERE uuid = ?;"
                );

                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    return new PlayerStats(
                            uuid,
                            result.getString("name"),
                            result.getInt("kills"),
                            result.getInt("deaths"),
                            result.getInt("wins"),
                            result.getInt("losses"),
                            result.getInt("beds"),
                            result.getInt("games")
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }, this.executor);
    }

    public void updateStats(PlayerStats stats) {
        CompletableFuture.runAsync(() -> {
            try (Connection connection = this.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO players (uuid, name, kills, deaths, wins, losses, beds, games) VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                                "ON DUPLICATE KEY UPDATE " +
                                "name = VALUES(name), " +
                                "kills = VALUES(kills), " +
                                "deaths = VALUES(deaths), " +
                                "wins = VALUES(wins), " +
                                "losses = VALUES(losses), " +
                                "beds = VALUES(beds), " +
                                "games = VALUES(games);"
                );

                statement.setString(1, stats.getUuid().toString());
                statement.setString(2, stats.getName());
                statement.setInt(3, stats.getKills());
                statement.setInt(4, stats.getDeaths());
                statement.setInt(5, stats.getWins());
                statement.setInt(6, stats.getLosses());
                statement.setInt(7, stats.getBeds());
                statement.setInt(8, stats.getGames());

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, this.executor);
    }

    public void close() {
        try {
            if (this.isOpen()) {
                this.dataSource.close();
            }
        } catch (Exception ignored) {}

        this.dataSource = null;
    }

    public boolean isOpen() {
        return this.dataSource != null;
    }

    private Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        if (connection == null) {
            throw new SQLException("Unable to get a connection from the pool.");
        }

        return connection;
    }
}
