package exceptions;

public class InsufficientCardBalanceException extends Exception {

	private static final long serialVersionUID = 109088030990216320L;

	public InsufficientCardBalanceException(String message) {
        super(message);
    }

}
