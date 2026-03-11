package com.cobblegyms.model;

public enum BattleFormat {
    SINGLES("Singles"),
    DOUBLES("Doubles");

    private final String displayName;

    BattleFormat(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }

    public static BattleFormat fromString(String s) {
        for (BattleFormat f : values()) {
            if (f.name().equalsIgnoreCase(s) || f.displayName.equalsIgnoreCase(s)) return f;
        }
        return SINGLES;
    }
}
