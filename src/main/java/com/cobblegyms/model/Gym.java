public class Gym {
    public String gymId;
    public String type;
    public String leaderUuid;
    public boolean isE4;
    public String secondaryType; // Sólo para E4, puede ser null

    public Gym(String gymId, String type, String leaderUuid, boolean isE4, String secondaryType) {
        this.gymId = gymId;
        this.type = type;
        this.leaderUuid = leaderUuid;
        this.isE4 = isE4;
        this.secondaryType = secondaryType;
    }
}