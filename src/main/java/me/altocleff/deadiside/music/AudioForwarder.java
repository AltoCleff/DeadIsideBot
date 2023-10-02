package me.altocleff.deadiside.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;

import java.nio.ByteBuffer;
@RequiredArgsConstructor
public class AudioForwarder implements AudioSendHandler {

    private final AudioPlayer player;
    @Getter
    private final ByteBuffer buffer;
    @Getter
    private final MutableAudioFrame frame;
    private final Guild guild;
    private  int time;

    @Override
    public boolean canProvide() {
        boolean canProvide = player.provide(frame);
        if(!canProvide) {
            time += 20;
            if(time>=3000000) {
                time = 0;
                guild.getAudioManager().closeAudioConnection();
            }
        } else {
            time = 0;
        }

        return canProvide;
    }

    @Override
    public ByteBuffer provide20MsAudio() {
        return buffer.flip();
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}
