package com.mefrreex.queryplaceholders.placeholder.placeholders;

import cn.nukkit.Player;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderParameters.Parameter;
import com.mefrreex.queryplaceholders.QueryPlaceholders;
import com.mefrreex.queryplaceholders.placeholder.Placeholder;

import java.util.List;

public class PlaceholderVersion extends Placeholder {

    public PlaceholderVersion() {
        super("query_minecraft_version");
    }

    @Override
    public String onUpdate(Player player, List<Parameter> parameters) {
        return QueryPlaceholders.getInstance().getQuery(parameters.get(0).getValue()).minecraftVersion();
    }
}
