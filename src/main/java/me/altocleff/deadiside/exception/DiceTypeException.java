package me.altocleff.deadiside.exception;

public class DiceTypeException extends RuntimeException{
    public DiceTypeException() {
        super();
    }

    public DiceTypeException(String s) {
        super(s);
    }

    public DiceTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiceTypeException(Throwable cause) {
        super(cause);
    }
}
