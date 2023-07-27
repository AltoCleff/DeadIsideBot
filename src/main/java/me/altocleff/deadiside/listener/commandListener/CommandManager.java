package me.altocleff.deadiside.listener.commandListener;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import me.altocleff.deadiside.command.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
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
                command.handle(event, List.of(Arrays.copyOfRange(split, 1, split.length)));
            }
        }
    }
}
