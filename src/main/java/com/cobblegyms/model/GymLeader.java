package com.cobblegyms.model;

public class GymLeader {
    private final String uuid;
    private final String playerName;
    private final GymType type;
    private final BattleFormat format;
    private final boolean open;
    private final String extraBan;
    private final Integer extraBanSeason;

    public GymLeader(String uuid, String playerName, GymType type, BattleFormat format,
                     boolean open, String extraBan, Integer extraBanSeason) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.type = type;
        this.format = format;
        this.open = open;
        this.extraBan = extraBan;
        this.extraBanSeason = extraBanSeason;
    }

    public String getUuid() { return uuid; }
    public String getPlayerName() { return playerName; }
    public GymType getType() { return type; }
    public BattleFormat getFormat() { return format; }
    public boolean isOpen() { return open; }
    public String getExtraBan() { return extraBan; }
    public Integer getExtraBanSeason() { return extraBanSeason; }

    @Override
    public String toString() {
        return "GymLeader{" + playerName + ", type=" + type + ", format=" + format + ", open=" + open + "}";
    }
}