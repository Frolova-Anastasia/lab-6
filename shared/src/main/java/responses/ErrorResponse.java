package responses;

import java.io.Serializable;

public class ErrorResponse implements Response, Serializable {
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
