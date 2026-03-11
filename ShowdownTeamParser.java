import java.util.*;
public class ShowdownTeamParser {
    public static List<PokemonData> parse(String paste) {
        List<PokemonData> team = new ArrayList<>();
        String[] blocks = paste.split("\n\n");
        for (String block : blocks) {
            String[] lines = block.split("\n");
            String species = lines[0].split(" ")[0].replaceAll("[@]|\(([^)]*)\)", "");
            int level = 50;
            for (String line : lines) {
                if (line.startsWith("Level: ")) {
                    level = Integer.parseInt(line.replace("Level: ", "").trim());
                }
            }
            team.add(new PokemonData(species, level));
        }
        return team;
    }
}

// Example usage in TeamManager
// List<PokemonData> importedTeam = ShowdownTeamParser.parse(pasteString);