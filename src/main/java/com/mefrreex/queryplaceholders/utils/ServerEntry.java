package com.mefrreex.queryplaceholders.utils;

public record ServerEntry(String address, int port) {

    @Override
    public final String toString() {
        return address + ":" + port;
    }
}
