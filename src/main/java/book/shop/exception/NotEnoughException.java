package book.shop.exception;

public class NotEnoughException extends RuntimeException {
    public NotEnoughException() {
        super();
    }

    public NotEnoughException(final String message) {
        super(message);
    }

    public NotEnoughException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NotEnoughException(final Throwable cause) {
        super(cause);
    }
}
