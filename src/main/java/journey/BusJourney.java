package journey;

import card.Card;
import card.CardBalance;
import card.CardNumber;
import exceptions.InsufficientCardBalanceException;
import exceptions.InvalidJourneyException;
import fare.BusJourneyFareCalculator;
import fare.Fare;
import fare.FareCalculator;
import settings.BusJourneyFare;
import trip.CardTrips;
import trip.Trip;
import trip.Trips;

public class BusJourney implements Journey {

	private final Trip trip;
	private final Fare maxFare;
	private final FareCalculator fareCalc;
	private final CardTrips cardTripsStore;
	private final CardNumber cardNumber;
	private final boolean cardHasTrips;
	private final Trips cardTrips;

	private BusJourney(Trip trip) {
		this.trip = trip;
		maxFare = BusJourneyFare.INST.getFare();
		fareCalc = BusJourneyFareCalculator.New();
		cardTripsStore = CardTrips.INST;
		this.cardNumber = trip.card.number;
		this.cardTrips = cardTripsStore.getTrips(cardNumber);
		this.cardHasTrips = cardTripsStore.isAnyTripOnCard(cardNumber);
	}

	public static Journey startNewJourney(Trip trip) {
		return new BusJourney(trip);
	}

	@Override
	public Trip commute() throws InvalidJourneyException {
		if(trip.isCheckingIn() || checkingOutWithNoCheckingIn()) {
			Fare journeyFare = fareCalc.calculate(trip);
			CardBalance newBalance = trip.card.amount.debit(journeyFare.value);
			Card updatedCard = Card.Builder.newUpdatedCard(trip.card, newBalance);
			Trip debitedTrip = Trip.Builder.newUpdatedTrip(trip, updatedCard);

			cardTripsStore.addNewTrip(debitedTrip);

			return debitedTrip;
		}

		if(trip.isCheckingOut() && cardHasTrips && cardTrips.mostRecentTrip().isCheckingInToBus()) {
			cardTripsStore.addNewTrip(trip);

			return trip;
		}

		throw new InvalidJourneyException("Invalid Journey. Alarm!!!!. Police. Details: " + trip.toString());
	}


	private boolean checkingOutWithNoCheckingIn() {
		if (trip.isCheckingOut() && !cardHasTrips)
			return Boolean.TRUE;

		return trip.isCheckingOut() && 
				(cardHasTrips && !cardTrips.mostRecentTrip().isCheckingInToBus());
	}

	@Override
	public void cardHasSufficientBalance() throws InsufficientCardBalanceException {
		if (trip.card.zeroBalance()) {
			throw new InsufficientCardBalanceException("Zero Balance. Please recharge your card.");
		}

		if(maxFare.value.compareTo(trip.card.amount.value) > 0) {
			throw new InsufficientCardBalanceException("Insufficient Balance. Minimum Balance: " + maxFare + ". Your balance is: " + trip.card.getBalance());
		}
	}

}
