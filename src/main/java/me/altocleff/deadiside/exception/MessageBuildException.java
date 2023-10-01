package me.altocleff.deadiside.exception;

public class MessageBuildException extends RuntimeException{

    public MessageBuildException() {
        super();
    }

    public MessageBuildException(String s) {
        super(s);
    }

    public MessageBuildException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageBuildException(Throwable cause) {
        super(cause);
    }
}
