package settings.constants;

public enum Zone {

	ONE(1), TWO(2), THREE(3);

	public final int id;

	private Zone(int id) {
		this.id = id;
	}

	public static Zone getBy(String zone) {
		Zone requestedZone = null;
		for (Zone z : Zone.values()) {
			if (z.id == Integer.valueOf(zone)) {
				requestedZone = z;
				break;
			}
		}

		return requestedZone;
	}

	public boolean isNotZone_1() {
		return !isZone_1();
	}

	public boolean isNotZone_2() {
		return !isZone_2();
	}

	public boolean isNotZone_3() {
		return !isZone_3();
	}

	public boolean isZone_1() {
		return ONE == this;
	}

	public boolean isZone_2() {
		return TWO == this;
	}

	public boolean isZone_3() {
		return THREE == this;
	}
}
