package com.cobblegyms.data.repository;

import com.cobblegyms.CobbleGyms;
import com.cobblegyms.model.BattleFormat;
import com.cobblegyms.model.GymLeader;
import com.cobblegyms.model.GymType;

import java.sql.*;
import java.util.*;

public class GymLeaderRepository {

    private Connection conn() {
        return CobbleGyms.getDataManager().getConnection();
    }

    public Optional<GymLeader> findByUuid(String uuid) {
        try (PreparedStatement ps = conn().prepareStatement(
                "SELECT uuid, player_name, type_name, battle_format, is_open, extra_ban, extra_ban_season FROM gym_leaders WHERE uuid = ?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapLeader(rs));
        } catch (SQLException e) {
            CobbleGyms.LOGGER.error("GymLeaderRepository.findByUuid", e);
        }
        return Optional.empty();
    }

    public Optional<GymLeader> findByType(GymType type) {
        try (PreparedStatement ps = conn().prepareStatement(
                "SELECT uuid, player_name, type_name, battle_format, is_open, extra_ban, extra_ban_season FROM gym_leaders WHERE type_name = ?")) {
            ps.setString(1, type.name());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapLeader(rs));
        } catch (SQLException e) {
            CobbleGyms.LOGGER.error("GymLeaderRepository.findByType", e);
        }
        return Optional.empty();
    }

    public List<GymLeader> findAll() {
        List<GymLeader> list = new ArrayList<>();
        try (Statement stmt = conn().createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT uuid, player_name, type_name, battle_format, is_open, extra_ban, extra_ban_season FROM gym_leaders")) {
            while (rs.next()) list.add(mapLeader(rs));
        } catch (SQLException e) {
            CobbleGyms.LOGGER.error("GymLeaderRepository.findAll", e);
        }
        return list;
    }

    public void assignLeader(String uuid, String playerName, GymType type, BattleFormat format) {
        try (PreparedStatement ps = conn().prepareStatement(
                "DELETE FROM gym_leaders WHERE type_name = ? AND uuid != ?")) {
            ps.setString(1, type.name()); ps.setString(2, uuid); ps.executeUpdate();
        } catch (SQLException e) {
            CobbleGyms.LOGGER.error("GymLeaderRepository.assignLeader - remove old", e);
        }
        try (PreparedStatement ps = conn().prepareStatement(
                "INSERT INTO gym_leaders (uuid, player_name, type_name, battle_format, is_open) VALUES (?, ?, ?, ?, 0) ON CONFLICT(uuid) DO UPDATE SET player_name = excluded.player_name, type_name = excluded.type_name, battle_format = excluded.battle_format")) {
            ps.setString(1, uuid); ps.setString(2, playerName);
            ps.setString(3, type.name()); ps.setString(4, format.name());
            ps.executeUpdate();
        } catch (SQLException e) {
            CobbleGyms.LOGGER.error("GymLeaderRepository.assignLeader", e);
        }
    }

    public void removeLeader(String uuid) {
        try (PreparedStatement ps = conn().prepareStatement("DELETE FROM leader_teams WHERE leader_uuid = ?")) {
            ps.setString(1, uuid); ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("GymLeaderRepository.removeLeader - teams", e); }
        try (PreparedStatement ps = conn().prepareStatement("DELETE FROM gym_leaders WHERE uuid = ?")) {
            ps.setString(1, uuid); ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("GymLeaderRepository.removeLeader", e); }
    }

    public void setOpen(String uuid, boolean open) {
        try (PreparedStatement ps = conn().prepareStatement(
                "UPDATE gym_leaders SET is_open = ? WHERE uuid = ?")) {
            ps.setInt(1, open ? 1 : 0); ps.setString(2, uuid); ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("GymLeaderRepository.setOpen", e); }
    }

    public void setBattleFormat(String uuid, BattleFormat format) {
        try (PreparedStatement ps = conn().prepareStatement(
                "UPDATE gym_leaders SET battle_format = ? WHERE uuid = ?")) {
            ps.setString(1, format.name()); ps.setString(2, uuid); ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("GymLeaderRepository.setBattleFormat", e); }
    }

    public void setExtraBan(String uuid, String pokemon, int seasonId) {
        try (PreparedStatement ps = conn().prepareStatement(
                "UPDATE gym_leaders SET extra_ban = ?, extra_ban_season = ? WHERE uuid = ?")) {
            ps.setString(1, pokemon); ps.setInt(2, seasonId); ps.setString(3, uuid); ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("GymLeaderRepository.setExtraBan", e); }
    }

    public void saveTeam(String leaderUuid, int slot, String showdownExport) {
        try (PreparedStatement ps = conn().prepareStatement(
                "INSERT INTO leader_teams (leader_uuid, slot, showdown_export) VALUES (?, ?, ?) ON CONFLICT(leader_uuid, slot) DO UPDATE SET showdown_export = excluded.showdown_export")) {
            ps.setString(1, leaderUuid); ps.setInt(2, slot); ps.setString(3, showdownExport);
            ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("GymLeaderRepository.saveTeam", e); }
    }

    public Optional<String> getTeam(String leaderUuid, int slot) {
        try (PreparedStatement ps = conn().prepareStatement(
                "SELECT showdown_export FROM leader_teams WHERE leader_uuid = ? AND slot = ?")) {
            ps.setString(1, leaderUuid); ps.setInt(2, slot);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(rs.getString("showdown_export"));
        } catch (SQLException e) { CobbleGyms.LOGGER.error("GymLeaderRepository.getTeam", e); }
        return Optional.empty();
    }

    public Map<Integer, String> getAllTeams(String leaderUuid) {
        Map<Integer, String> teams = new LinkedHashMap<>();
        try (PreparedStatement ps = conn().prepareStatement(
                "SELECT slot, showdown_export FROM leader_teams WHERE leader_uuid = ? ORDER BY slot")) {
            ps.setString(1, leaderUuid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) teams.put(rs.getInt("slot"), rs.getString("showdown_export"));
        } catch (SQLException e) { CobbleGyms.LOGGER.error("GymLeaderRepository.getAllTeams", e); }
        return teams;
    }

    public void deleteTeam(String leaderUuid, int slot) {
        try (PreparedStatement ps = conn().prepareStatement(
                "DELETE FROM leader_teams WHERE leader_uuid = ? AND slot = ?")) {
            ps.setString(1, leaderUuid); ps.setInt(2, slot); ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("GymLeaderRepository.deleteTeam", e); }
    }

    private GymLeader mapLeader(ResultSet rs) throws SQLException {
        return new GymLeader(
                rs.getString("uuid"), rs.getString("player_name"),
                GymType.fromString(rs.getString("type_name")),
                BattleFormat.fromString(rs.getString("battle_format")),
                rs.getInt("is_open") == 1, rs.getString("extra_ban"),
                rs.getObject("extra_ban_season") != null ? rs.getInt("extra_ban_season") : null
        );
    }
}