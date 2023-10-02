package me.altocleff.deadiside.music;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GuildMusicManager {
    private final TrackScheduler trackScheduler;
    private final AudioForwarder audioForwarder;
}
