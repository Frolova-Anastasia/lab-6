package utility;

import requests.Request;

import java.io.Serial;
import java.io.Serializable;

public class CommandWrapper implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String commandName;
    private final Request request;

    public CommandWrapper(String commandName, Request request) {
        this.commandName = commandName;
        this.request = request;
    }

    public String getCommandName() {
        return commandName;
    }

    public Request getRequest() {
        return request;
    }
}
