package com.andrei1058.bedwars.top;

import java.util.UUID;

import org.jetbrains.annotations.NotNull;

public class TopEntry<T> {
    
    private final @NotNull UUID uniqueId;
    private final @NotNull String name;
    private final @NotNull T value;
    
    public TopEntry(@NotNull UUID uniqueId, @NotNull String name, @NotNull T value) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.value = value;
    }
    
    
    public @NotNull UUID getUniqueId() {
        return this.uniqueId;
    }
    
    public @NotNull String getName() {
        return this.name;
    }
    
    public @NotNull T getValue() {
        return this.value;
    }

}
