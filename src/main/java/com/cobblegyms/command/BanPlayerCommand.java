package com.cobblegyms.command;

import com.cobblegyms.permission.PermissionHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class BanPlayerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("ban_gym_player")
            .requires(PermissionHelper::isAdmin)
            .then(CommandManager.argument("player", StringArgumentType.word())
                .then(CommandManager.argument("reason", StringArgumentType.greedyString())
                    .executes(context -> {
                        String player = StringArgumentType.getString(context, "player");
                        String reason = StringArgumentType.getString(context, "reason");
                        context.getSource().sendFeedback(Text.literal("Jugador " + player + " ha sido baneado por: " + reason), false);
                        // Aquí la lógica de ban
                        return 1;
                    })
                )
            )
        );
    }
}