package me.altocleff.deadiside.config;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import me.altocleff.deadiside.music.AudioForwarder;
import me.altocleff.deadiside.music.GuildMusicManager;
import me.altocleff.deadiside.music.PlayerManager;
import me.altocleff.deadiside.music.TrackScheduler;
import net.dv8tion.jda.api.entities.Guild;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.nio.ByteBuffer;

@Configuration
public class LavaPlayerConfig {

    @Bean
    public AudioPlayerManager audioPlayerManager() {
        return new DefaultAudioPlayerManager();
    }


    @Bean
    public PlayerManager playerManager(AudioPlayerManager audioPlayerManager) {
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        return new PlayerManager(audioPlayerManager);
    }

    @Bean
    @Scope("prototype")
    public GuildMusicManager guildMusicManager(Guild guild, AudioPlayerManager manager) {
        AudioPlayer player = manager.createPlayer();
        TrackScheduler trackScheduler = new TrackScheduler(player);
        player.addListener(trackScheduler);
        AudioForwarder audioForwarder = new AudioForwarder(player, ByteBuffer.allocate(1024), new MutableAudioFrame(), guild);
        audioForwarder.getFrame().setBuffer(audioForwarder.getBuffer());
        return new GuildMusicManager(trackScheduler, audioForwarder);
    }
}
