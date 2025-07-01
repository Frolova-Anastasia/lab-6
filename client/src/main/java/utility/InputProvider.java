package utility;

import java.util.NoSuchElementException;

public interface InputProvider {
    String nextLine() throws NoSuchElementException;
}
