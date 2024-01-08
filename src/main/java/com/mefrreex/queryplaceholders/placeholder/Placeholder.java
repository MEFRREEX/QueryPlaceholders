package com.mefrreex.queryplaceholders.placeholder;

import cn.nukkit.Player;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderParameters.Parameter;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class Placeholder {
    
    private final String name;

    public Placeholder(String name) {
        this.name = name;
    }

    /**
     * This method is called when the placeholder is updated
     * @param parameters List<Parameter>
     * @return The string that the placeholder returns
     */
    public abstract String onUpdate(Player player, List<Parameter> parameters);
}