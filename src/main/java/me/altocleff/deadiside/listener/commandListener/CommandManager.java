package me.altocleff.deadiside.listener.commandListener;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import me.altocleff.deadiside.command.ICommand;
import me.altocleff.deadiside.exception.DiceTypeException;
import me.altocleff.deadiside.exception.MessageBuildException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommandManager {

    private final List<ICommand> commands;

    @Value("${bot.prefix}")
    private String prefix;

    @Nullable
    public ICommand getCommand(String name) {
        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(name.toLowerCase()) || cmd.getAliases().contains(name.toLowerCase())) {
                return cmd;
            }
        }

        return null;
    }

    public void handle(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.toLowerCase().startsWith(prefix.toLowerCase())) {
            String[] split = content.substring(prefix.length()).split("\\s+");
            ICommand command = getCommand(split[0].toLowerCase());

            if (command != null) {
                try {
                    command.handle(event, List.of(Arrays.copyOfRange(split, 1, split.length)));
                } catch (MessageBuildException | DiceTypeException e) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Error");
                    builder.setColor(Color.RED);
                    builder.setDescription(e.getMessage());


                    event.getChannel().sendMessage(new MessageCreateBuilder().addEmbeds(builder.build()).build()).queue();
                }
            }
        }
    }
}
