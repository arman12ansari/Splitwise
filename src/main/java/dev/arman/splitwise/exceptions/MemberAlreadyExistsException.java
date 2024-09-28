package dev.arman.splitwise.exceptions;

/**
 * @author mdarmanansari
 */
public class MemberAlreadyExistsException extends Exception {

    public MemberAlreadyExistsException() {
        super();
    }

    public MemberAlreadyExistsException(String message) {
        super(message);
    }

    public MemberAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected MemberAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
