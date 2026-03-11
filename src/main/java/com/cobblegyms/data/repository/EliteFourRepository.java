package com.cobblegyms.data.repository;

import com.cobblegyms.CobbleGyms;
import com.cobblegyms.model.BattleFormat;
import com.cobblegyms.model.EliteFourMember;
import com.cobblegyms.model.GymType;

import java.sql.*;
import java.util.*;

public class EliteFourRepository {

    private Connection conn() { return CobbleGyms.getDataManager().getConnection(); }

    public List<EliteFourMember> findAll() {
        List<EliteFourMember> list = new ArrayList<>();
        try (Statement stmt = conn().createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT uuid, player_name, type1, type2, battle_format, order_index FROM elite_four ORDER BY order_index")) {
            while (rs.next()) list.add(mapMember(rs));
        } catch (SQLException e) { CobbleGyms.LOGGER.error("EliteFourRepository.findAll", e); }
        return list;
    }

    public Optional<EliteFourMember> findByUuid(String uuid) {
        try (PreparedStatement ps = conn().prepareStatement(
                "SELECT uuid, player_name, type1, type2, battle_format, order_index FROM elite_four WHERE uuid = ?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapMember(rs));
        } catch (SQLException e) { CobbleGyms.LOGGER.error("EliteFourRepository.findByUuid", e); }
        return Optional.empty();
    }

    public void assign(String uuid, String playerName, GymType type1, GymType type2, BattleFormat format, int orderIndex) {
        try (PreparedStatement ps = conn().prepareStatement(
                "INSERT INTO elite_four (uuid, player_name, type1, type2, battle_format, order_index) VALUES (?, ?, ?, ?, ?, ?) ON CONFLICT(uuid) DO UPDATE SET player_name = excluded.player_name, type1 = excluded.type1, type2 = excluded.type2, battle_format = excluded.battle_format, order_index = excluded.order_index")) {
            ps.setString(1, uuid); ps.setString(2, playerName);
            ps.setString(3, type1.name()); ps.setString(4, type2.name());
            ps.setString(5, format.name()); ps.setInt(6, orderIndex);
            ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("EliteFourRepository.assign", e); }
    }

    public void remove(String uuid) {
        try (PreparedStatement ps = conn().prepareStatement("DELETE FROM elite_four WHERE uuid = ?")) {
            ps.setString(1, uuid); ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("EliteFourRepository.remove", e); }
    }

    public void setBattleFormat(String uuid, BattleFormat format) {
        try (PreparedStatement ps = conn().prepareStatement(
                "UPDATE elite_four SET battle_format = ? WHERE uuid = ?")) {
            ps.setString(1, format.name()); ps.setString(2, uuid); ps.executeUpdate();
        } catch (SQLException e) { CobbleGyms.LOGGER.error("EliteFourRepository.setBattleFormat", e); }
    }

    private EliteFourMember mapMember(ResultSet rs) throws SQLException {
        return new EliteFourMember(
                rs.getString("uuid"), rs.getString("player_name"),
                GymType.fromString(rs.getString("type1")), GymType.fromString(rs.getString("type2")),
                BattleFormat.fromString(rs.getString("battle_format")), rs.getInt("order_index")
        );
    }
}