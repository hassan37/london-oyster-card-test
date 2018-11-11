package journey;

import card.Card;
import card.CardBalance;
import card.CardNumber;
import exceptions.InsufficientCardBalanceException;
import exceptions.InvalidJourneyException;
import fare.Fare;
import fare.FareCalculator;
import fare.TubeJourneyZonesFaresCalculator;
import settings.TubeJourneyFares;
import trip.CardTrips;
import trip.Trip;

public class TubeJourney implements Journey {

	private final Trip trip;
	private final Fare maxFare;
	private final FareCalculator fareCalc;
	private final CardTrips cardTripsStore;
	private final CardNumber cardNumber;

	private TubeJourney(Trip trip) {
		this.trip = trip;
		maxFare = TubeJourneyFares.INST.getMaxFare();
		fareCalc = TubeJourneyZonesFaresCalculator.New();
		cardTripsStore = CardTrips.INST;
		this.cardNumber = trip.card.number;
	}

	public static Journey startNewJourney(Trip trip) {
		return new TubeJourney(trip);
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

	@Override
	public Trip commute() throws InvalidJourneyException {
		Trip creditedTrip = creditMaxFareDeductionIfDue();
		Fare journeyFare = fareCalc.calculate(creditedTrip);

		CardBalance newBalance = creditedTrip.card.amount.debit(journeyFare.value);
		Card updatedCard = Card.Builder.newUpdatedCard(trip.card, newBalance);
		Trip debitedTrip = Trip.Builder.newUpdatedTrip(creditedTrip, updatedCard);
		cardTripsStore.addNewTrip(debitedTrip);

		return debitedTrip;
	}

	private Trip creditMaxFareDeductionIfDue() {
		if(cardTripsStore.noTripOnCard(cardNumber))
			return this.trip;

		Trip mostRecentTrip = cardTripsStore.getTrips(cardNumber).mostRecentTrip();
		if(trip.isCheckingOut() && mostRecentTrip.isCheckingInToTube()) {
			CardBalance newBalance = trip.card.amount.credit(maxFare.value);
			Card updatedCard = Card.Builder.newUpdatedCard(trip.card, newBalance);
			Trip updatedTrip = Trip.Builder.newUpdatedTrip(trip, updatedCard);
			System.out.println("New Card Balance is:" + updatedTrip.card.amount.value + ". MAX Fare Credited.\n---");

			return updatedTrip;
		}

		return this.trip;
	}
	
}
