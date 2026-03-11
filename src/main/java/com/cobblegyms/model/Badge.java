package com.cobblegyms.model;

public class Badge {

    private final String playerUuid;
    private final int seasonId;
    private final GymType type;
    private final String earnedDate;

    public Badge(String playerUuid, int seasonId, GymType type, String earnedDate) {
        this.playerUuid = playerUuid;
        this.seasonId = seasonId;
        this.type = type;
        this.earnedDate = earnedDate;
    }

    public String getPlayerUuid() { return playerUuid; }
    public int getSeasonId() { return seasonId; }
    public GymType getType() { return type; }
    public String getEarnedDate() { return earnedDate; }

    @Override
    public String toString() {
        return "Badge{player='" + playerUuid + "', season=" + seasonId + ", type=" + type + ", date='" + earnedDate + "'}";
    }
}