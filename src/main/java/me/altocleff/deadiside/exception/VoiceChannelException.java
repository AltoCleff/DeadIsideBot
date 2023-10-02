package me.altocleff.deadiside.exception;

public class VoiceChannelException extends RuntimeException{
    public VoiceChannelException() {
        super();
    }

    public VoiceChannelException(String s) {
        super(s);
    }

    public VoiceChannelException(String message, Throwable cause) {
        super(message, cause);
    }

    public VoiceChannelException(Throwable cause) {
        super(cause);
    }
}
