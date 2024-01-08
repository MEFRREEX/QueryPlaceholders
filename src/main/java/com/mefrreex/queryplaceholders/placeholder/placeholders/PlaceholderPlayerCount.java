package com.mefrreex.queryplaceholders.placeholder.placeholders;

import cn.nukkit.Player;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderParameters.Parameter;
import com.mefrreex.queryplaceholders.QueryPlaceholders;
import com.mefrreex.queryplaceholders.placeholder.Placeholder;

import java.util.List;

public class PlaceholderPlayerCount extends Placeholder {

    public PlaceholderPlayerCount() {
        super("query_player_count");
    }

    @Override
    public String onUpdate(Player player, List<Parameter> parameters) {
        // You can specify multiple servers for online summation
        // Example: %query_player_count<zeqa;nethergames>%
        QueryPlaceholders queryPlaceholders = QueryPlaceholders.getInstance();

        int online = parameters.stream()
                .map(parameter -> queryPlaceholders.getQuery(parameter.getValue()).playerCount())
                .reduce(0, Integer::sum);

        return String.valueOf(online);
    }
}
