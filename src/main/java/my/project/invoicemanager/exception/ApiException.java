package my.project.invoicemanager.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
