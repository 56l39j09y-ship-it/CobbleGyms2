package com.cobblegyms;

import com.cobblegyms.commands.CommandRegistry;
import com.cobblegyms.config.CobbleGymsConfig;
import com.cobblegyms.data.DataManager;
import com.cobblegyms.events.EventRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CobbleGyms implements ModInitializer {

    public static final String MOD_ID = "cobblegyms";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static MinecraftServer server;
    private static DataManager dataManager;
    private static CobbleGymsConfig config;

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing CobbleGyms...");

        // Cargar configuración
        config = CobbleGymsConfig.load();

        // Registrar eventos del ciclo de vida del servidor
        ServerLifecycleEvents.SERVER_STARTED.register(srv -> {
            server = srv;
            dataManager = new DataManager(srv);
            dataManager.load();
            LOGGER.info("CobbleGyms data loaded successfully.");
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(srv -> {
            if (dataManager != null) {
                dataManager.save();
            }
            LOGGER.info("CobbleGyms data saved.");
        });

        // Registrar comandos
        CommandRegistry.register();

        // Registrar eventos de Cobblemon
        EventRegistry.register();

        LOGGER.info("CobbleGyms initialized successfully!");
    }

    public static MinecraftServer getServer() { return server; }
    public static DataManager getDataManager() { return dataManager; }
    public static CobbleGymsConfig getConfig() { return config; }
}
