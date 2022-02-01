package exceptions;

public class NotValidInputFormat extends RuntimeException{
    public NotValidInputFormat(String message) {
        super(message);
    }
}
