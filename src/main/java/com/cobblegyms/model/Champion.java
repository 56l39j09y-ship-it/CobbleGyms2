public class Champion {
    public String uuid;
    public String name;
    public int wins;
    public int losses;
    public Champion(String uuid, String name, int wins, int losses) {
        this.uuid = uuid;
        this.name = name;
        this.wins = wins;
        this.losses = losses;
    }
}