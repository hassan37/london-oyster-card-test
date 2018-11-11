package fare;

import settings.BusJourneyFare;
import trip.Trip;

public class BusJourneyFareCalculator implements FareCalculator {

	private BusJourneyFareCalculator() { }

	public static FareCalculator New() {
		return new BusJourneyFareCalculator();
	}

	@Override
	public Fare calculate(Trip trip) {
		return BusJourneyFare.INST.getFare();
	}

}
