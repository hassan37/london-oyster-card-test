package settings.constants;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZoneTest {

	@Test
	public void returnsZoneOneByString() {
		String name = "1";
		assertEquals(Zone.ONE, Zone.getBy(name));
	}

	@Test
	public void returnsZoneThreeByString() {
		String name = "3";
		assertEquals(Zone.THREE, Zone.getBy(name));
	}

}
