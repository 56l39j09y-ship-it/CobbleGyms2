package com.cobblegyms.model;

public class EliteFourMember {
    private final String uuid;
    private final String playerName;
    private final GymType type1;
    private final GymType type2;
    private final BattleFormat format;
    private final int orderIndex;

    public EliteFourMember(String uuid, String playerName, GymType type1, GymType type2,
                           BattleFormat format, int orderIndex) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.type1 = type1;
        this.type2 = type2;
        this.format = format;
        this.orderIndex = orderIndex;
    }

    public String getUuid() { return uuid; }
    public String getPlayerName() { return playerName; }
    public GymType getType1() { return type1; }
    public GymType getType2() { return type2; }
    public BattleFormat getFormat() { return format; }
    public int getOrderIndex() { return orderIndex; }

    @Override
    public String toString() {
        return "EliteFourMember{" + playerName + ", types=" + type1 + "/" + type2 + ", order=" + orderIndex + "}";
    }
}