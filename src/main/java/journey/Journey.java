package journey;

import exceptions.InsufficientCardBalanceException;
import exceptions.InvalidJourneyException;
import trip.Trip;

public interface Journey {

	void cardHasSufficientBalance() throws InsufficientCardBalanceException;

	Trip commute() throws InvalidJourneyException;
}
