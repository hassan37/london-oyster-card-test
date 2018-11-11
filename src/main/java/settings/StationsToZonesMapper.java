package settings;

import java.util.ArrayList;
import java.util.List;

import settings.constants.Station;
import settings.constants.Zone;

public enum StationsToZonesMapper {

	INST;

	public List<Zones> map(Station from, Station to) {
		List<Zones> journeyZones = new ArrayList<>(3);
		StationZones stationZones = StationZones.INST;
		Zones fromZones = stationZones.getZones(from);
		Zones toZones = stationZones.getZones(to);

		for (Zone fz : fromZones) {
			for (Zone tz : toZones) {
				Zones zones = mapFarewiseZones(fz, tz);

				journeyZones.add(zones);
			}
		}

		return journeyZones;
	}

	public Zones mapFarewiseZones(Zone fz, Zone tz) {
		Zones zones = Zones.New();

		if (isAnyThreeZones(fz, tz)) {
			zones.add(Zone.ONE);
			zones.add(Zone.TWO);
			zones.add(Zone.THREE);

			return zones;
		}

		if (	isAnywhereInZone1(fz, tz) 
			||	isAnyOneZoneOutsideZone1(fz, tz)
		) {
			zones.add(fz);

			return zones;
		}		

		if (isAny2ZonesIncludingZone1(fz, tz)) {
			zones.add(Zone.ONE);
		
			if(fz.isZone_1())
				zones.add(tz);
			else
				zones.add(fz);

			return zones;
		}

		if (isAny2ZonesExcludingZone1(fz, tz)) {
			zones.add(Zone.TWO);
			zones.add(Zone.THREE);

			return zones;
		}

		return zones;
	}

	private boolean isAnywhereInZone1(Zone fz, Zone tz) {
		return (fz == tz && fz.isZone_1());
	}

	private boolean isAnyOneZoneOutsideZone1(Zone fz, Zone tz) {
		return ( fz == tz && isZone_2_OR_Zone_3(fz) );
	}

	private boolean isZone_2_OR_Zone_3(Zone zone) {
		return (zone.isZone_2() || zone.isZone_3());
	}

	private boolean isAny2ZonesIncludingZone1(Zone fz, Zone tz) {
		if (fz == tz)
			return Boolean.FALSE;

		boolean isAnyZone_1 = (fz.isZone_1() || tz.isZone_1());
		if (!isAnyZone_1)
			return Boolean.FALSE;

		boolean fromZoneIs_2_OR_3 = (fz.isNotZone_1() && isZone_2_OR_Zone_3(fz));
		if (fromZoneIs_2_OR_3)
			return Boolean.TRUE;
				
		boolean toZoneIs_2_OR_3 = (tz.isNotZone_1() && isZone_2_OR_Zone_3(tz));
		if (toZoneIs_2_OR_3)
			return Boolean.TRUE;

		return Boolean.FALSE;
	}

	private boolean isAny2ZonesExcludingZone1(Zone fz, Zone tz) {
		if (fz == tz)
			return Boolean.FALSE;

		if (fz.isZone_1() || tz.isZone_1())
			return Boolean.FALSE;

		return	(fz.isZone_2() || fz.isZone_3())
			&&	(tz.isZone_2() || tz.isZone_3());
	}

	private boolean isAnyThreeZones(Zone fz, Zone tz) {
		return	(fz.isZone_1() && tz.isZone_3() )
			||	(fz.isZone_3() && tz.isZone_1());
	}
}
