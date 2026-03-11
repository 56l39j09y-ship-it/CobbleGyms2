package com.cobblegyms.permission;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public final class PermissionHelper {

    private PermissionHelper() {}

    public static boolean has(ServerCommandSource source, String permission) {
        if (source.hasPermissionLevel(4)) return true;
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            return LuckPermsHook.hasPermission(player, permission);
        }
        return false;
    }

    public static boolean isAdmin(ServerCommandSource source) {
        return has(source, Permissions.ADMIN);
    }

    public static boolean isGymLeader(ServerCommandSource source) {
        return has(source, Permissions.GYM_LEADER);
    }

    public static boolean isEliteFour(ServerCommandSource source) {
        return has(source, Permissions.ELITE_FOUR);
    }

    public static boolean isChampion(ServerCommandSource source) {
        return has(source, Permissions.CHAMPION);
    }
}