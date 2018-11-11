package settings;

import static settings.constants.Separator.COMMA;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import settings.constants.Zone;

public final class Zones implements Iterable<Zone> {

	private final Set<Zone> set;

	private Zones(Set<Zone> zones) {
		this.set = zones;
	}

	private Zones() {
		this.set = new HashSet<>();
	}

	public static Zones createNewEmptyZones() {
		return new Zones(Collections.emptySet());
	}

	public static Zones newSetOfZones(String zones) {
		String[] zonesArr = zones.split(COMMA.val);

		Set<Zone> zonesSet = new HashSet<>(zonesArr.length);
		for (String z : zonesArr) {
			zonesSet.add(Zone.getBy(z));
		}

		return new Zones(zonesSet);
	}

	public static Zones New() {
		return new Zones();
	}

	public Zones add(Zone zone) {
		set.add(zone);

		return this;
	}

	@Override
	public String toString() {
		return "Zones [set=" + set + "]";
	}

	@Override
	public Iterator<Zone> iterator() {
		return set.iterator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((set == null) ? 0 : set.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Zones other = (Zones) obj;
		if (set == null) {
			if (other.set != null)
				return false;
		} else if (!set.equals(other.set))
			return false;
		return true;
	}

	public Set<Zone> getZones() {
		return Collections.unmodifiableSet(set);
	}
	
}
