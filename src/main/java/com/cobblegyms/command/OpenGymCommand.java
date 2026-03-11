package com.cobblegyms.command;

import com.cobblegyms.permission.PermissionHelper;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class OpenGymCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("open_gym")
            .requires(PermissionHelper::isGymLeader)
            .executes(context -> {
                context.getSource().sendFeedback(Text.literal("Tu gimnasio está ahora abierto!"), false);
                // Aquí la lógica para poner el gimnasio como abierto
                return 1;
            })
        );
    }
}