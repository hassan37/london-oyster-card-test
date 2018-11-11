package fare;

import exceptions.InvalidJourneyException;
import trip.Trip;

public interface FareCalculator {

	Fare calculate(Trip trip) throws InvalidJourneyException;

}
