package me.lorenzo0111.bedwars.tasks;

import lombok.Getter;
import me.lorenzo0111.bedwars.BedwarsPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class Countdown extends BukkitRunnable {
    @Getter
    private int seconds;
    private final @Nullable Consumer<Integer> onTick;
    private final @Nullable Runnable onFinish;

    public Countdown(int seconds, @Nullable Consumer<Integer> onTick, @Nullable Runnable onFinish) {
        this.seconds = seconds;
        this.onTick = onTick;
        this.onFinish = onFinish;
    }

    @Override
    public void run() {
        if (onTick != null) onTick.accept(seconds);

        if (seconds == 0) {
            if (onFinish != null) onFinish.run();
            cancel();
            return;
        }

        seconds--;
    }

    public Countdown start() {
        this.runTaskTimerAsynchronously(BedwarsPlugin.getInstance(), 0, 20);
        return this;
    }

}
