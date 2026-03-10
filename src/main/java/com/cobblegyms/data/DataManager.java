package com.cobblegyms.data;

import com.cobblegyms.CobbleGyms;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;

import java.io.IOException;
import java.nio.file.*;
import java.sql.*;

public class DataManager {

    private final MinecraftServer server;
    private Connection connection;

    public DataManager(MinecraftServer server) {
        this.server = server;
    }

    public void load() {
        try {
            Path dbPath = server.getSavePath(WorldSavePath.ROOT)
                    .resolve(CobbleGyms.getConfig().databaseFile);
            Files.createDirectories(dbPath.getParent());

            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath.toAbsolutePath());
            CobbleGyms.LOGGER.info("Database connected: {}", dbPath);
            initializeTables();
        } catch (SQLException | IOException e) {
            CobbleGyms.LOGGER.error("Failed to connect to database.", e);
        }
    }

    private void initializeTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS seasons (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    start_date TEXT NOT NULL,
                    end_date TEXT,
                    is_active INTEGER NOT NULL DEFAULT 1
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS gym_leaders (
                    uuid TEXT PRIMARY KEY,
                    player_name TEXT NOT NULL,
                    type_name TEXT NOT NULL,
                    battle_format TEXT NOT NULL DEFAULT 'SINGLES',
                    is_open INTEGER NOT NULL DEFAULT 0,
                    extra_ban TEXT,
                    extra_ban_season INTEGER
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS leader_teams (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    leader_uuid TEXT NOT NULL,
                    slot INTEGER NOT NULL,
                    showdown_export TEXT NOT NULL,
                    UNIQUE(leader_uuid, slot)
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS elite_four (
                    uuid TEXT PRIMARY KEY,
                    player_name TEXT NOT NULL,
                    type1 TEXT NOT NULL,
                    type2 TEXT NOT NULL,
                    battle_format TEXT NOT NULL DEFAULT 'SINGLES',
                    order_index INTEGER NOT NULL DEFAULT 0
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS champion (
                    id INTEGER PRIMARY KEY DEFAULT 1,
                    uuid TEXT,
                    player_name TEXT,
                    battle_format TEXT NOT NULL DEFAULT 'SINGLES'
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS player_badges (
                    player_uuid TEXT NOT NULL,
                    season_id INTEGER NOT NULL,
                    type_name TEXT NOT NULL,
                    earned_date TEXT NOT NULL,
                    PRIMARY KEY (player_uuid, season_id, type_name)
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS player_progress (
                    player_uuid TEXT NOT NULL,
                    season_id INTEGER NOT NULL,
                    e4_wins INTEGER NOT NULL DEFAULT 0,
                    champion_win INTEGER NOT NULL DEFAULT 0,
                    PRIMARY KEY (player_uuid, season_id)
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS battle_records (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    season_id INTEGER NOT NULL,
                    leader_uuid TEXT NOT NULL,
                    challenger_uuid TEXT NOT NULL,
                    challenger_name TEXT NOT NULL,
                    result TEXT NOT NULL,
                    battle_date TEXT NOT NULL,
                    battle_type TEXT NOT NULL,
                    format TEXT NOT NULL DEFAULT 'SINGLES'
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS temp_bans (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    leader_uuid TEXT NOT NULL,
                    banned_uuid TEXT NOT NULL,
                    banned_name TEXT NOT NULL,
                    reason TEXT,
                    expires_at TEXT NOT NULL
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS smogon_bans (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    category TEXT NOT NULL,
                    value TEXT NOT NULL UNIQUE
                )
            """);

            CobbleGyms.LOGGER.info("Database tables initialized.");
            ensureDefaultData();
        }
    }

    private void ensureDefaultData() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM seasons");
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.execute("INSERT INTO seasons (start_date, is_active) VALUES (datetime('now'), 1)");
                CobbleGyms.LOGGER.info("Created initial season.");
            }
        }

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM champion");
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.execute("INSERT INTO champion (id) VALUES (1)");
            }
        }

        insertDefaultSmogonBans();
    }

    private void insertDefaultSmogonBans() throws SQLException {
        String[] uberPokemon = {
            "Arceus", "Calyrex-Ice", "Calyrex-Shadow", "Dialga", "Eternatus",
            "Giratina", "Giratina-Origin", "Groudon", "Ho-Oh", "Koraidon",
            "Kyogre", "Kyurem-White", "Lugia", "Mewtwo", "Miraidon",
            "Necrozma-Dawn-Wings", "Necrozma-Dusk-Mane", "Palkia", "Rayquaza",
            "Reshiram", "Solgaleo", "Xerneas", "Yveltal", "Zacian",
            "Zacian-Crowned", "Zamazenta-Crowned", "Zekrom", "Zygarde"
        };
        String[] bannedMoves = { "Baton Pass" };
        String[] bannedAbilities = { "Moody", "Power Construct", "Shadow Tag" };
        String[] bannedItems = { "Soul Dew" };

        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT OR IGNORE INTO smogon_bans (category, value) VALUES (?, ?)")) {
            for (String p : uberPokemon) {
                ps.setString(1, "POKEMON"); ps.setString(2, p); ps.addBatch();
            }
            for (String m : bannedMoves) {
                ps.setString(1, "MOVE"); ps.setString(2, m); ps.addBatch();
            }
            for (String a : bannedAbilities) {
                ps.setString(1, "ABILITY"); ps.setString(2, a); ps.addBatch();
            }
            for (String i : bannedItems) {
                ps.setString(1, "ITEM"); ps.setString(2, i); ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void save() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            CobbleGyms.LOGGER.error("Error closing database connection.", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
