// Implementación básica de GymManager
import java.util.List;
import java.util.ArrayList;
import java.nio.file.*;
import com.google.gson.*;

public class GymManager {
    private static final String GYMS_FILE = "data/gyms.json";
    private List<Gym> gyms = new ArrayList<>();

    public GymManager() {
        loadGyms();
    }
    public void addGym(Gym gym) {
        gyms.add(gym);
        saveGyms();
    }
    public List<Gym> getAllGyms() {
        return gyms;
    }
    private void loadGyms() {
        // Código para cargar gyms.json
    }
    private void saveGyms() {
        // Código para guardar gyms.json
    }
}