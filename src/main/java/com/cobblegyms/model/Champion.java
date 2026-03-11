package com.cobblegyms.model;

public class Champion {

    private String uuid;
    private String playerName;
    private BattleFormat battleFormat;

    public Champion(String uuid, String playerName, BattleFormat battleFormat) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.battleFormat = battleFormat;
    }

    public boolean isEmpty() { return uuid == null || uuid.isBlank(); }

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }
    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }
    public BattleFormat getBattleFormat() { return battleFormat; }
    public void setBattleFormat(BattleFormat f) { this.battleFormat = f; }

    @Override
    public String toString() {
        return isEmpty() ? "Champion{vacant}" : "Champion{uuid='" + uuid + "', name='" + playerName + "', format=" + battleFormat + "}";
    }
}