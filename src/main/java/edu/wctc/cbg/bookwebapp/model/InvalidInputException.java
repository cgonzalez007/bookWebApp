package edu.wctc.cbg.bookwebapp.model;

/**
 *
 * @author Chris Gonzalez 2017
 */
public class InvalidInputException extends IllegalArgumentException{
    
    private static final String MESSAGE = "Error: Values"
            + " passed in cannot be null/empty. Number values cannot be zero "
            + "or less";
    
    public InvalidInputException() {
        super(MESSAGE);
    }

    public InvalidInputException(String s) {
        super(s);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInputException(Throwable cause) {
        super(MESSAGE, cause);
    }
    
}
