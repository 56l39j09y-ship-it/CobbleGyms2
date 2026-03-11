public class GymLeader {
    public String uuid;
    public String name;
    public String gymType;
    public int wins;
    public int losses;
    public GymLeader(String uuid, String name, String gymType, int wins, int losses) {
        this.uuid = uuid;
        this.name = name;
        this.gymType = gymType;
        this.wins = wins;
        this.losses = losses;
    }
}