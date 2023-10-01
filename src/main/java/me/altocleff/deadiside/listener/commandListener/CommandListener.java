package me.altocleff.deadiside.listener.commandListener;

import lombok.AllArgsConstructor;
import me.altocleff.deadiside.util.Utils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommandListener extends ListenerAdapter {
    private final CommandManager commandManager;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(Utils.UserCheck(event)) {
            commandManager.handle(event);
        }
    }
}
