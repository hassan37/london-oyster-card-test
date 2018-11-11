package settings;

import java.time.Instant;

public enum CardNumberGenerator {

	INST;

	public Long generate() {
		Long number = Instant.now().toEpochMilli();

		return number;
	}
}
