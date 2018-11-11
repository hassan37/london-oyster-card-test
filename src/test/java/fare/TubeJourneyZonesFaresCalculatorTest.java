package fare;

import static org.junit.Assert.assertEquals;
import static settings.constants.ConfigProperty.FARE_PRECISION_SCALE;
import static settings.constants.ConfigProperty.STATIONS_WITH_ZONES;
import static settings.constants.ConfigProperty.TUBE_JOURNEY_FARES;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import card.Card;
import exceptions.InvalidJourneyException;
import settings.AppConfig;
import settings.StationZones;
import settings.TubeJourneyFares;
import settings.Zones;
import settings.constants.Station;
import settings.constants.Zone;
import trip.CardTrips;
import trip.Trip;
import trip.Trip.Direction;
import trip.Trip.JourneyType;

public class TubeJourneyZonesFaresCalculatorTest {

	private final String ZONES_WISE_FARES = "1:2.50|2:2.00|3:2.00|1,2:3.00|2,3:2.25|1,2,3:3.20";
	private final String STATION_ZONES = "Holborn:1|Earl's Court:1,2|Wimbledon:3|Hammersmith:2";

	private FareCalculator tubeJourneyFareCalc;
	private Card card;

	@Before
	public void setUp() {
		AppConfig.INST.setProperty(FARE_PRECISION_SCALE, String.valueOf(2));

		AppConfig.INST.setProperty(STATIONS_WITH_ZONES, STATION_ZONES);
		StationZones.INST.init();

		AppConfig.INST.setProperty(TUBE_JOURNEY_FARES, ZONES_WISE_FARES);
		TubeJourneyFares.INST.init();

		this.tubeJourneyFareCalc = TubeJourneyZonesFaresCalculator.New();
		card = Card.Builder.New().balance(2500D).build();
	}

	@After
	public void tearDown() {
		AppConfig.INST.removeProperty(TUBE_JOURNEY_FARES);
		AppConfig.INST.removeProperty(FARE_PRECISION_SCALE);
		TubeJourneyFares.INST.clear();

		AppConfig.INST.removeProperty(STATIONS_WITH_ZONES);
		StationZones.INST.clear();

		this.card = null;
	}

	@Test
	public void calculatesFare_MAX_For_Checking_IN_to_Holborn() throws InvalidJourneyException {
		Trip tripIn = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.IN)
			.card(card)
			.station(Station.EARLS_COURT)
			.build();

		Fare fare = this.tubeJourneyFareCalc.calculate(tripIn);

