package com.andrei1058.bedwars.top;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigPath;

public class TopManager {
    
    
    private final @NotNull Map<TopType<? extends Object>, List<TopEntry<?>>> cachedTop = new ConcurrentHashMap<>();
    private @Nullable BukkitTask cacheUpdateTask;
    
    
    public TopManager() {
        this.setupUpdateTask();
    }
    
    
    @SuppressWarnings("unchecked")
    public <T> @NotNull List<TopEntry<T>> getTop(@NotNull TopType<T> topType) {
        Objects.requireNonNull(topType, "topType cannot be null");
        return this.cachedTop.getOrDefault(topType, Collections.emptyList()).stream()
                .map(e -> (TopEntry<T>) e)
                .collect(Collectors.toList());
    }
    
    
    public void updateAll() {
        int size = BedWars.config.getInt(ConfigPath.GENERAL_CONFIGURATION_TOP_SIZE);
        Stream.of(TopType.values())
                .parallel()
                .forEach(topType -> {
                    List<TopEntry<?>> list = new LinkedList<>();
                    BedWars.getRemoteDatabase().fetchTop(topType, size).stream().forEach(list::add);
                    cachedTop.put(topType, list);
                });
    }
    
    
    public void setupUpdateTask() {
        int time = BedWars.config.getInt(ConfigPath.GENERAL_CONFIGURATION_TOP_TIME);
        if (this.cacheUpdateTask != null) this.cacheUpdateTask.cancel();
        this.cacheUpdateTask = Bukkit.getScheduler().runTaskTimerAsynchronously(BedWars.plugin, this::updateAll, 0, time * 20L);
    }
    

}
