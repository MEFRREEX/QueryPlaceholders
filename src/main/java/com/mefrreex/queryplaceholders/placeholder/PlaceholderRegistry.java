package com.mefrreex.queryplaceholders.placeholder;

import cn.nukkit.Player;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderParameters.Parameter;
import com.mefrreex.queryplaceholders.placeholder.placeholders.PlaceholderMaxPlayers;
import com.mefrreex.queryplaceholders.placeholder.placeholders.PlaceholderMotd;
import com.mefrreex.queryplaceholders.placeholder.placeholders.PlaceholderPlayerCount;
import com.mefrreex.queryplaceholders.placeholder.placeholders.PlaceholderVersion;

import java.util.List;

public class PlaceholderRegistry {
    
    private static final PlaceholderAPI placeholderApi = PlaceholderAPI.getInstance();

    /**
     * Register all placeholders
     */
    public static void init() {
        register(new PlaceholderPlayerCount());
        register(new PlaceholderMaxPlayers());
        register(new PlaceholderMotd());
        register(new PlaceholderVersion());
    }

    /**
     * Register placeholder
     * @param placeholder Placeholder
     */
    public static void register(Placeholder placeholder) {
        placeholderApi.builder(placeholder.getName(), String.class)
            .processParameters(true)
            .visitorLoader(entry -> {

                Player player = entry.getPlayer();
                List<Parameter> parameters = entry.getParameters().getUnnamed();

                try {
                    return placeholder.onUpdate(player, parameters);
                } catch(Exception e) {
                    throw new RuntimeException("An error occurred when updating the placeholder", e);
                }
            })
            .build();
    }
}
