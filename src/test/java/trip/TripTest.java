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

public class TripTest {

	private JourneyType jt;
	private Direction dir;
	private Card card;
	private Station station;

	@Before
	public void setUp() {
		AppConfig.INST.setProperty(FARE_PRECISION_SCALE, String.valueOf(2));
		jt		= JourneyType.TUBE;
		dir		= Direction.IN;
		card	= Card.Builder.New().balance(2500D).build();
		station	= Station.EARLS_COURT;
	}

	@After
	public void tearDown() {
		AppConfig.INST.removeProperty(FARE_PRECISION_SCALE);
		jt		= null;
		dir		= null;
		card	= null;
		station	= null;
	}

	@Test(expected = NullPointerException.class)
	public void failToCreateATripWithNullData() {
		Trip.Builder.New().build();
	}

	@Test(expected = NullPointerException.class)
	public void failToCreateATripWithNullDirection() {
		Trip.Builder.New()
		.journeyType(jt)
		.card(card)
		.station(station)
		.build();
	}

	@Test(expected = NullPointerException.class)
	public void failToCreateATripWithNullJourneyType() {
		Trip.Builder.New()
		.direction(dir)
		.card(card)
		.station(station)
		.build();
	}

	@Test(expected = NullPointerException.class)
	public void failToCreateATripWithNullCard() {
		Trip.Builder.New()
		.direction(dir)
		.journeyType(jt)
		.station(station)
		.build();
	}

	@Test(expected = NullPointerException.class)
	public void failToCreateATripWithNullStation() {
		Trip.Builder.New()
		.direction(dir)
		.card(card)
		.journeyType(jt)
		.build();
	}

	@Test
	public void createsASuccessfulTrip() {
		Trip trip = Trip.Builder.New()
		.direction(dir)
		.journeyType(jt)
		.station(station)
		.card(card)
		.build();

		assertEquals(dir, trip.direction);
		assertEquals(jt, trip.journeyType);
		assertEquals(card, trip.card);
		assertEquals(station, trip.station);
	}
}
