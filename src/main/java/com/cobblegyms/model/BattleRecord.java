package com.cobblegyms.model;

public class BattleRecord {
    private final int id;
    private final int seasonId;
    private final String leaderUuid;
    private final String challengerUuid;
    private final String challengerName;
    private final BattleResult result;
    private final String battleDate;
    private final String battleType;
    private final BattleFormat format;

    public BattleRecord(int id, int seasonId, String leaderUuid, String challengerUuid,
                        String challengerName, BattleResult result, String battleDate,
                        String battleType, BattleFormat format) {
        this.id = id;
        this.seasonId = seasonId;
        this.leaderUuid = leaderUuid;
        this.challengerUuid = challengerUuid;
        this.challengerName = challengerName;
        this.result = result;
        this.battleDate = battleDate;
        this.battleType = battleType;
        this.format = format;
    }

    public int getId() { return id; }
    public int getSeasonId() { return seasonId; }
    public String getLeaderUuid() { return leaderUuid; }
    public String getChallengerUuid() { return challengerUuid; }
    public String getChallengerName() { return challengerName; }
    public BattleResult getResult() { return result; }
    public String getBattleDate() { return battleDate; }
    public String getBattleType() { return battleType; }
    public BattleFormat getFormat() { return format; }
}