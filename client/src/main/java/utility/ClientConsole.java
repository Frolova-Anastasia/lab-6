package utility;

import commands.ClientCommand;
import exceptions.EndInputException;
import exceptions.WrongNumberOfArgsException;
import responses.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientConsole {
    private final Scanner scanner = new Scanner(System.in);
    private final CommandManager commandManager;

    public ClientConsole(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void run() throws WrongNumberOfArgsException, IOException, EndInputException {
        while (true) {
            System.out.print("> ");
            String line;
            try {
                line = scanner.nextLine().trim();
            } catch (NoSuchElementException e) {
                System.out.println("Ввод прерван");
                break;
            }
            if (line.isEmpty()) continue;
            String[] parts = line.split(" ");
            String name = parts[0];
            String[] args = Arrays.copyOfRange(parts, 1, parts.length);

            ClientCommand command = commandManager.getCommand(name);
            if (command == null) {
                System.out.println("Неизвестная команда " + name);
                continue;
            }
            try {
                Response response = command.execute(args);
                System.out.println(response.getMessage());
            } catch (EndInputException e) {
                System.out.println("Ввод прерван пользователем");
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
