package com.cobblegyms.data;

import com.cobblegyms.data.repository.*;

/**
 * Punto de acceso central a todos los repositorios DAO.
 * Uso: Repositories.seasons().getActiveSeason()
 */
public final class Repositories {

    private static final SeasonRepository SEASONS = new SeasonRepository();
    private static final GymLeaderRepository GYM_LEADERS = new GymLeaderRepository();
    private static final EliteFourRepository ELITE_FOUR = new EliteFourRepository();
    private static final ChampionRepository CHAMPION = new ChampionRepository();
    private static final PlayerRepository PLAYERS = new PlayerRepository();
    private static final BattleRecordRepository BATTLE_RECORDS = new BattleRecordRepository();
    private static final TempBanRepository TEMP_BANS = new TempBanRepository();
    private static final SmogonBanRepository SMOGON_BANS = new SmogonBanRepository();

    private Repositories() {}

    public static SeasonRepository seasons() { return SEASONS; }
    public static GymLeaderRepository gymLeaders() { return GYM_LEADERS; }
    public static EliteFourRepository eliteFour() { return ELITE_FOUR; }
    public static ChampionRepository champion() { return CHAMPION; }
    public static PlayerRepository players() { return PLAYERS; }
    public static BattleRecordRepository battleRecords() { return BATTLE_RECORDS; }
    public static TempBanRepository tempBans() { return TEMP_BANS; }
    public static SmogonBanRepository smogonBans() { return SMOGON_BANS; }
}