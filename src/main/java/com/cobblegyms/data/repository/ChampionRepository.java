package com.cobblegyms.data.repository;

import com.cobblegyms.CobbleGyms;
import com.cobblegyms.model.BattleFormat;
import com.cobblegyms.model.Champion;

import java.sql.*;
import java.util.Optional;

public class ChampionRepository {

    private Connection conn() { return CobbleGyms.getDataManager().getConnection(); }

    public Optional<Champion> getChampion() {
        try (Statement stmt = conn().createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT uuid, player_name, battle_format FROM champion WHERE id = 1")) {
            if (rs.next() && rs.getString("uuid") != null) {
                return Optional.of(new Champion(
                        rs.getString("uuid"), rs.getString("player_name"),
                        BattleFormat.fromString(rs.getString("battle_format"))
                ));
            }
        } catch (SQLException e) { CobbleGyms.LOGGER.error("ChampionRepository.getChampion", e); }
        return Optional.empty();
    }

    public void setChampion(String uuid, String playerName, BattleFormat format) {
        try (PreparedStatement ps = conn().prepareStatement(
                "UPDATE champion SET uuid = ?, player_name = ?, battle_format = ? WHERE id = 1")) {
            ps.setString(1, uuid); ps.setString(2, playerName); ps.setString(3, format.name());
            ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("ChampionRepository.setChampion", e); }
    }

    public void clearChampion() {
        try (PreparedStatement ps = conn().prepareStatement(
                "UPDATE champion SET uuid = NULL, player_name = NULL WHERE id = 1")) {
            ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("ChampionRepository.clearChampion", e); }
    }

    public void setBattleFormat(BattleFormat format) {
        try (PreparedStatement ps = conn().prepareStatement(
                "UPDATE champion SET battle_format = ? WHERE id = 1")) {
            ps.setString(1, format.name()); ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("ChampionRepository.setBattleFormat", e); }
    }
}