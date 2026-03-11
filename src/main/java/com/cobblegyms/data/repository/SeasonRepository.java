package com.cobblegyms.data.repository;

import com.cobblegyms.CobbleGyms;
import com.cobblegyms.model.Season;

import java.sql.*;
import java.util.*;

public class SeasonRepository {

    private Connection conn() {
        return CobbleGyms.getDataManager().getConnection();
    }

    public Optional<Season> getActiveSeason() {
        try (PreparedStatement ps = conn().prepareStatement(
                "SELECT id, start_date, end_date FROM seasons WHERE is_active = 1 ORDER BY id DESC LIMIT 1")) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapSeason(rs));
        } catch (SQLException e) {
            CobbleGyms.LOGGER.error("SeasonRepository.getActiveSeason", e);
        }
        return Optional.empty();
    }

    public List<Season> getAllSeasons() {
        List<Season> list = new ArrayList<>();
        try (Statement stmt = conn().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, start_date, end_date FROM seasons ORDER BY id DESC")) {
            while (rs.next()) list.add(mapSeason(rs));
        } catch (SQLException e) {
            CobbleGyms.LOGGER.error("SeasonRepository.getAllSeasons", e);
        }
        return list;
    }

    public Season startNewSeason() {
        try {
            conn().setAutoCommit(false);
            try (PreparedStatement ps = conn().prepareStatement(
                    "UPDATE seasons SET is_active = 0, end_date = datetime('now') WHERE is_active = 1")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn().prepareStatement(
                    "INSERT INTO seasons (start_date, is_active) VALUES (datetime('now'), 1)")) {
                ps.executeUpdate();
            }
            conn().commit();
            conn().setAutoCommit(true);
        } catch (SQLException e) {
            CobbleGyms.LOGGER.error("SeasonRepository.startNewSeason", e);
            try { conn().rollback(); conn().setAutoCommit(true); } catch (SQLException ignored) {}
        }
        return getActiveSeason().orElseThrow(() -> new IllegalStateException("No active season"));
    }

    private Season mapSeason(ResultSet rs) throws SQLException {
        return new Season(rs.getInt("id"), rs.getString("start_date"), rs.getString("end_date"));
    }
}