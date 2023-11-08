package com.mefrreex.queryplaceholders.query;

import com.mefrreex.queryplaceholders.utils.ServerAddress;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class BedrockQueryFactory {
    
    private static BedrockQuery query;

    private static final Map<ServerAddress, BedrockQueryResponse> queryMap = new HashMap<>();

    /**
     * Set BedrockQuery
     */
    public static void setBedrockQuery(@NonNull BedrockQuery query) {
        BedrockQueryFactory.query = query;
    }

    /**
     * Get the stored BedrockQueryResponse for the server address
     * @param server ServerAddress
     * @return BedrockQueryResponse
     */
    public static BedrockQueryResponse get(@NonNull ServerAddress server) {
        return queryMap.getOrDefault(server, BedrockQueryResponse.empty());
    }

    /**
     * Create or update a BedrockQueryResponse for the server address
     * @param server ServerAddress
     */
    public static void createOrUpdate(@NonNull ServerAddress server) {
        query.create(server.address(), server.port(), response -> {
            queryMap.put(server, response);
        });
    }
}
