package entities;

import static org.junit.Assert.assertEquals;
import static settings.constants.ConfigProperty.FARE_PRECISION_SCALE;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fare.Fare;
import settings.AppConfig;

public class FareTest {

	@Before
	public void setUp() {
		AppConfig.INST.setProperty(FARE_PRECISION_SCALE, String.valueOf(2));
	}

	@After
	public void tearDown() {
		AppConfig.INST.removeProperty(FARE_PRECISION_SCALE);
	}

	@Test
	public void maxReturns2_55WhenComparedTo1_25() {
		Fare f1 = Fare.New(2.55D);
		Fare f2 = Fare.New(1.25D);

		assertEquals(f1, f1.max(f2));
	}

	@Test
	public void maxReturns2_55WhenComparedTo2_55() {
		Fare f1 = Fare.New(2.55D);
		Fare f2 = Fare.New(2.55D);

		assertEquals(f1, f1.max(f2));
	}

	@Test
	public void maxReturns2_55WhenComparedTo0_00() {
		Fare f1 = Fare.New(0.00D);
		Fare f2 = Fare.New(2.55D);

		assertEquals(f2, f1.max(f2));
	}
}
