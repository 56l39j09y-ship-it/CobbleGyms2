public class Badge {
    public String badgeId;
    public String gymLeaderUuid;
    public String seasonId;
    public String awardedToUuid;
    public Badge(String badgeId, String gymLeaderUuid, String seasonId, String awardedToUuid) {
        this.badgeId = badgeId;
        this.gymLeaderUuid = gymLeaderUuid;
        this.seasonId = seasonId;
        this.awardedToUuid = awardedToUuid;
    }
}