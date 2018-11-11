package settings.constants;

public enum Station {

	HOLBORN, EARLS_COURT, WIMBLEDON, HAMMERSMITH, CHELSEA, NONE;

	public static Station getBy(String name) {
		Station requestedStation = Station.NONE;

		for (Station s : Station.values()) {
			if (s.toString().equalsIgnoreCase(name)) {
				requestedStation = s;
				break;
			}
		}

		return requestedStation;
	}

	@Override
	public String toString() {
		if(this == HOLBORN)
			return "Holborn";

		if(this == EARLS_COURT)
			return "Earl's Court";

		if(this == WIMBLEDON)
			return "Wimbledon";

		if(this == HAMMERSMITH)
			return "Hammersmith";

		return "NONE";
	}
}
