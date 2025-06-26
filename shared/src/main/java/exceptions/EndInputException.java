package exceptions;

public class EndInputException extends Exception{
    public EndInputException(String message) {
        super("Ввод был неожиданно завершен");
    }

    public EndInputException() {
    }
}
