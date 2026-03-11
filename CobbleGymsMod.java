package com.cobblegyms;

import com.cobblegyms.command.OpenGymCommand;
import com.cobblegyms.command.BanPlayerCommand;
import com.cobblegyms.command.SetTeamCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

public class CobbleGymsMod implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            OpenGymCommand.register(dispatcher);
            BanPlayerCommand.register(dispatcher);
            SetTeamCommand.register(dispatcher);
        });
    }
}
