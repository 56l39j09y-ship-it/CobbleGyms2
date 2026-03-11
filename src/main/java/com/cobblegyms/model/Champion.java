package com.cobblegyms.model;

public class Champion {
    private final String uuid;
    private final String playerName;
    private final BattleFormat format;

    public Champion(String uuid, String playerName, BattleFormat format) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.format = format;
    }

    public String getUuid() { return uuid; }
    public String getPlayerName() { return playerName; }
    public BattleFormat getFormat() { return format; }

    @Override
    public String toString() {
        return "Champion{" + playerName + ", format=" + format + "}";
    }
}