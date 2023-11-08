package com.mefrreex.queryplaceholders.placeholder;

import com.mefrreex.queryplaceholders.QueryPlaceholders;

public class PlaceholderManager {

    private final QueryPlaceholders main;

    public PlaceholderManager(QueryPlaceholders main) {
        this.main = main;
    }

    /**
     * Register all query placeholders
     */
    public void registerPlaceholders() {
        registerPlaceholder("player_count", name -> main.getQuery(name).playerCount());
        registerPlaceholder("max_players", name -> main.getQuery(name).maxPlayers());
        registerPlaceholder("motd", name -> main.getQuery(name).motd());
        registerPlaceholder("minecraft_version", name -> main.getQuery(name).minecraftVersion());
    }

    /**
     * Register query placeholder
     * @param name     Placeholder name
     * @param supplier PlaceholderSupplier
     */
    public void registerPlaceholder(String name, PlaceholderSupplier supplier) {
        Placeholder.create(name)
            .supplier(supplier)
            .register();
    }
}
