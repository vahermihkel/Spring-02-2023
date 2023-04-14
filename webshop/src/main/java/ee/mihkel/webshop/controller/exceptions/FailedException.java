package ee.mihkel.webshop.controller.exceptions;

public class FailedException extends Throwable {

    public FailedException(String message) {
        super(message);
    }
}
