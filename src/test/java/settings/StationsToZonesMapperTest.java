package settings;

import static org.junit.Assert.assertEquals;
import static settings.constants.ConfigProperty.STATIONS_WITH_ZONES;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import settings.constants.Station;
import settings.constants.Zone;

public class StationsToZonesMapperTest {

	private final String STATION_ZONES = "Holborn:1|Earl's Court:1,2|Wimbledon:3|Hammersmith:2";

	@Before
	public void setUp() {
		AppConfig.INST.setProperty(STATIONS_WITH_ZONES, STATION_ZONES);
		StationZones.INST.init();
	}

	@After
	public void tearDown() {
		AppConfig.INST.removeProperty(STATIONS_WITH_ZONES);
		StationZones.INST.clear();
	}

	@Test
	public void mapsToZones_1_For_AnywhereInZone_1() {
		Zone fz = Zone.ONE;
		Zone tz = fz;
		Zones expected = Zones.New().add(fz);

		assertEquals(expected, StationsToZonesMapper.INST.mapFarewiseZones(fz, tz));
	}

	@Test
	public void mapsToZones_2_OR_3_For_isAnyOneZoneOutsideZone1() {
		Zones expectedZones_2 = Zones.New().add(Zone.TWO);
		Zones expectedZones_3 = Zones.New().add(Zone.THREE);

		assertEquals(expectedZones_2, StationsToZonesMapper.INST.mapFarewiseZones(Zone.TWO, Zone.TWO));
		assertEquals(expectedZones_3, StationsToZonesMapper.INST.mapFarewiseZones(Zone.THREE, Zone.THREE));
	}

	@Test
	public void mapsToZones_1_AND_2_OR_1_AND_3_For_isAny2ZonesIncludingZone1() {
		Zones expectedZones_1_AND_2 = Zones.New().add(Zone.TWO).add(Zone.ONE);

		assertEquals(expectedZones_1_AND_2, StationsToZonesMapper.INST.mapFarewiseZones(Zone.ONE, Zone.TWO));
	}

	@Test
	public void mapsToZones_2_AND_3_For_isAny2ZonesExcludingZone1() {
		Zones expectedZones_2_AND_3 = Zones.New().add(Zone.TWO).add(Zone.THREE);

		assertEquals(expectedZones_2_AND_3, StationsToZonesMapper.INST.mapFarewiseZones(Zone.THREE, Zone.TWO));
	}

	@Test
	public void mapsToZones_1_AND2_AND_3_For_isAnyThreeZones() {
		Zones expectedZones_1_AND_2_AND_3 = Zones.New().add(Zone.ONE).add(Zone.TWO).add(Zone.THREE);

		assertEquals(expectedZones_1_AND_2_AND_3, StationsToZonesMapper.INST.mapFarewiseZones(Zone.ONE, Zone.THREE));
		assertEquals(expectedZones_1_AND_2_AND_3, StationsToZonesMapper.INST.mapFarewiseZones(Zone.THREE, Zone.ONE));
	}

	@Test
	public void testJourneyZonesForTravelingFromHolbornToEarlsCourt() {
		Station fs = Station.HOLBORN;
		Station ts = Station.EARLS_COURT;

		Zones z1 = Zones.New().add(Zone.ONE);
		Zones z1_2 = Zones.New().add(Zone.ONE).add(Zone.TWO);
		List<Zones> expected = new ArrayList<>(2);
		expected.add(z1_2);
		expected.add(z1);

		assertEquals(expected, StationsToZonesMapper.INST.map(fs, ts));
	}

	@Test
	public void testJourneyZonesForTravelingFromHolbornToWimbeldon() {
		Station fs = Station.HOLBORN;
		Station ts = Station.WIMBLEDON;

		Zones z1_2_3 = Zones.New().add(Zone.ONE).add(Zone.TWO).add(Zone.THREE);
		List<Zones> expected = new ArrayList<>(1);
		expected.add(z1_2_3);

		assertEquals(expected, StationsToZonesMapper.INST.map(fs, ts));
	}

	@Test
	public void testJourneyZonesForTravelingFromHolbornToHammerSmith() {
		Station fs = Station.HOLBORN;
		Station ts = Station.HAMMERSMITH;

		Zones z1_2 = Zones.New().add(Zone.ONE).add(Zone.TWO);
		List<Zones> expected = new ArrayList<>(1);
		expected.add(z1_2);

		assertEquals(expected, StationsToZonesMapper.INST.map(fs, ts));
	}

	@Test
	public void testJourneyZonesForTravelingFromWimbeldonToHammerSmith() {
		Station fs = Station.WIMBLEDON;
		Station ts = Station.HAMMERSMITH;

		Zones z2_3 = Zones.New().add(Zone.TWO).add(Zone.THREE);
		List<Zones> expected = new ArrayList<>(1);
		expected.add(z2_3);

		assertEquals(expected, StationsToZonesMapper.INST.map(fs, ts));
	}
}