		assertEquals(TubeJourneyFares.INST.getMaxFare(), fare);
	}

	@Test
	public void calculatesFare_MIN_For_JourneyFrom_Holborn_to_EarlCourt() throws InvalidJourneyException {
		Trip tripIn = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.IN)
			.card(card)
			.station(Station.HOLBORN)
			.build();
		CardTrips.INST.addNewTrip(tripIn);

		Trip tripOut = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.OUT)
			.card(card)
			.station(Station.EARLS_COURT)
			.build();

		Fare fare = this.tubeJourneyFareCalc.calculate(tripOut);
		Zones z1 = Zones.New().add(Zone.ONE);
		Fare expected = TubeJourneyFares.INST.getFare(z1);

		assertEquals(expected, fare);
	}

	@Test
	public void calculatesZone_1_AND_2_Fare_For_JourneyFrom_Holborn_to_Hammersmith() throws InvalidJourneyException {
		Trip tripIn = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.IN)
			.card(card)
			.station(Station.HOLBORN)
			.build();
		CardTrips.INST.addNewTrip(tripIn);

		Trip tripOut = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.OUT)
			.card(card)
			.station(Station.HAMMERSMITH)
			.build();

		Fare fare = this.tubeJourneyFareCalc.calculate(tripOut);
		Zones z1_2 = Zones.New().add(Zone.ONE).add(Zone.TWO);
		Fare expected = TubeJourneyFares.INST.getFare(z1_2);

		assertEquals(expected, fare);
	}

	@Test
	public void calculatesZone_1_AND_2_AND_3Fare_For_JourneyFrom_Holborn_to_Wimbeldon() throws InvalidJourneyException {
		Trip tripIn = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.IN)
			.card(card)
			.station(Station.WIMBLEDON)
			.build();
		CardTrips.INST.addNewTrip(tripIn);

		Trip tripOut = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.OUT)
			.card(card)
			.station(Station.HOLBORN)
			.build();

		Fare fare = this.tubeJourneyFareCalc.calculate(tripOut);
		Zones z1_2_3 = Zones.New().add(Zone.ONE).add(Zone.TWO).add(Zone.THREE);
		Fare expected = TubeJourneyFares.INST.getFare(z1_2_3);

		assertEquals(expected, fare);
	}

	@Test
	public void calculatesZone_2_AND_3Fare_For_JourneyFrom_Hammersmit_to_Wimbeldon() throws InvalidJourneyException {
		Trip tripIn = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.IN)
			.card(card)
			.station(Station.WIMBLEDON)
			.build();
		CardTrips.INST.addNewTrip(tripIn);

		Trip tripOut = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.OUT)
			.card(card)
			.station(Station.HAMMERSMITH)
			.build();

		Fare fare = this.tubeJourneyFareCalc.calculate(tripOut);
		Zones z2_3 = Zones.New().add(Zone.TWO).add(Zone.THREE);
		Fare expected = TubeJourneyFares.INST.getFare(z2_3);

		assertEquals(expected, fare);
	}

	@Test
	public void calculatesZone_2Fare_For_JourneyFrom_Hammersmit_to_EarlsCourt() throws InvalidJourneyException {
		Trip tripIn = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.IN)
			.card(card)
			.station(Station.EARLS_COURT)
			.build();
		CardTrips.INST.addNewTrip(tripIn);

		Trip tripOut = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.OUT)
			.card(card)
			.station(Station.HAMMERSMITH)
			.build();

		Fare fare = this.tubeJourneyFareCalc.calculate(tripOut);
		Zones z2 = Zones.New().add(Zone.TWO);
		Fare expected = TubeJourneyFares.INST.getFare(z2);

		assertEquals(expected, fare);
	}

	@Test
	public void calculatesZone_3_2Fare_For_JourneyFrom_Wimbledon_to_EarlsCourt() throws InvalidJourneyException {
		Trip tripIn = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.IN)
			.card(card)
			.station(Station.EARLS_COURT)
			.build();
		CardTrips.INST.addNewTrip(tripIn);

		Trip tripOut = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.OUT)
			.card(card)
			.station(Station.WIMBLEDON)
			.build();

		Fare fare = this.tubeJourneyFareCalc.calculate(tripOut);
		Zones z2_3 = Zones.New().add(Zone.TWO).add(Zone.THREE);
		Fare expected = TubeJourneyFares.INST.getFare(z2_3);

		assertEquals(expected, fare);
	}

	@Test
	public void calculatesZoneFare_For_JourneyWithinEarlsCourt() throws InvalidJourneyException {
		Trip tripIn = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.IN)
			.card(card)
			.station(Station.EARLS_COURT)
			.build();
		CardTrips.INST.addNewTrip(tripIn);

		Trip tripOut = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.OUT)
			.card(card)
			.station(Station.EARLS_COURT)
			.build();

		Fare fare = this.tubeJourneyFareCalc.calculate(tripOut);
		Zones z2 = Zones.New().add(Zone.TWO);
		Fare expected = TubeJourneyFares.INST.getFare(z2);

		assertEquals(expected, fare);
	}

	@Test
	public void calculatesZoneFare_For_JourneyWithinWimbledon() throws InvalidJourneyException {
		Trip tripIn = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.IN)
			.card(card)
			.station(Station.WIMBLEDON)
			.build();
		CardTrips.INST.addNewTrip(tripIn);

		Trip tripOut = Trip.Builder.New()
			.journeyType(JourneyType.TUBE)
			.direction(Direction.OUT)
			.card(card)
			.station(Station.WIMBLEDON)
			.build();

		Fare fare = this.tubeJourneyFareCalc.calculate(tripOut);
		Zones z3 = Zones.New().add(Zone.THREE);
		Fare expected = TubeJourneyFares.INST.getFare(z3);

		assertEquals(expected, fare);
	}
}
