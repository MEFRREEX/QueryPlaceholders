package com.mefrreex.queryplaceholders.placeholder;

import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderParameters.Parameter;

public class Placeholder {
    
    private static final PlaceholderAPI placeholderApi = PlaceholderAPI.getInstance();

    private final String name;
    private String subname = "query_";

    private PlaceholderSupplier supplier = (name) -> "";

    public Placeholder(String name) {
        this.name = name;
    }

    /**
     * Create a new Placeholder
     * @param name Placeholder name
     * @return Placeholder
     */
    public static Placeholder create(String name) {
        return new Placeholder(name);
    }

    /**
     * Sub placeholder name. Example: "query_"
     * @param subname Sub name
     * @return Placeholder
     */
    public Placeholder subname(String subname) {
        this.subname = subname;
        return this;
    }

    /**
     * Placeholder supplier
     * @param supplier PlaceholderSupplier
     * @return Placeholder
     */
    public Placeholder supplier(PlaceholderSupplier supplier) {
        this.supplier = supplier;
        return this;
    }

    /**
     * Register a placeholder
     */
    public void register() {
        placeholderApi.builder(subname + name, String.class)
            .processParameters(true)
            .visitorLoader(entry -> {
                Parameter parameter = entry.getParameters().single();
                if (parameter != null) {
                    String name = parameter.getValue();
                    return String.valueOf(supplier.handle(name));
                }
                return "";
            })
            .build();
    }
}

