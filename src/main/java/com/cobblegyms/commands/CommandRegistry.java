package com.cobblegyms.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CommandRegistry {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            // TODO Paso 8.1: /gyms
            // TODO Paso 8.2: /challenge
            // TODO Paso 8.3: /gymsadmin
        });
    }
}
