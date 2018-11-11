package app;

import java.io.IOException;

import card.Card;
import exceptions.InvalidJourneyException;
import journey.BusJourney;
import journey.Journey;
import journey.TubeJourney;
import settings.AppConfig;
import settings.StationZones;
import settings.TubeJourneyFares;
import settings.constants.Station;
import trip.Trip;
import trip.Trip.Direction;
import trip.Trip.JourneyType;

public class OysterCardApplication {

	public static void main(String[] args) throws IOException, InvalidJourneyException {
		OysterCardApplication alefTest = new OysterCardApplication();

		alefTest.initializeProperties();
		Card card = alefTest.createNewCard();
		
		Trip tubeHolbornIn = alefTest.tubeTravel(Station.HOLBORN, Direction.IN, card);
		System.out.println("New Card Balance is:" + tubeHolbornIn.card.amount.value + ". TUBE - HOLBORN - IN.\n---");

		Trip tubeECOut = alefTest.tubeTravel(Station.EARLS_COURT, Direction.OUT, tubeHolbornIn.card);
		System.out.println("New Card Balance is:" + tubeECOut.card.amount.value + ". TUBE - EARLS_COURT - OUT.\n---");

		Trip busECIn = alefTest.busTravel(Station.EARLS_COURT, Direction.IN, tubeECOut.card);
		System.out.println("New Card Balance is:" + busECIn.card.amount.value + ". BUS - EARLS_COURT - IN.\n---");

		Trip busChelseaOut = alefTest.busTravel(Station.CHELSEA, Direction.OUT, busECIn.card);
		System.out.println("New Card Balance is:" + busChelseaOut.card.amount.value + ". BUS - CHELSEA - OUT.\n---");

		Trip tubeECIn = alefTest.tubeTravel(Station.EARLS_COURT, Direction.IN, busChelseaOut.card);
		System.out.println("New Card Balance is:" + tubeECIn.card.amount.value + ". TUBE - EARLS_COURT - IN.\n---");

		Trip tubeHSOut = alefTest.tubeTravel(Station.HAMMERSMITH, Direction.OUT, tubeECIn.card);
		System.out.println("New Card Balance is:" + tubeHSOut.card.amount.value + ". TUBE - HAMMERSMITH - OUT.");

		System.out.println("--- THE END. ---");
}

	private void initializeProperties() throws IOException {
		AppConfig.INST.load();
		StationZones.INST.init();
		TubeJourneyFares.INST.init();
	}

	private Card createNewCard() {
		Card card = Card.Builder.New().balance(30.00D).build();

		return card;
	}

	private Trip tubeTravel(Station st, Direction dir, Card card) throws InvalidJourneyException {
		Trip trip = trip(JourneyType.TUBE, st, dir, card);
		Journey j = TubeJourney.startNewJourney(trip);

		return j.commute();
	}

	private Trip trip(JourneyType jt, Station st, Direction dir, Card card) {
		Trip trip = Trip.Builder.New()
			.journeyType(jt)
			.station(st)
			.direction(dir)
			.card(card)
			.build();

		return trip;
	}

	private Trip busTravel(Station st, Direction dir, Card card) throws InvalidJourneyException {
		Trip trip = trip(JourneyType.BUS, st, dir, card);
		Journey j = BusJourney.startNewJourney(trip);

		return j.commute();
	}
}
