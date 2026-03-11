package com.cobblegyms.data.repository;

import com.cobblegyms.CobbleGyms;

import java.sql.*;
import java.util.*;

public class SmogonBanRepository {

    public enum Category { POKEMON, MOVE, ABILITY, ITEM }

    private Connection conn() { return CobbleGyms.getDataManager().getConnection(); }

    public Set<String> getBans(Category category) {
        Set<String> bans = new LinkedHashSet<>();
        try (PreparedStatement ps = conn().prepareStatement(
                "SELECT value FROM smogon_bans WHERE category = ? ORDER BY value")) {
            ps.setString(1, category.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) bans.add(rs.getString("value"));
        } catch (SQLException e) { CobbleGyms.LOGGER.error("SmogonBanRepository.getBans", e); }
        return bans;
    }

    public boolean isBanned(Category category, String value) {
        try (PreparedStatement ps = conn().prepareStatement(
                "SELECT 1 FROM smogon_bans WHERE category = ? AND LOWER(value) = LOWER(?)")) {
            ps.setString(1, category.name()); ps.setString(2, value);
            return ps.executeQuery().next();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("SmogonBanRepository.isBanned", e); }
        return false;
    }

    public boolean addBan(Category category, String value) {
        try (PreparedStatement ps = conn().prepareStatement(
                "INSERT OR IGNORE INTO smogon_bans (category, value) VALUES (?, ?)")) {
            ps.setString(1, category.name()); ps.setString(2, value);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { CobbleGyms.LOGGER.error("SmogonBanRepository.addBan", e); }
        return false;
    }

    public boolean removeBan(Category category, String value) {
        try (PreparedStatement ps = conn().prepareStatement(
                "DELETE FROM smogon_bans WHERE category = ? AND LOWER(value) = LOWER(?)")) {
            ps.setString(1, category.name()); ps.setString(2, value);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { CobbleGyms.LOGGER.error("SmogonBanRepository.removeBan", e); }
        return false;
    }
}