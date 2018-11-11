package settings.constants;

import static org.junit.Assert.*;

import org.junit.Test;

public class StationTest {

	@Test
	public void returnsEarlsCourtStationByString() {
		String name = "Earl's Court";
		assertEquals(Station.EARLS_COURT, Station.getBy(name));
	}

	@Test
	public void returnsHolbornStationByString() {
		String name = "Holborn";
		assertEquals(Station.HOLBORN, Station.getBy(name));
	}
}
