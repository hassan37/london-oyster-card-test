package settings;

import static org.junit.Assert.assertEquals;
import static settings.constants.ConfigProperty.STATIONS_WITH_ZONES;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import settings.constants.Station;
import settings.constants.Zone;

public class StationZonesTest {

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
	public void returnsZoneOneAndTwoForEarlsCountStation() {
		Zones zones = StationZones.INST.getZones(Station.EARLS_COURT);
		Set<Zone> expectedZones = new HashSet<>(2);
		expectedZones.add(Zone.ONE);
		expectedZones.add(Zone.TWO);

		assertEquals(expectedZones, zones.getZones());
	}

	@Test
	public void returnsZoneOneForHolbornStation() {
		Zones zones = StationZones.INST.getZones(Station.HOLBORN);
		Set<Zone> expectedZones = new HashSet<>(2);
		expectedZones.add(Zone.ONE);

		assertEquals(expectedZones, zones.getZones());
	}

	@Test
	public void returnsZoneTwoForHammersmithStation() {
		Zones zones = StationZones.INST.getZones(Station.HAMMERSMITH);
		Set<Zone> expectedZones = new HashSet<>(2);
		expectedZones.add(Zone.TWO);

		assertEquals(expectedZones, zones.getZones());
	}

	@Test
	public void returnsZoneThreeForWimbeldonStation() {
		Zones zones = StationZones.INST.getZones(Station.WIMBLEDON);
		Set<Zone> expectedZones = new HashSet<>(2);
		expectedZones.add(Zone.THREE);

		assertEquals(expectedZones, zones.getZones());
	}
}
