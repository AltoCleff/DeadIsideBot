package me.altocleff.deadiside.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lombok.RequiredArgsConstructor;
import me.altocleff.deadiside.util.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
@Component
@RequiredArgsConstructor
public class PlayerManager {

    private final Map<Long, GuildMusicManager> guildMusicManagerMap = new HashMap<>();
    private final AudioPlayerManager audioPlayerManager;

//    public PlayerManager() {
//        AudioSourceManagers.registerLocalSource(audioPlayerManager);
//        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
//    }

    public GuildMusicManager getGuildMusicManager(Guild guild) {
        return guildMusicManagerMap.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            GuildMusicManager musicManager = Utils.LAVA_CONTEXT.getBean(GuildMusicManager.class, guild, audioPlayerManager);
            guild.getAudioManager().setSendingHandler(musicManager.getAudioForwarder());
            return  musicManager;
        });
    }

    public void play(MessageReceivedEvent event, String trackUrl) {
        GuildMusicManager guildMusicManager = getGuildMusicManager(event.getGuild());
        audioPlayerManager.loadItemOrdered(guildMusicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                guildMusicManager.getTrackScheduler().queue(audioTrack);

                AudioTrackInfo audioTrackInfo = audioTrack.getInfo();
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle(audioTrackInfo.title);
                embedBuilder.setUrl(audioTrackInfo.uri);
                embedBuilder.setDescription("Added to queue");
                embedBuilder.setColor(Color.GREEN);
                Utils.ReplyToMessage(event, embedBuilder.build());
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                for(AudioTrack track : audioPlaylist.getTracks()) {
                    guildMusicManager.getTrackScheduler().queue(track);
                }
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle(audioPlaylist.getName());
                embedBuilder.setDescription("Added to queue");
                embedBuilder.setColor(Color.GREEN);
                Utils.ReplyToMessage(event, embedBuilder.build());
            }

            @Override
            public void noMatches() {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Error");
                embedBuilder.setDescription("Song not found");
                embedBuilder.setColor(Color.RED);
                Utils.ReplyToMessage(event, embedBuilder.build());
            }

            @Override
            public void loadFailed(FriendlyException e) {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Error");
                embedBuilder.setDescription("Load failed");
                embedBuilder.setColor(Color.RED);
                Utils.ReplyToMessage(event, embedBuilder.build());
            }
        });
    }
}
