package main.java.by.gstu.airline.exception;

public class DBTypeException extends RuntimeException {

    public DBTypeException() {
        super();
    }

    public DBTypeException(String message) {
        super(message);
    }

    public DBTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
