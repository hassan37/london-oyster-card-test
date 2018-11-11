package settings;

public class MoneyPrecision {

	public final int scale;

	public MoneyPrecision(int scale) {
		this.scale = scale;
	}

	@Override
	public String toString() {
		return "MoneyPrecision [scale=" + scale + "]";
	}

}
