package com.cobblegyms.model;

public class TempBan {

    private final int id;
    private final String leaderUuid;
    private final String bannedUuid;
    private final String bannedName;
    private final String reason;
    private final String expiresAt;

    public TempBan(int id, String leaderUuid, String bannedUuid, String bannedName,
                   String reason, String expiresAt) {
        this.id = id;
        this.leaderUuid = leaderUuid;
        this.bannedUuid = bannedUuid;
        this.bannedName = bannedName;
        this.reason = reason;
        this.expiresAt = expiresAt;
    }

    public int getId() { return id; }
    public String getLeaderUuid() { return leaderUuid; }
    public String getBannedUuid() { return bannedUuid; }
    public String getBannedName() { return bannedName; }
    public String getReason() { return reason; }
    public String getExpiresAt() { return expiresAt; }

    @Override
    public String toString() {
        return "TempBan{banned='" + bannedName + "', leader='" + leaderUuid + "', expires='" + expiresAt + "'}";
    }
}