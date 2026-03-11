package com.cobblegyms.model;

public class E4Member {

    private final String uuid;
    private final String playerName;
    private final GymType type1;
    private final GymType type2;
    private BattleFormat battleFormat;
    private int orderIndex;

    public E4Member(String uuid, String playerName, GymType type1, GymType type2,
                    BattleFormat battleFormat, int orderIndex) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.type1 = type1;
        this.type2 = type2;
        this.battleFormat = battleFormat;
        this.orderIndex = orderIndex;
    }

    public String getUuid() { return uuid; }
    public String getPlayerName() { return playerName; }
    public GymType getType1() { return type1; }
    public GymType getType2() { return type2; }
    public BattleFormat getBattleFormat() { return battleFormat; }
    public void setBattleFormat(BattleFormat f) { this.battleFormat = f; }
    public int getOrderIndex() { return orderIndex; }
    public void setOrderIndex(int orderIndex) { this.orderIndex = orderIndex; }

    @Override
    public String toString() {
        return "E4Member{uuid='" + uuid + "', name='" + playerName + "', type1=" + type1 + ", type2=" + type2 + ", order=" + orderIndex + "}";
    }
}