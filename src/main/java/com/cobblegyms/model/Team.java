public class Team {
    public String teamId;
    public String leaderUuid;
    public String name;
    public List<PokemonData> pokemons;

    public Team(String teamId, String leaderUuid, String name, List<PokemonData> pokemons) {
        this.teamId = teamId;
        this.leaderUuid = leaderUuid;
        this.name = name;
        this.pokemons = pokemons;
    }
}