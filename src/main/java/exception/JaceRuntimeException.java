package exception;

/**
 * A generic runtime exception for J-Ace that indicates an unexpected condition.
 * @author Iiro Hietala
 */
public class JaceRuntimeException extends RuntimeException {

    public JaceRuntimeException(String message, Throwable t) {
        super(message, t);
    }

    public JaceRuntimeException(String message) {
        super(message);
    }
}
