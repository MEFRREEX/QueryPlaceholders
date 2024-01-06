package com.mefrreex.queryplaceholders.placeholder;

import com.mefrreex.queryplaceholders.QueryPlaceholders;

public class PlaceholderRegistry {

    /**
     * Register all query placeholders
     */
    public static void init(QueryPlaceholders main) {
        register("player_count", name -> main.getQuery(name).playerCount()); // %query_player_count<server_name>%
        register("max_players", name -> main.getQuery(name).maxPlayers()); // %query_max_players<server_name>%
        register("motd", name -> main.getQuery(name).motd()); // %query_motd<server_name>%
        register("minecraft_version", name -> main.getQuery(name).minecraftVersion()); // %query_minecraft_version<server_name>%
    }

    /**
     * Register query placeholder
     * @param name     Placeholder name
     * @param supplier PlaceholderSupplier
     */
    public static void register(String name, PlaceholderSupplier supplier) {
        Placeholder.create(name)
            .supplier(supplier)
            .register();
    }
}
