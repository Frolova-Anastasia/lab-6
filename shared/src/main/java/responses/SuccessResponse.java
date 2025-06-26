package responses;

import java.io.Serializable;

public class SuccessResponse implements Response, Serializable {
    private final String message;

    public SuccessResponse(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
