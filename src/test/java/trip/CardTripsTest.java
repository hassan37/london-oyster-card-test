package trip;

import static org.junit.Assert.assertEquals;
import static settings.constants.ConfigProperty.FARE_PRECISION_SCALE;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import card.Card;
import settings.AppConfig;
import settings.constants.Station;
import trip.Trip.Direction;
import trip.Trip.JourneyType;

public class CardTripsTest {

	@Before
	public void setUp() {
		AppConfig.INST.setProperty(FARE_PRECISION_SCALE, String.valueOf(2));
	}

	@After
	public void tearDown() {
		AppConfig.INST.removeProperty(FARE_PRECISION_SCALE);
	}

	@Test
	public void testMostRecentTripInformation() {
		Card card = Card.Builder.New().balance(2500D).build();
		Trip tripIn = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.IN)
			.card(card)
			.station(Station.EARLS_COURT)
			.build();

		Trip tripOut = Trip.Builder.New()
				.journeyType(JourneyType.TUBE)
				.direction(Direction.OUT)
				.card(card)
				.station(Station.EARLS_COURT)
				.build();

		CardTrips.INST.addNewTrip(tripIn);
		CardTrips.INST.addNewTrip(tripOut);

		assertEquals(Boolean.TRUE, CardTrips.INST.getTrips(card.number).isMostRecentTripCheckOut());
		assertEquals(Boolean.TRUE, CardTrips.INST.getTrips(card.number).isMostRecentTripOnTube());
		assertEquals(Station.EARLS_COURT, CardTrips.INST.getTrips(card.number).mostRecentTrip().station);
		assertEquals(2, CardTrips.INST.getTrips(card.number).total());
	}

}
