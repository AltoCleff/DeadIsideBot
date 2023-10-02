package me.altocleff.deadiside.command.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lombok.RequiredArgsConstructor;
import me.altocleff.deadiside.command.ICommand;
import me.altocleff.deadiside.exception.VoiceChannelException;
import me.altocleff.deadiside.music.GuildMusicManager;
import me.altocleff.deadiside.music.PlayerManager;
import me.altocleff.deadiside.util.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@RequiredArgsConstructor
public class QueueCommand implements ICommand {
    private final PlayerManager playerManager;
    @Override
    public void handle(MessageReceivedEvent event, List<String> args) throws RuntimeException {
        if(Utils.UserInVoiceChanel(event)) {

            GuildMusicManager guildMusicManager = playerManager.getGuildMusicManager(event.getGuild());
            List<AudioTrack> queue = new ArrayList<>(guildMusicManager.getTrackScheduler().getQueue());
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Current Queue");
            if(queue.isEmpty()) {
                embedBuilder.setDescription("Queue is empty");
            }
            for(int i = 0; i < queue.size(); i++) {
                AudioTrackInfo info = queue.get(i).getInfo();
                embedBuilder.addField(i+1 + ":",info.author + " - " + info.title, false);
            }
            Utils.ReplyToMessage(event, embedBuilder.build());
        } else {
            throw new VoiceChannelException("You should be in the voice channel!");
        }
    }

    @Override
    public String getName() {
        return "queue";
    }
}
