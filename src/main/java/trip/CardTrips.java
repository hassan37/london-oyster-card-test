package trip;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import card.CardNumber;

public enum CardTrips {
	INST;

	private final Map<CardNumber, Trips> DICT;

	private CardTrips() {
		DICT = new ConcurrentHashMap<>();
	}

	public void addNewTrip(Trip trip) {
		CardNumber cardNumber = trip.card.number;

		if (DICT.containsKey(cardNumber)) {
			DICT.get(cardNumber).add(trip);
		} else {
			Trips trips = Trips.newJourney(trip);

			DICT.put(cardNumber, trips);
		}
	}

	public Trips getTrips(CardNumber cardNumber) {
		return DICT.get(cardNumber);
	}

	public boolean isAnyTripOnCard(CardNumber cardNumber) {
		Trips cardTrips = DICT.get(cardNumber);
		return (null != cardTrips && cardTrips.total() > 0);
	}

	public boolean noTripOnCard(CardNumber cardNumber) {
		return !isAnyTripOnCard(cardNumber);
	}

	public void clear() {
		this.DICT.clear();
	}

}
