// Implementación básica de BanManager
import java.util.List;
import java.util.ArrayList;
import java.nio.file.*;

public class BanManager {
    private static final String BANS_FILE = "data/bans.json";
    private List<String> bannedPlayers = new ArrayList<>();
    public BanManager() {
        loadBans();
    }
    public void banPlayer(String player) {
        bannedPlayers.add(player);
        saveBans();
    }
    public boolean isBanned(String player) {
        return bannedPlayers.contains(player);
    }
    private void loadBans() {
        // Código para cargar bans.json
    }
    private void saveBans() {
        // Código para guardar bans.json
    }
}