package fare;

import java.math.BigDecimal;
import java.util.Objects;

import settings.AppConfig;

public class Fare {

	public final BigDecimal value;

	private Fare(BigDecimal value) {
		Objects.requireNonNull(value, "Value of Fare can not be null");

		int scale = AppConfig.INST.getMoneyPrecisionScale();
		this.value = value.setScale(scale, BigDecimal.ROUND_HALF_UP);
	}

	public static Fare New(Double value) {
		return new Fare(BigDecimal.valueOf(value));
	}

	public Fare max(Fare f2) {
		int res = this.value.compareTo(f2.value);
		if(res > 0)
			return this;
		if(res < 0)
			return f2;

		return this;
	}

	public Fare min(Fare f2) {
		int res = this.value.compareTo(f2.value);
		if(res > 0)
			return f2;
		if(res < 0)
			return this;

		return this;
	}

	@Override
	public String toString() {
		return "Fare [value=" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Fare other = (Fare) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
