package com.cobblegyms.command;

import com.cobblegyms.permission.PermissionHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class SetTeamCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("set_gym_team")
            .requires(source -> PermissionHelper.isGymLeader(source)
                || PermissionHelper.isEliteFour(source)
                || PermissionHelper.isChampion(source))
            .then(CommandManager.argument("team", StringArgumentType.greedyString())
                .executes(context -> {
                    String team = StringArgumentType.getString(context, "team");
                    context.getSource().sendFeedback(Text.literal("Equipo registrado: " + team), false);
                    // Aquí la lógica para actualizar el equipo
                    return 1;
                })
            )
        );
    }
}