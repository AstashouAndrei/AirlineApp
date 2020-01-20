package by.gstu.airline.exception;

public class ProfessionException extends RuntimeException {

    public ProfessionException() {
        super();
    }

    public ProfessionException(String message) {
        super(message);
    }

    public ProfessionException(String message, Throwable cause) {
        super(message, cause);
    }
}
