package me.altocleff.deadiside.command.music;

import lombok.RequiredArgsConstructor;
import me.altocleff.deadiside.command.ICommand;
import me.altocleff.deadiside.exception.VoiceChannelException;
import me.altocleff.deadiside.music.GuildMusicManager;
import me.altocleff.deadiside.music.PlayerManager;
import me.altocleff.deadiside.util.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RepeatCommand implements ICommand {

    private final PlayerManager playerManager;
    @Override
    public void handle(MessageReceivedEvent event, List<String> args) throws RuntimeException {
        if(Utils.UserInVoiceChanel(event)) {
            GuildMusicManager guildMusicManager = playerManager.getGuildMusicManager(event.getGuild());
            boolean isRepeat = !guildMusicManager.getTrackScheduler().isRepeat();
            guildMusicManager.getTrackScheduler().setRepeat(isRepeat);
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setDescription("Repeat : " + isRepeat)
                    .setColor(Color.GREEN);
            Utils.ReplyToMessage(event, embedBuilder.build());
        } else {
            throw new VoiceChannelException("You should be in the voice channel!");
        }
    }

    @Override
    public String getName() {
        return "repeat";
    }

    @Override
    public List<String> getAliases() {
        return List.of("loop");
    }
}
