package exceptions;

public class NotValidDateTime extends RuntimeException{
    public NotValidDateTime(String message) {
        super(message);
    }
}
