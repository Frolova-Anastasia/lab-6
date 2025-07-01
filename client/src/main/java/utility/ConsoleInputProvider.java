package utility;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleInputProvider implements InputProvider{
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String nextLine() throws NoSuchElementException {
        return scanner.nextLine();
    }
}
