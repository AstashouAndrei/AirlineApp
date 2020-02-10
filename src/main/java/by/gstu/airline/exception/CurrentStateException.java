package main.java.by.gstu.airline.exception;

public class CurrentStateException extends RuntimeException {

    public CurrentStateException() {
        super();
    }

    public CurrentStateException(String message) {
        super(message);
    }

    public CurrentStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
