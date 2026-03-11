// Implementación básica de TeamManager
import java.util.HashMap;
import java.nio.file.*;

public class TeamManager {
    private static final String TEAMS_FILE = "data/teams.json";
    private HashMap<String, String> teams = new HashMap<>();
    public TeamManager() {
        loadTeams();
    }
    public void setTeam(String role, String teamConfig) {
        teams.put(role, teamConfig);
        saveTeams();
    }
    public String getTeam(String role) {
        return teams.get(role);
    }
    private void loadTeams() {
        // Código para cargar teams.json
    }
    private void saveTeams() {
        // Código para guardar teams.json
    }
}