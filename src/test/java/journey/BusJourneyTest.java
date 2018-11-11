package journey;

import static org.junit.Assert.assertEquals;
import static settings.constants.ConfigProperty.BUS_JOURNEY_FARE;
import static settings.constants.ConfigProperty.FARE_PRECISION_SCALE;
import static settings.constants.ConfigProperty.STATIONS_WITH_ZONES;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import card.Card;
import card.CardBalance;
import exceptions.InvalidJourneyException;
import settings.AppConfig;
import settings.StationZones;
import settings.constants.Station;
import trip.CardTrips;
import trip.Trip;
import trip.Trip.Direction;
import trip.Trip.JourneyType;

public class BusJourneyTest {

	private final String STATION_ZONES = "Holborn:1|Earl's Court:1,2|Wimbledon:3|Hammersmith:2";

	@Before
	public void setUp() {
		CardTrips.INST.clear();
		AppConfig.INST.setProperty(FARE_PRECISION_SCALE, String.valueOf(2));
		AppConfig.INST.setProperty(BUS_JOURNEY_FARE, String.valueOf(1.80D));
		AppConfig.INST.setProperty(STATIONS_WITH_ZONES, STATION_ZONES);
		StationZones.INST.init();
	}

	@After
	public void tearDown() {
		AppConfig.INST.removeProperty(BUS_JOURNEY_FARE);
		AppConfig.INST.removeProperty(FARE_PRECISION_SCALE);
		AppConfig.INST.removeProperty(STATIONS_WITH_ZONES);

		StationZones.INST.clear();
		CardTrips.INST.clear();
	}

	@Test
	public void testNewJourney_for_newCheck_IN_At_Earlscourt() throws InvalidJourneyException {
		CardTrips.INST.clear();
		Card card = Card.Builder.New().balance(2500D).build();
		Trip tripIn = Trip.Builder.New()
			.journeyType(JourneyType.BUS)
			.direction(Direction.IN)
			.card(card)
			.station(Station.EARLS_COURT)
			.build();
		Journey bj = BusJourney.startNewJourney(tripIn);
		Trip updatedTrip = bj.commute();

		Card updatedCard = Card.Builder.newUpdatedCard(card, CardBalance.New(2498.20D));
		Trip expectedTrip = Trip.Builder.newUpdatedTrip(tripIn, updatedCard);

		assertEquals(expectedTrip, updatedTrip);
		assertEquals(1, CardTrips.INST.getTrips(expectedTrip.card.number).total());
	}

	@Test
	public void testNewJourney_for_newCheck_OUT_At_Earlscourt() throws InvalidJourneyException {
		CardTrips.INST.clear();
		Card card = Card.Builder.New().balance(2500D).build();
		Trip tripIn = Trip.Builder.New()
			.journeyType(JourneyType.BUS)
			.direction(Direction.OUT)
			.card(card)
			.station(Station.EARLS_COURT)
			.build();
		Journey bj = BusJourney.startNewJourney(tripIn);
		Trip updatedTrip = bj.commute();

		Card updatedCard = Card.Builder.newUpdatedCard(card, CardBalance.New(2498.20D));
		Trip expectedTrip = Trip.Builder.newUpdatedTrip(tripIn, updatedCard);

		assertEquals(expectedTrip, updatedTrip);
		assertEquals(1, CardTrips.INST.getTrips(expectedTrip.card.number).total());
	}

	@Test
	public void testNewJourney_for_2Check_OUTs_At_Earlscourt() throws InvalidJourneyException {
		CardTrips.INST.clear();
		Card card = Card.Builder.New().balance(2500D).build();
		Trip tripOut_1 = Trip.Builder.New()
			.journeyType(JourneyType.BUS)
			.direction(Direction.OUT)
			.card(card)
			.station(Station.EARLS_COURT)
			.build();
		Journey bj = BusJourney.startNewJourney(tripOut_1);
		Trip updatedTrip_1 = bj.commute();
//-------------
		Trip tripOut_2 = Trip.Builder.New()
			.journeyType(JourneyType.BUS)
			.direction(Direction.OUT)
			.card(updatedTrip_1.card)
			.station(Station.EARLS_COURT)
			.build();
		Journey tjo = BusJourney.startNewJourney(tripOut_2);
		Trip updatedTrip_2 = tjo.commute();

		Card updatedCard = Card.Builder.newUpdatedCard(updatedTrip_2.card, CardBalance.New(2497.40D));
		Trip expectedTrip = Trip.Builder.newUpdatedTrip(tripOut_2, updatedCard);

		assertEquals(expectedTrip, updatedTrip_2);
		assertEquals(2, CardTrips.INST.getTrips(expectedTrip.card.number).total());
	}

	@Test
	public void testNewJourney_for_2Check_INs_At_Earlscourt() throws InvalidJourneyException {
		CardTrips.INST.clear();
		Card card = Card.Builder.New().balance(2500D).build();
		Trip tripOut_1 = Trip.Builder.New()
			.journeyType(JourneyType.BUS)
			.direction(Direction.IN)
			.card(card)
			.station(Station.EARLS_COURT)
			.build();
		Journey bj = BusJourney.startNewJourney(tripOut_1);
		Trip updatedTrip_1 = bj.commute();
//-------------
		Trip tripOut_2 = Trip.Builder.New()
			.journeyType(JourneyType.BUS)
			.direction(Direction.IN)
			.card(updatedTrip_1.card)
			.station(Station.EARLS_COURT)
			.build();
		Journey tjo = BusJourney.startNewJourney(tripOut_2);
		Trip updatedTrip_2 = tjo.commute();

		Card updatedCard = Card.Builder.newUpdatedCard(updatedTrip_2.card, CardBalance.New(2497.40D));
		Trip expectedTrip = Trip.Builder.newUpdatedTrip(tripOut_2, updatedCard);

		assertEquals(expectedTrip, updatedTrip_2);
		assertEquals(2, CardTrips.INST.getTrips(expectedTrip.card.number).total());
	}

	@Test
	public void testNewJourney_for_Check_IN_OUT_At_Earlscourt() throws InvalidJourneyException {
		CardTrips.INST.clear();
		Card card = Card.Builder.New().balance(2500D).build();
		Trip tripOut_1 = Trip.Builder.New()
			.journeyType(JourneyType.BUS)
			.direction(Direction.IN)
			.card(card)
			.station(Station.EARLS_COURT)
			.build();
		Journey bj = BusJourney.startNewJourney(tripOut_1);
		Trip updatedTrip_1 = bj.commute();
//-------------
		Trip tripOut_2 = Trip.Builder.New()
			.journeyType(JourneyType.BUS)
			.direction(Direction.OUT)
			.card(updatedTrip_1.card)
			.station(Station.EARLS_COURT)
			.build();
		Journey tjo = BusJourney.startNewJourney(tripOut_2);
		Trip updatedTrip_2 = tjo.commute();

		Card updatedCard = Card.Builder.newUpdatedCard(updatedTrip_2.card, CardBalance.New(2498.20D));
		Trip expectedTrip = Trip.Builder.newUpdatedTrip(tripOut_2, updatedCard);

		assertEquals(expectedTrip, updatedTrip_2);
		assertEquals(2, CardTrips.INST.getTrips(expectedTrip.card.number).total());
	}
}
