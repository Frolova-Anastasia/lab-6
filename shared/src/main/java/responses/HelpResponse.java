package responses;

import java.io.Serializable;

public class HelpResponse implements Response, Serializable {
    private  final String message;

    public HelpResponse(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
