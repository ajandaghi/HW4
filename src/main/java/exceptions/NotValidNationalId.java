package exceptions;

public class NotValidNationalId extends RuntimeException {
    public NotValidNationalId(String message) {
        super(message);
    }
}
