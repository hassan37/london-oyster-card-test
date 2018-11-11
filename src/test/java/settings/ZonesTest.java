package settings;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import settings.constants.Zone;

public class ZonesTest {

	@Test
	public void returnsSetOfZonesOneTwoAndThree() {
		String zone = "1,2,3";
		Set<Zone> zonesSet = new HashSet<>(3);
		zonesSet.add(Zone.ONE);
		zonesSet.add(Zone.TWO);
		zonesSet.add(Zone.THREE);

		assertEquals(zonesSet, Zones.newSetOfZones(zone).getZones());
	}

	@Test
	public void returnsSetOfZoneTwo() {
		String zone = "2";
		Set<Zone> zonesSet = new HashSet<>(3);
		zonesSet.add(Zone.TWO);

		assertEquals(zonesSet, Zones.newSetOfZones(zone).getZones());
	}
}
