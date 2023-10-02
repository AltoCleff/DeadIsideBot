package me.altocleff.deadiside.command.music;

import lombok.RequiredArgsConstructor;
import me.altocleff.deadiside.command.ICommand;
import me.altocleff.deadiside.exception.VoiceChannelException;
import me.altocleff.deadiside.music.GuildMusicManager;
import me.altocleff.deadiside.music.PlayerManager;
import me.altocleff.deadiside.music.TrackScheduler;
import me.altocleff.deadiside.util.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StopCommand implements ICommand {

    private final PlayerManager playerManager;
    @Override
    public void handle(MessageReceivedEvent event, List<String> args) throws RuntimeException {
        if(Utils.UserInVoiceChanel(event)) {
            GuildMusicManager guildMusicManager =playerManager.getGuildMusicManager(event.getGuild());
            TrackScheduler trackScheduler = guildMusicManager.getTrackScheduler();
            trackScheduler.setRepeat(false);
            trackScheduler.getQueue().clear();
            trackScheduler.getPlayer().stopTrack();
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setDescription("Stopped")
                    .setColor(Color.GREEN);
            Utils.ReplyToMessage(event, embedBuilder.build());
        } else {
            throw new VoiceChannelException("You should be in the voice channel!");
        }
    }

    @Override
    public String getName() {
        return "stop";
    }
}
