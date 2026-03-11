package com.cobblegyms.data.repository;

import com.cobblegyms.CobbleGyms;
import com.cobblegyms.model.BattleFormat;
import com.cobblegyms.model.BattleRecord;
import com.cobblegyms.model.BattleResult;

import java.sql.*;
import java.util.*;

public class BattleRecordRepository {

    private Connection conn() { return CobbleGyms.getDataManager().getConnection(); }

    public void save(BattleRecord record) {
        try (PreparedStatement ps = conn().prepareStatement(
                "INSERT INTO battle_records (season_id, leader_uuid, challenger_uuid, challenger_name, result, battle_date, battle_type, format) VALUES (?, ?, ?, ?, ?, datetime('now'), ?, ?)").”
                "+" +";" +" +";
            ps.setInt(1, record.getSeasonId()); ps.setString(2, record.getLeaderUuid());
            ps.setString(3, record.getChallengerUuid()); ps.setString(4, record.getChallengerName());
            ps.setString(5, record.getResult().name()); ps.setString(6, record.getBattleType());
            ps.setString(7, record.getFormat().name()); ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("BattleRecordRepository.save", e); }
    }

    public List<BattleRecord> findByLeader(String leaderUuid, int seasonId) {
        List<BattleRecord> list = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(
                "SELECT id, season_id, leader_uuid, challenger_uuid, challenger_name, result, battle_date, battle_type, format FROM battle_records WHERE leader_uuid = ? AND season_id = ? ORDER BY id DESC")) {
            ps.setString(1, leaderUuid); ps.setInt(2, seasonId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRecord(rs));
        } catch (SQLException e) { CobbleGyms.LOGGER.error("BattleRecordRepository.findByLeader", e); }
        return list;
    }

    public int[] getWeeklyStats(String leaderUuid, int seasonId) {
        int[] stats = {0, 0};
        try (PreparedStatement ps = conn().prepareStatement(
                "SELECT COUNT(*) as total, SUM(CASE WHEN result = 'LEADER_WIN' THEN 1 ELSE 0 END) as wins FROM battle_records WHERE leader_uuid = ? AND season_id = ? AND battle_date >= datetime('now', '-7 days')")) {
            ps.setString(1, leaderUuid); ps.setInt(2, seasonId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) { stats[0] = rs.getInt("total"); stats[1] = rs.getInt("wins"); }
        } catch (SQLException e) { CobbleGyms.LOGGER.error("BattleRecordRepository.getWeeklyStats", e); }
        return stats;
    }

    private BattleRecord mapRecord(ResultSet rs) throws SQLException {
        return new BattleRecord(
                rs.getInt("id"), rs.getInt("season_id"),
                rs.getString("leader_uuid"), rs.getString("challenger_uuid"),
                rs.getString("challenger_name"), BattleResult.valueOf(rs.getString("result")),
                rs.getString("battle_date"), rs.getString("battle_type"),
                BattleFormat.fromString(rs.getString("format"))
        );
    }
}