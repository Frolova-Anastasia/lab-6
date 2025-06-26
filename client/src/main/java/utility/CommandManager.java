package utility;

import commands.AddCommand;
import commands.ClientCommand;
import commands.HelpCommand;

import java.util.HashMap;
import java.util.Map;


public class CommandManager {
    private final Map<String, ClientCommand> commandMap = new HashMap<>();

    public CommandManager(CommandSender sender, ProductBuilder builder) {
        register(new HelpCommand(sender));
        register(new AddCommand(sender, builder));
    }

    private void register(ClientCommand command) {
        commandMap.put(command.getName(), command);
    }

    public ClientCommand getCommand(String name) {
        return commandMap.get(name);
    }

    public Map<String, ClientCommand> getCommands() {
        return commandMap;
    }
}
