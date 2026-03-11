package com.cobblegyms.data.repository;

import com.cobblegyms.CobbleGyms;
import com.cobblegyms.model.TempBan;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TempBanRepository {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Connection conn() { return CobbleGyms.getDataManager().getConnection(); }

    public Optional<TempBan> getActiveBan(String leaderUuid, String bannedUuid) {
        try (PreparedStatement ps = conn().prepareStatement(
                "SELECT id, leader_uuid, banned_uuid, banned_name, reason, expires_at FROM temp_bans WHERE leader_uuid = ? AND banned_uuid = ? AND expires_at > datetime('now') ORDER BY expires_at DESC LIMIT 1")) {
            ps.setString(1, leaderUuid); ps.setString(2, bannedUuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapBan(rs));
        } catch (SQLException e) { CobbleGyms.LOGGER.error("TempBanRepository.getActiveBan", e); }
        return Optional.empty();
    }

    public void ban(String leaderUuid, String bannedUuid, String bannedName, String reason, int durationHours) {
        String expiresAt = LocalDateTime.now().plusHours(durationHours).format(FMT);
        try (PreparedStatement ps = conn().prepareStatement(
                "INSERT INTO temp_bans (leader_uuid, banned_uuid, banned_name, reason, expires_at) VALUES (?, ?, ?, ?, ?)") ) {
            ps.setString(1, leaderUuid); ps.setString(2, bannedUuid);
            ps.setString(3, bannedName); ps.setString(4, reason); ps.setString(5, expiresAt);
            ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("TempBanRepository.ban", e); }
    }

    public void unban(String leaderUuid, String bannedUuid) {
        try (PreparedStatement ps = conn().prepareStatement(
                "DELETE FROM temp_bans WHERE leader_uuid = ? AND banned_uuid = ?")) {
            ps.setString(1, leaderUuid); ps.setString(2, bannedUuid); ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("TempBanRepository.unban", e); }
    }

    public List<TempBan> getActiveBansForLeader(String leaderUuid) {
        List<TempBan> list = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(
                "SELECT id, leader_uuid, banned_uuid, banned_name, reason, expires_at FROM temp_bans WHERE leader_uuid = ? AND expires_at > datetime('now') ORDER BY expires_at DESC")) {
            ps.setString(1, leaderUuid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapBan(rs));
        } catch (SQLException e) { CobbleGyms.LOGGER.error("TempBanRepository.getActiveBansForLeader", e); }
        return list;
    }

    private TempBan mapBan(ResultSet rs) throws SQLException {
        return new TempBan(rs.getInt("id"), rs.getString("leader_uuid"), rs.getString("banned_uuid"),
                rs.getString("banned_name"), rs.getString("reason"), rs.getString("expires_at"));
    }
}