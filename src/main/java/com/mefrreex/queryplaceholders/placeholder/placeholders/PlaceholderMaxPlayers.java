package com.mefrreex.queryplaceholders.placeholder.placeholders;

import cn.nukkit.Player;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderParameters.Parameter;
import com.mefrreex.queryplaceholders.QueryPlaceholders;
import com.mefrreex.queryplaceholders.placeholder.Placeholder;

import java.util.List;

public class PlaceholderMaxPlayers extends Placeholder {

    public PlaceholderMaxPlayers() {
        super("query_max_players");
    }

    @Override
    public String onUpdate(Player player, List<Parameter> parameters) {
        return String.valueOf(QueryPlaceholders.getInstance().getQuery(parameters.get(0).getValue()).maxPlayers());
    }
}
