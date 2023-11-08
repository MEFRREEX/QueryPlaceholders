package com.mefrreex.queryplaceholders.query;

public record BedrockQueryResponse(
    boolean online, 
    String motd, 
    int protocolVersion, 
    String minecraftVersion, 
    int playerCount, 
    int maxPlayers, 
    String software, 
    String gamemode
) {

    private static final BedrockQueryResponse EMPTY = new BedrockQueryResponse(false, "", -1, "", 0, 0, "", "");

    public static BedrockQueryResponse empty() {
        return EMPTY;
    }
}
