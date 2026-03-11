import java.util.*;
import java.io.*;
import com.google.gson.*;

public class TeamManager {
    private final Map<String, List<Team>> leaderTeamsMap = new HashMap<>(); // UUID as String
    private static final int MAX_TEAMS = 3;
    private static final String TEAMS_FILE = "data/teams.json";

    public void addTeam(String leaderUuid, Team team) {
        leaderTeamsMap.putIfAbsent(leaderUuid, new ArrayList<>());
        List<Team> teams = leaderTeamsMap.get(leaderUuid);
        if (teams.size() < MAX_TEAMS) {
            teams.add(team);
            saveTeams();
        }
    }

    public List<Team> getTeams(String leaderUuid) {
        return leaderTeamsMap.getOrDefault(leaderUuid, new ArrayList<>());
    }

    public void backupTeams() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(TEAMS_FILE)) {
            gson.toJson(leaderTeamsMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restoreTeams() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(TEAMS_FILE)) {
            // TypeToken for Map<String, List<Team>>
            leaderTeamsMap.clear();
            Map<String, List<Team>> restored = gson.fromJson(reader, new com.google.gson.reflect.TypeToken<Map<String, List<Team>>>(){}.getType());
            if (restored != null) leaderTeamsMap.putAll(restored);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}