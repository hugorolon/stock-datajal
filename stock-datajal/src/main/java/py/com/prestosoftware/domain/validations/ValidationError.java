package py.com.prestosoftware.domain.validations;

public class ValidationError {

    private String message;

    public ValidationError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
