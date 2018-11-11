package card;

import java.util.Objects;

public final class CardNumber {

	public final Long value;

	private CardNumber(Long value) {
		Objects.requireNonNull(value, "Card Number can not be null.");
		this.value = value;
	}

	public static CardNumber New(Long value) {
		return new CardNumber(value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (value ^ (value >>> 32));
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
		CardNumber other = (CardNumber) obj;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CardNumber [value=" + value + "]";
	}

}
