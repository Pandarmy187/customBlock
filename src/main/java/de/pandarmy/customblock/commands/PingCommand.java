package de.pandarmy.customblock.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class PingCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("ping").executes(ctx -> {
            if (ctx.getSource().isExecutedByPlayer()) {

                ServerPlayerEntity player = ctx.getSource().getPlayer();
                int ping = player.networkHandler.getLatency();
                Text message = Text.literal("Ping: ").formatted(Formatting.YELLOW).append(Text.literal(String.valueOf(ping)).formatted(Formatting.GREEN)).append(Text.literal("ms").formatted(Formatting.GREEN));


                player.sendMessage(message);


            } else {
                ctx.getSource().sendFeedback(() -> Text.literal("You must be a player"), false);
            }
            return 1;
        }));
    }
}
