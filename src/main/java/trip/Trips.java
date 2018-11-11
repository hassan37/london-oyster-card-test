package trip;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Trips implements Iterable<Trip> {

	private final List<Trip> list;

	private Trips(Trip trip) {
		this.list = new CopyOnWriteArrayList<>();
		this.list.add(trip);
	}

	public static Trips newJourney(Trip trip) {
		return new Trips(trip);
	}

	public void add(Trip trip) {
		list.add(trip);
	}

	public Trip mostRecentTrip() {
		int lastElementIndex = list.size() - 1;

		return list.get(lastElementIndex);
	}

	public boolean isMostRecentTripCheckIn() {
		return mostRecentTrip().isCheckingIn();
	}

	public boolean isMostRecentTripCheckOut() {
		return mostRecentTrip().isCheckingOut();
	}

	public boolean isMostRecentTripOnBus() {
		return mostRecentTrip().isTravelingThroughBus();
	}

	public boolean isMostRecentTripOnTube() {
		return mostRecentTrip().isTravelingThroughTube();
	}

	public int total() {
		return list.size();
	}

	@Override
	public Iterator<Trip> iterator() {
		return list.iterator();
	}

}
