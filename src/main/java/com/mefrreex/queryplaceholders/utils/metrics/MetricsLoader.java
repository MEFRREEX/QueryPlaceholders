package com.mefrreex.queryplaceholders.utils.metrics;

import com.mefrreex.queryplaceholders.QueryPlaceholders;
import com.mefrreex.queryplaceholders.utils.metrics.Metrics.AdvancedPie;
import com.mefrreex.queryplaceholders.utils.metrics.Metrics.SimplePie;

import java.util.HashMap;
import java.util.Map;

public class MetricsLoader {
    
    private final QueryPlaceholders main;
    private final Metrics metrics;

    private final int pluginId = 20722;

    public MetricsLoader() {
        this.main = QueryPlaceholders.getInstance();
        this.metrics = new Metrics(main, pluginId);
    }

    public void addCustomMetrics() {
        metrics.addCustomChart(new AdvancedPie("query_servers", () -> {
            Map<String, Integer> servers = new HashMap<>();
            
            main.getServers().forEach((name, server) -> {
                servers.put(server.toString(), 1);
            });
            
            return servers;
        }));
        metrics.addCustomChart(new SimplePie("nukkit_version", () -> main.getServer().getNukkitVersion()));
        metrics.addCustomChart(new SimplePie("xbox_auth", () -> main.getServer().getPropertyBoolean("xbox-auth") ? "Required" : "Not required"));
    }
}
