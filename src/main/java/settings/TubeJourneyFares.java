package settings;

import static settings.constants.ConfigProperty.TUBE_JOURNEY_FARES;
import static settings.constants.Separator.COLON;
import static settings.constants.Separator.PIPE;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fare.Fare;

public enum TubeJourneyFares {

	INST;

	private final Map<Zones, Fare> DICT;
	private Fare maxFare;

	private TubeJourneyFares() {
		DICT = new ConcurrentHashMap<>(7);
		maxFare = Fare.New(0.00D);
	}

	public void init() {
		String property = AppConfig.INST.getProperty(TUBE_JOURNEY_FARES);
		String pipeSep = "\\" + PIPE.val;
		String[] zoneWiseFares = property.split(pipeSep);

		for (String zf : zoneWiseFares) {
			String[] zonesFare = zf.split(COLON.val);
			Zones zones = Zones.newSetOfZones(zonesFare[0]);
			Fare fare = Fare.New(Double.parseDouble(zonesFare[1]));

			DICT.put(zones, fare);
			maxFare = maxFare.max(fare);
		}
	}

	public Fare getFare(Zones zones) {
		return DICT.get(zones);
	}

	public Fare getMaxFare() {
		return maxFare;
	}

	public void clear() {
		DICT.clear();
	}


}
