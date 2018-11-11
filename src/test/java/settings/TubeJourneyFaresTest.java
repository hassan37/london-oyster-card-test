package settings;

import static org.junit.Assert.assertEquals;
import static settings.constants.ConfigProperty.FARE_PRECISION_SCALE;
import static settings.constants.ConfigProperty.TUBE_JOURNEY_FARES;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fare.Fare;

public class TubeJourneyFaresTest {

	private final String ZONES_WISE_FARES = "1:2.50|2:2.00|3:2.00|1,2:3.00|2,3:2.25|1,2,3:3.20";

	@Before
	public void setUp() {
		AppConfig.INST.setProperty(FARE_PRECISION_SCALE, String.valueOf(2));
		AppConfig.INST.setProperty(TUBE_JOURNEY_FARES, ZONES_WISE_FARES);
		TubeJourneyFares.INST.init();
	}

	@After
	public void tearDown() {
		AppConfig.INST.removeProperty(TUBE_JOURNEY_FARES);
		AppConfig.INST.removeProperty(FARE_PRECISION_SCALE);
		TubeJourneyFares.INST.clear();
	}

	@Test
	public void returns_2_50_forZoneOne() {
		Zones zones = Zones.newSetOfZones("1");
		Fare expectedFare = Fare.New(2.50D);

		assertEquals(expectedFare, TubeJourneyFares.INST.getFare(zones));
	}

	@Test
	public void returns_2_00_forZoneTwoAndThree() {
		Zones zone2 = Zones.newSetOfZones("2");
		Zones zone3 = Zones.newSetOfZones("3");
		Fare expectedFare = Fare.New(2.00D);

		assertEquals(expectedFare, TubeJourneyFares.INST.getFare(zone2));
		assertEquals(expectedFare, TubeJourneyFares.INST.getFare(zone3));
	}

	@Test
	public void returns_3_00_forZoneOneTwoAndOneThree() {
		Zones zone1_2 = Zones.newSetOfZones("1,2");
		Fare expectedFare = Fare.New(3.00D);

		assertEquals(expectedFare, TubeJourneyFares.INST.getFare(zone1_2));
	}

	@Test
	public void returns_2_25_forZoneTwoAndThree() {
		Zones zone2_3 = Zones.newSetOfZones("2,3");
		Fare expectedFare = Fare.New(2.25D);

		assertEquals(expectedFare, TubeJourneyFares.INST.getFare(zone2_3));
	}

	@Test
	public void returns_3_20_forZoneOneAndTwoAndThree() {
		Zones zone1_2_3 = Zones.newSetOfZones("1,2,3");
		Fare expectedFare = Fare.New(3.20D);

		assertEquals(expectedFare, TubeJourneyFares.INST.getFare(zone1_2_3));
	}

}
