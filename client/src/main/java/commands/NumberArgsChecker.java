package commands;

import exceptions.WrongNumberOfArgsException;


public class NumberArgsChecker {
    public static void checkArgs(String[] command, int expectedArgs) throws WrongNumberOfArgsException {
        int actualArgs = (command == null) ? 0 : command.length;
        if(actualArgs != expectedArgs){
            throw new WrongNumberOfArgsException("Эта команда принимает " + expectedArgs + " аргументов");
        }
    }
}
