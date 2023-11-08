package com.mefrreex.queryplaceholders;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.ConfigSection;
import com.mefrreex.queryplaceholders.placeholder.PlaceholderManager;
import com.mefrreex.queryplaceholders.query.BedrockQuery;
import com.mefrreex.queryplaceholders.query.BedrockQueryResponse;
import com.mefrreex.queryplaceholders.query.BedrockQueryFactory;
import com.mefrreex.queryplaceholders.task.UpdateQueryTask;
import com.mefrreex.queryplaceholders.utils.ServerAddress;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class QueryPlaceholders extends PluginBase {

    private static QueryPlaceholders instance;

    private BedrockQuery bedrockQuery;
    private PlaceholderManager placeholderRegistry;

    @Getter
    private final Map<String, ServerAddress> servers = new HashMap<>();

    @Override
    public void onLoad() {
        QueryPlaceholders.instance = this;
        this.saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        this.loadServers();
        this.scheduleTask();
        this.bedrockQuery = new BedrockQuery(this.getTimeout());
        BedrockQueryFactory.setBedrockQuery(bedrockQuery);
        this.updateServers();
        this.placeholderRegistry = new PlaceholderManager(this);
        this.placeholderRegistry.registerPlaceholders();
    }

    private void scheduleTask() {
        this.getServer().getScheduler().scheduleRepeatingTask(this, 
            new UpdateQueryTask(this), this.getUpdate());
    }

    /**
     * Load servers from config
     */
    public void loadServers() {
        ConfigSection serversSection = this.getConfig().getSection("servers");
        
        serversSection.forEach((name, server) -> {
            if (server instanceof ConfigSection serverSection) {
                var address = serverSection.getString("address");
                var port = serverSection.getInt("port", 19132);
                servers.put(name, new ServerAddress(address, port));
            }
        });
    }

    /**
     * Update query for all servers
     */
    public void updateServers() {
        for (ServerAddress server : servers.values()) {
            BedrockQueryFactory.createOrUpdate(server);
        }
    }
    
    /**
     * Get server address by name
     * @param serverName Server name
     * @return ServerAddress
     */
    public ServerAddress getServerAddress(String serverName) {
        return servers.get(serverName);
    }

    /**
     * Get BedrockQueryResponse server by name
     * @param serverName Server name
     * @return BedrockQueryResponse
     */
    public BedrockQueryResponse getQuery(String serverName) {
        return BedrockQueryFactory.get(this.getServerAddress(serverName));
    }

    /**
     * Timeout time for query request
     */
    public int getTimeout() {
        return this.getConfig().getInt("timeout", 2000);
    }

    /**
     * Interval for updating query information
     */
    public int getUpdate() {
        return this.getConfig().getInt("update", 1200);
    }

    public static QueryPlaceholders getInstance() {
        return instance;
    }
}
