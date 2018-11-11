package fare;

import java.util.List;

import exceptions.InvalidJourneyException;
import settings.StationsToZonesMapper;
import settings.TubeJourneyFares;
import settings.Zones;
import settings.constants.Station;
import trip.CardTrips;
import trip.Trip;

public class TubeJourneyZonesFaresCalculator implements FareCalculator {

	private TubeJourneyZonesFaresCalculator() { }

	public static FareCalculator New() {
		return new TubeJourneyZonesFaresCalculator();
	}

	@Override
	public Fare calculate(Trip currentTrip) throws InvalidJourneyException {
		CardTrips cardTripsStore = CardTrips.INST;
		Fare maxFareForNewCheckIn = TubeJourneyFares.INST.getMaxFare();
		if(cardTripsStore.noTripOnCard(currentTrip.card.number)) {
			return maxFareForNewCheckIn;
		}

		Trip mostRecentTrip = cardTripsStore.getTrips(currentTrip.card.number).mostRecentTrip();
		if (currentTrip.isCheckingOut() && mostRecentTrip.isCheckingInToTube())
			return getFareForCheckInAndOutTrip(mostRecentTrip.station, currentTrip.station);

		if (currentTrip.isCheckingIn())
			return maxFareForNewCheckIn;

		throw new InvalidJourneyException("Invalid Journey. Alarm!!!!. Police. How did you get in?. Details: " + currentTrip.toString());
	}

	private Fare getFareForCheckInAndOutTrip(Station from, Station to) throws InvalidJourneyException {
		List<Zones> travelledZones = StationsToZonesMapper.INST.map(from, to);

		if (travelledZones.size() < 1)
			throw new InvalidJourneyException("Invalid Journey. Can not find any zone for Staion [" + from + "] and Station [" + to + "].");

		Fare minFare = Fare.New(Double.MAX_VALUE);
		for (Zones zones : travelledZones) {
			Fare fare = TubeJourneyFares.INST.getFare(zones);
			if (null == fare)
				throw new InvalidJourneyException("Invalid Journey. Can not find any fare for Staion [" + from + "] and Station [" + to + "].");

			minFare = fare.min(minFare);
		}

		return minFare;
	}
}
