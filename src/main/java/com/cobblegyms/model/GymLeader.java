package com.cobblegyms.model;

public class GymLeader {

    private final String uuid;
    private final String playerName;
    private final GymType type;
    private BattleFormat battleFormat;
    private boolean open;
    private String extraBan;
    private Integer extraBanSeason;

    public GymLeader(String uuid, String playerName, GymType type, BattleFormat battleFormat,
                     boolean open, String extraBan, Integer extraBanSeason) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.type = type;
        this.battleFormat = battleFormat;
        this.open = open;
        this.extraBan = extraBan;
        this.extraBanSeason = extraBanSeason;
    }

    public String getUuid() { return uuid; }
    public String getPlayerName() { return playerName; }
    public GymType getType() { return type; }
    public BattleFormat getBattleFormat() { return battleFormat; }
    public void setBattleFormat(BattleFormat f) { this.battleFormat = f; }
    public boolean isOpen() { return open; }
    public void setOpen(boolean open) { this.open = open; }
    public String getExtraBan() { return extraBan; }
    public void setExtraBan(String extraBan) { this.extraBan = extraBan; }
    public Integer getExtraBanSeason() { return extraBanSeason; }
    public void setExtraBanSeason(Integer s) { this.extraBanSeason = s; }

    @Override
    public String toString() {
        return "GymLeader{uuid='" + uuid + "', name='" + playerName + "', type=" + type + ", format=" + battleFormat + ", open=" + open + "}";
    }
}