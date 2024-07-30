package me.lorenzo0111.bedwars.tasks;

import me.lorenzo0111.bedwars.BedwarsPlugin;
import org.bukkit.Bukkit;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class BukkitScheduler {
    private final BedwarsPlugin plugin;
    private final Executor executor;

    public BukkitScheduler(BedwarsPlugin plugin) {
        this.plugin = plugin;
        this.executor = (cmd) -> Bukkit.getScheduler().runTaskAsynchronously(plugin, cmd);
    }

    public CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, executor);
    }

    public <T> CompletableFuture<T> runAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

    public CompletableFuture<Void> runSync(Runnable runnable) {
        return CompletableFuture.runAsync(() -> Bukkit.getScheduler().runTask(plugin, runnable));
    }

    public <T> Future<T> runSync(Callable<T> supplier) {
        return Bukkit.getScheduler().callSyncMethod(plugin, supplier);
    }

    public CompletableFuture<Void> ensureSync(Runnable runnable) {
        if (Bukkit.isPrimaryThread()) runnable.run();
        else return runSync(runnable);

        return CompletableFuture.completedFuture(null);
    }

    public void runLater(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
    }


}
