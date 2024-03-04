package com.unnamedtemp.xuan;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.unnamedtemp.xuan.data.Element;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import static com.unnamedtemp.xuan.Register.AttachmentTypes;

@Mod.EventBusSubscriber
public class ModCommands {
    @SubscribeEvent
    public static void OnCommandRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> naturityCmd = dispatcher.register(
                Commands.literal("naturity")
                        .then(
                                Commands.literal("chunk")
                                        .requires(commandSourceStack -> {
                                            return commandSourceStack.hasPermission(0);
                                        })
                                        .executes(GetChunkNaturity.getInstance())
                        )
        );
    }

    public static class GetChunkNaturity implements Command<CommandSourceStack> {
        private static ModCommands.GetChunkNaturity instance;

        public static GetChunkNaturity getInstance() {
            if (instance == null) instance = new ModCommands.GetChunkNaturity();
            return instance;
        }

        @Override
        public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
            var source = context.getSource();
            var chunk = source.getLevel().getChunk((int) source.getPosition().x, (int) source.getPosition().z);
            if (chunk.hasData(AttachmentTypes.ENV_NATURITY)) {
                var naturity = chunk.getData(AttachmentTypes.ENV_NATURITY);
                for (var element : Element.values()) {
                    String msg = String.format("%s:%d\n", element, naturity.getConc(element));
                    context.getSource().sendSystemMessage(Component.literal(msg));
                }
                String msg = String.format("polarity:%f%%", naturity.getPolarity() * 100);
                context.getSource().sendSystemMessage(Component.literal(msg));
            } else {
                var msg = "null chunk";
                context.getSource().sendSystemMessage(Component.literal(msg));
            }
            return 0;
        }
    }
}
