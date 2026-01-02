package it.vladastos.exceptions;

public class SolverNotFoundException extends Exception {
    
    public SolverNotFoundException(String message) {
        super(message);
    }
    
    public SolverNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
