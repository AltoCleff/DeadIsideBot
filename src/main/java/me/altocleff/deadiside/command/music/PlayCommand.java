package me.altocleff.deadiside.command.music;

import lombok.RequiredArgsConstructor;
import me.altocleff.deadiside.command.ICommand;
import me.altocleff.deadiside.exception.VoiceChannelException;
import me.altocleff.deadiside.music.PlayerManager;
import me.altocleff.deadiside.util.Utils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlayCommand implements ICommand {

    private final PlayerManager playerManager;
    @Override
    public void handle(MessageReceivedEvent event, List<String> args) throws RuntimeException {
        if(Utils.UserInVoiceChanel(event)) {
            event
                    .getGuild()
                    .getAudioManager()
                    .openAudioConnection(event
                            .getMember()
                            .getVoiceState()
                            .getChannel());
            playerManager.play(event, args.get(0));

        } else {
            throw new VoiceChannelException("You should be in the voice channel!");
        }


    }

    @Override
    public String getName() {
        return "play";
    }
}
