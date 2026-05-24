package carshop.app;

/**
 * Означает отмену текущей пользовательской операции.
 */
public class OperationCancelledException extends RuntimeException {

    public OperationCancelledException() {
        super("Операция отменена.");
    }
}
