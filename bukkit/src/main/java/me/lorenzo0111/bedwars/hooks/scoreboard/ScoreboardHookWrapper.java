package me.lorenzo0111.bedwars.hooks.scoreboard;

import lombok.Getter;
import lombok.Setter;
import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.scoreboard.ScoreboardHook;
import me.lorenzo0111.bedwars.hooks.scoreboard.natives.NativeScoreboardHook;

public class ScoreboardHookWrapper {
    @Getter
    @Setter
    private static ScoreboardHook hook;

    public static void init() {
        unload();

        hook = new NativeScoreboardHook();

        BedwarsPlugin.getInstance().log("Using scoreboard hook: &c" + hook.getId());
    }

    public static void unload() {
        if (hook != null) hook.unload();
    }
}
