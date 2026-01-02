package it.vladastos.exceptions;

public class InputFileException extends Exception {
    public InputFileException(String message) {
        super(message);
    }

    public InputFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
