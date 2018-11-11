package settings;

import static settings.constants.ConfigProperty.STATIONS_WITH_ZONES;
import static settings.constants.Separator.COLON;
import static settings.constants.Separator.PIPE;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import settings.constants.Station;

public enum StationZones {
	
	INST;

	private final Map<Station, Zones> DICT;
	private final Zones NONE;

	private StationZones() {
		DICT = new ConcurrentHashMap<>(4);
		NONE = Zones.createNewEmptyZones();
	}

	public void init() {
		String property = AppConfig.INST.getProperty(STATIONS_WITH_ZONES);
		String pipeSep = "\\" + PIPE.val;
		String[] stations = property.split(pipeSep);

		for (String s : stations) {
			String[] stationZones = s.split(COLON.val);
			Station st = Station.getBy(stationZones[0]);
			Zones zones = Zones.newSetOfZones(stationZones[1]);

			DICT.put(st, zones);
		}
	}

	public Zones getZones(Station station) {
		Zones zones = DICT.get(station);

		return (null == zones ? this.NONE : zones);
	}

	public void clear() {
		DICT.clear();
	}
}
