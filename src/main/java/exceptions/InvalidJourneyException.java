package exceptions;

public class InvalidJourneyException extends Exception {

	private static final long serialVersionUID = -7867462553603764800L;

	public InvalidJourneyException(String message) {
        super(message);
    }

}
