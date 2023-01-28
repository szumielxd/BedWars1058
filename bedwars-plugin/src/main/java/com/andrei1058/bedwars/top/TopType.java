package com.andrei1058.bedwars.top;

import static com.andrei1058.bedwars.api.language.Language.getMsg;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.andrei1058.bedwars.api.language.Messages;

public final class TopType<T extends Object> {
    
    
    private static final List<TopType<?>> values = new LinkedList<>();
    
    
    public static final @NotNull TopType<Timestamp> FIRST_PLAYED = new TopType<>("FIRST_PLAYED", Timestamp.class, TopType::formatTimestamp, "first_play");
    public static final @NotNull TopType<Timestamp> LAST_PLAYED = new TopType<>("LAST_PLAYED", Timestamp.class, TopType::formatTimestamp, "last_play");
    public static final @NotNull TopType<Integer> WINS = new TopType<>("WINS", Integer.class, TopType::formatInteger, "wins");
    public static final @NotNull TopType<Integer> KILLS = new TopType<>("KILLS", Integer.class, TopType::formatInteger, "kills");
    public static final @NotNull TopType<Integer> FINAL_KILLS = new TopType<>("FINAL_KILLS", Integer.class, TopType::formatInteger, "final_kills");
    public static final @NotNull TopType<Integer> TOTAL_KILLS = new TopType<>("TOTAL_KILLS", Integer.class, TopType::formatInteger, "kills", "final_kills");
    public static final @NotNull TopType<Integer> LOSSES = new TopType<>("LOSSES", Integer.class, TopType::formatInteger, "looses");
    public static final @NotNull TopType<Integer> DEATHS = new TopType<>("DEATHS", Integer.class, TopType::formatInteger, "deaths");
    public static final @NotNull TopType<Integer> FINAL_DEATHS = new TopType<>("FINAL_DEATHS", Integer.class, TopType::formatInteger, "final_deaths");
    public static final @NotNull TopType<Integer> TOTAL_DEATHS = new TopType<>("TOTAL_DEATHS", Integer.class, TopType::formatInteger, "deaths", "final_deaths");
    public static final @NotNull TopType<Integer> BEDS_DESTROYED = new TopType<>("BEDS_DESTROYED", Integer.class, TopType::formatInteger, "beds_destroyed");
    public static final @NotNull TopType<Integer> GAMES_PLAYED = new TopType<>("GAMES_PLAYED", Integer.class, TopType::formatInteger, "games_played");
    public static final @NotNull TopType<String> LEVEL = new TopType<>("LEVEL", String.class, TopType::formatColoredString, "level");
    
    
    private final @NotNull String name;
    private final @NotNull Class<T> clazz;
    private final BiFunction<Player, T, String> stringifier;
    private final @NotNull String[] internalNames;
    
    private TopType(@NotNull String name, @NotNull Class<T> clazz, BiFunction<Player, T, String> stringifier, @NotNull String... internalNames) {
        this.name = name;
        this.clazz = clazz;
        this.stringifier = stringifier;
        this.internalNames = internalNames;
        values.add(this);
    }
    
    public @NotNull Class<T> getValueClass() {
        return this.clazz;
    }
    
    public @NotNull String[] getInternalNames() {
        return this.internalNames;
    }
    
    public @NotNull String format(@NotNull Player player, T value) {
        return this.stringifier.apply(player, value);
    }
    
    public @NotNull String name() {
        return this.name;
    }
    
    
    public static @NotNull TopType<?>[] values() {
        return values.toArray(new TopType<?>[values.size()]);
    }
    
    public static @NotNull Optional<TopType<?>> valueOf(String name) {
        String val = name.toUpperCase();
        return values.stream().filter(type -> type.name.equals(val)).findAny();
    }
    
    
    private static @NotNull String formatTimestamp(@NotNull Player player, @Nullable Timestamp time) {
        return new SimpleDateFormat(getMsg(player, Messages.FORMATTING_STATS_DATE_FORMAT)).format(time);
    }
    
    private static @NotNull String formatInteger(@NotNull Player player, @Nullable Integer value) {
        return String.valueOf(value);
    }
    
    private static @NotNull String formatColoredString(@NotNull Player player, @Nullable String value) {
        return ChatColor.translateAlternateColorCodes('&', String.valueOf(value));
    }


}
