package journey;

import static org.junit.Assert.assertEquals;
import static settings.constants.ConfigProperty.FARE_PRECISION_SCALE;
import static settings.constants.ConfigProperty.STATIONS_WITH_ZONES;
import static settings.constants.ConfigProperty.TUBE_JOURNEY_FARES;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import card.Card;
import card.CardBalance;
import exceptions.InvalidJourneyException;
import settings.AppConfig;
import settings.StationZones;
import settings.TubeJourneyFares;
import settings.constants.Station;
import trip.CardTrips;
import trip.Trip;
import trip.Trip.Direction;
import trip.Trip.JourneyType;

public class TubeJourneyTest {

	private final String ZONES_WISE_FARES = "1:2.50|2:2.00|3:2.00|1,2:3.00|2,3:2.25|1,2,3:3.20";
	private final String STATION_ZONES = "Holborn:1|Earl's Court:1,2|Wimbledon:3|Hammersmith:2";

	@Before
	public void setUp() {
		CardTrips.INST.clear();

		AppConfig.INST.setProperty(FARE_PRECISION_SCALE, String.valueOf(2));
		AppConfig.INST.setProperty(STATIONS_WITH_ZONES, STATION_ZONES);
		AppConfig.INST.setProperty(TUBE_JOURNEY_FARES, ZONES_WISE_FARES);

		StationZones.INST.init();
		TubeJourneyFares.INST.init();
	}

	@After
	public void tearDown() {
		AppConfig.INST.removeProperty(TUBE_JOURNEY_FARES);
		AppConfig.INST.removeProperty(FARE_PRECISION_SCALE);
		AppConfig.INST.removeProperty(STATIONS_WITH_ZONES);

		TubeJourneyFares.INST.clear();
		StationZones.INST.clear();
		CardTrips.INST.clear();
	}

	@Test
	public void testNewJourney_for_newCheck_IN_At_Earlscourt() throws InvalidJourneyException {
		CardTrips.INST.clear();
		Card card = Card.Builder.New().balance(2500D).build();
		Trip tripIn = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.IN)
			.card(card)
			.station(Station.EARLS_COURT)
			.build();
		Journey tj = TubeJourney.startNewJourney(tripIn);
		Trip updatedTrip = tj.commute();

		Card updatedCard = Card.Builder.newUpdatedCard(updatedTrip.card, CardBalance.New(2496.80D));
		Trip expectedTrip = Trip.Builder.newUpdatedTrip(tripIn, updatedCard);

		assertEquals(expectedTrip, updatedTrip);
		assertEquals(1, CardTrips.INST.getTrips(expectedTrip.card.number).total());
	}

	@Test
	public void testJourney_for_Check_OUT_At_Earlscourt() throws InvalidJourneyException {
		CardTrips.INST.clear();
		Card card = Card.Builder.New().balance(2500D).build();
		Trip tripIn = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.IN)
			.card(card)
			.station(Station.EARLS_COURT)
			.build();
		Journey tji = TubeJourney.startNewJourney(tripIn);
		Trip updatedTripIn = tji.commute();
//-------------
		Trip tripOut = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.OUT)
			.card(updatedTripIn.card)
			.station(Station.EARLS_COURT)
			.build();
		Journey tjo = TubeJourney.startNewJourney(tripOut);
		Trip updatedTripOut = tjo.commute();

		Card updatedCard = Card.Builder.newUpdatedCard(updatedTripOut.card, CardBalance.New(2498.00D));
		Trip expectedTrip = Trip.Builder.newUpdatedTrip(tripOut, updatedCard);
//-------------

		assertEquals(expectedTrip, updatedTripOut);
		assertEquals(2, CardTrips.INST.getTrips(expectedTrip.card.number).total());
	}
}
