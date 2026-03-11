package com.cobblegyms.permission;

import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.LoggerFactory;

public final class LuckPermsHook {

    private static final boolean AVAILABLE;

    static {
        boolean found = false;
        try {
            Class.forName("net.luckperms.api.LuckPerms");
            found = true;
        } catch (ClassNotFoundException e) {
            LoggerFactory.getLogger("cobblegyms").warn("LuckPerms not found, using OP-level fallback.");
        }
        AVAILABLE = found;
    }

    public static boolean hasPermission(ServerPlayerEntity player, String permission) {
        if (!AVAILABLE) return player.hasPermissionLevel(4);
        try {
            var api = net.luckperms.api.LuckPermsProvider.get();
            var user = api.getUserManager().getUser(player.getUuid());
            if (user == null) return false;
            var data = user.getCachedData().getPermissionData();
            return data.checkPermission(permission).asBoolean();
        } catch (Exception e) {
            return player.hasPermissionLevel(4);
        }
    }

    private LuckPermsHook() {}
}