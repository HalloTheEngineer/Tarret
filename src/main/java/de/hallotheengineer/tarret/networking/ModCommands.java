package de.hallotheengineer.tarret.networking;

import com.mojang.brigadier.context.CommandContext;
import de.hallotheengineer.tarret.Tarret;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public class ModCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, environment) -> {
            dispatcher.register(literal("tarret")
                    .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                    .then(literal("config")
                            .then(literal("reload")
                                    .executes(ModCommands::reloadConfig))));
        });
    }


    private static int reloadConfig(CommandContext<ServerCommandSource> context) {
        Tarret.CONFIG.load();
        return 1;
    }
}
