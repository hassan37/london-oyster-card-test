package settings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import settings.constants.ConfigProperty;
import settings.constants.Constants;

import static settings.constants.ConfigProperty.FARE_PRECISION_SCALE;


public enum AppConfig {
	INST;

	private final Properties PROPERTIES;

	private AppConfig() {
		PROPERTIES = new Properties();
	}

	public void load() throws IOException {
		try (InputStream in =  getClass().getClassLoader().getResourceAsStream(Constants.CONFIG_FILE_NAME.value);) {
			PROPERTIES.loadFromXML(in);
		}
	}

	public int getMoneyPrecisionScale() {
		String mps = PROPERTIES.getProperty(FARE_PRECISION_SCALE.toString());

		return Integer.valueOf(mps);
	}

	public void setProperty(ConfigProperty key, String value) {
		PROPERTIES.setProperty(key.toString(), value);
	}

	public String getProperty(ConfigProperty key) {
		return PROPERTIES.getProperty(key.toString());
	}

	public void removeProperty(ConfigProperty key) {
		PROPERTIES.remove(key.toString());
	}
}
