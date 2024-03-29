package com.mefrreex.queryplaceholders.query;

import com.mefrreex.queryplaceholders.utils.ServerEntry;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class BedrockQueryFactory {
    
    @Getter
    private static BedrockQuery bedrockQuery;

    private static final Map<ServerEntry, BedrockQueryResponse> queryMap = new HashMap<>();

    /**
     * Set BedrockQuery
     */
    public static void setBedrockQuery(@NonNull BedrockQuery bedrockQuery) {
        BedrockQueryFactory.bedrockQuery = bedrockQuery;
    }

    /**
     * Get the stored BedrockQueryResponse for the server address
     * @param server ServerAddress
     * @return BedrockQueryResponse
     */
    public static BedrockQueryResponse get(@NonNull ServerEntry server) {
        return queryMap.getOrDefault(server, BedrockQueryResponse.empty());
    }

    /**
     * Create or update a BedrockQueryResponse for the server address
     * @param server ServerAddress
     */
    public static void createOrUpdate(@NonNull ServerEntry server) {
        bedrockQuery.create(server.address(), server.port(), response -> {
            queryMap.put(server, response);
        });
    }
}
