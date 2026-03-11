package com.cobblegyms.data.repository;

import com.cobblegyms.CobbleGyms;
import com.cobblegyms.model.GymType;

import java.sql.*;
import java.util.*;

public class PlayerRepository {

    private Connection conn() { return CobbleGyms.getDataManager().getConnection(); }

    public Set<GymType> getBadges(String playerUuid, int seasonId) {
        Set<GymType> badges = new LinkedHashSet<>();
        try (PreparedStatement ps = conn().prepareStatement(
                "SELECT type_name FROM player_badges WHERE player_uuid = ? AND season_id = ?")) {
            ps.setString(1, playerUuid); ps.setInt(2, seasonId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GymType t = GymType.fromString(rs.getString("type_name"));
                if (t != null) badges.add(t);
            }
        } catch (SQLException e) { CobbleGyms.LOGGER.error("PlayerRepository.getBadges", e); }
        return badges;
    }

    public void awardBadge(String playerUuid, int seasonId, GymType type) {
        try (PreparedStatement ps = conn().prepareStatement(
                "INSERT OR IGNORE INTO player_badges (player_uuid, season_id, type_name, earned_date) VALUES (?, ?, ?, datetime('now'))")) {
            ps.setString(1, playerUuid); ps.setInt(2, seasonId); ps.setString(3, type.name());
            ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("PlayerRepository.awardBadge", e); }
    }

    public boolean hasAllBadges(String playerUuid, int seasonId) {
        return getBadges(playerUuid, seasonId).size() == GymType.values().length;
    }

    public int getE4Wins(String playerUuid, int seasonId) {
        try (PreparedStatement ps = conn().prepareStatement(
                "SELECT e4_wins FROM player_progress WHERE player_uuid = ? AND season_id = ?")) {
            ps.setString(1, playerUuid); ps.setInt(2, seasonId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("e4_wins");
        } catch (SQLException e) { CobbleGyms.LOGGER.error("PlayerRepository.getE4Wins", e); }
        return 0;
    }

    public void incrementE4Win(String playerUuid, int seasonId) {
        ensureProgressRow(playerUuid, seasonId);
        try (PreparedStatement ps = conn().prepareStatement(
                "UPDATE player_progress SET e4_wins = e4_wins + 1 WHERE player_uuid = ? AND season_id = ?")) {
            ps.setString(1, playerUuid); ps.setInt(2, seasonId); ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("PlayerRepository.incrementE4Win", e); }
    }

    public boolean hasBeatenChampion(String playerUuid, int seasonId) {
        try (PreparedStatement ps = conn().prepareStatement(
                "SELECT champion_win FROM player_progress WHERE player_uuid = ? AND season_id = ?")) {
            ps.setString(1, playerUuid); ps.setInt(2, seasonId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("champion_win") == 1;
        } catch (SQLException e) { CobbleGyms.LOGGER.error("PlayerRepository.hasBeatenChampion", e); }
        return false;
    }

    public void setBeatenChampion(String playerUuid, int seasonId) {
        ensureProgressRow(playerUuid, seasonId);
        try (PreparedStatement ps = conn().prepareStatement(
                "UPDATE player_progress SET champion_win = 1 WHERE player_uuid = ? AND season_id = ?")) {
            ps.setString(1, playerUuid); ps.setInt(2, seasonId);
            ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("PlayerRepository.setBeatenChampion", e); }
    }

    private void ensureProgressRow(String playerUuid, int seasonId) {
        try (PreparedStatement ps = conn().prepareStatement(
                "INSERT OR IGNORE INTO player_progress (player_uuid, season_id) VALUES (?, ?)")) {
            ps.setString(1, playerUuid); ps.setInt(2, seasonId); ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("PlayerRepository.ensureProgressRow", e); }
    }
}