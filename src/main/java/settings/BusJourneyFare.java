package settings;

import fare.Fare;
import settings.constants.ConfigProperty;

public enum BusJourneyFare {

	INST;

	private Fare fare;

	public Fare getFare() {
		if (null == fare)
			initFare();

		return fare;
	}

	private void initFare() {
		String bjfProp = AppConfig.INST.getProperty(ConfigProperty.BUS_JOURNEY_FARE);
		fare = Fare.New(Double.valueOf(bjfProp));
	}
}
