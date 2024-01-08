package com.mefrreex.queryplaceholders;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.ConfigSection;
import com.mefrreex.queryplaceholders.placeholder.PlaceholderRegistry;
import com.mefrreex.queryplaceholders.query.BedrockQuery;
import com.mefrreex.queryplaceholders.query.BedrockQueryResponse;
import com.mefrreex.queryplaceholders.query.BedrockQueryFactory;
import com.mefrreex.queryplaceholders.task.UpdateQueryTask;
import com.mefrreex.queryplaceholders.utils.ServerEntry;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class QueryPlaceholders extends PluginBase {

    private static QueryPlaceholders instance;

    @Getter
    private final Map<String, ServerEntry> servers = new HashMap<>();

    private int timeout;
    private int update;

    @Override
    public void onLoad() {
        QueryPlaceholders.instance = this;
        this.saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        this.loadServers();
        this.loadConfig();
        this.scheduleTask();
        BedrockQueryFactory.setBedrockQuery(new BedrockQuery(timeout));
        PlaceholderRegistry.init();
        this.updateServers();
    }

    /**
     * Load servers from config
     */
    private void loadServers() {
        ConfigSection serversSection = this.getConfig().getSection("servers");
        
        serversSection.forEach((name, server) -> {
            if (server instanceof ConfigSection serverSection) {
                var address = serverSection.getString("address");
                var port = serverSection.getInt("port", 19132);
                servers.put(name, new ServerEntry(address, port));
            }
        });
    }

    /**
     * Load config
     */
    private void loadConfig() {
        this.timeout = this.getConfig().getInt("timeout", 2000);
        this.update = this.getConfig().getInt("update", 1200);
    }

    /**
     * Schedule a task to update the servers
     */
    private void scheduleTask() {
        this.getServer().getScheduler().scheduleRepeatingTask(this, 
            new UpdateQueryTask(this), this.getUpdate());
    }

    /**
     * Update query for all servers
     */
    public void updateServers() {
        for (ServerEntry server : servers.values()) {
            BedrockQueryFactory.createOrUpdate(server);
        }
    }
    
    /**
     * Get server address by name
     * @param serverName Server name
     * @return ServerAddress
     */
    public ServerEntry getServer(String serverName) {
        return servers.get(serverName);
    }

    /**
     * Get BedrockQueryResponse server by name
     * @param serverName Server name
     * @return BedrockQueryResponse
     */
    public BedrockQueryResponse getQuery(String serverName) {
        return this.getQuery(this.getServer(serverName));
    }

    /**
     * Get BedrockQueryResponse server by name
     * @param serverEntry Server entry
     * @return BedrockQueryResponse
     */
    public BedrockQueryResponse getQuery(ServerEntry serverEntry) {
        return BedrockQueryFactory.get(serverEntry);
    }

    public static QueryPlaceholders getInstance() {
        return instance;
    }
}
