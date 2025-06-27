package utility;

import commands.*;

import java.util.*;

public class CommandManager {
    public final Map<String, Command> commands = new HashMap<>();
    private final CollectionManager collectionManager;

    public CommandManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        registerAll();
    }

    private void registerAll(){
        registerCommand(new HelpCommand(commands));
        registerCommand(new AddCommand(collectionManager));
        registerCommand(new ShowCommand(collectionManager));
        registerCommand(new ClearCommand(collectionManager));
        registerCommand(new CountByPriceCommand(collectionManager));
        registerCommand(new FilterGreaterManufactureCommand(collectionManager));
        registerCommand(new InfoCommand(collectionManager));
    }

    public void registerCommand(Command command){
        commands.put(command.getName(), command);
    }

    public Command getCommand(String name){
        return commands.get(name);
    }

    public Collection<Command> getCommands() {
        return commands.values();
    }
}
