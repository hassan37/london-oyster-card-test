package card;

import java.math.BigDecimal;

import settings.CardNumberGenerator;

public class Card {

	public final CardNumber number;
	public final CardBalance amount;

	private Card(Builder b) {
		this.number = b.number;
		this.amount = b.amount;
	}

	public Double getBalance() {
		return amount.value.doubleValue();
	}

	public boolean zeroBalance() {
		return amount.value.compareTo(BigDecimal.ZERO) == 0;
	}

	public Long getNumber() {
		return number.value;
	}

	public static class Builder {
		CardNumber number;
		CardBalance amount;

		private Builder() {
		}

		public static Builder New() {
			return new Builder();
		}

		public Builder number(Long number) {
			this.number = CardNumber.New(number);

			return this;
		}

		public Builder balance(Double amount) {
			this.amount = CardBalance.New(amount);

			return this;
		}

		public Card build() {
			if (null == number)
				number(CardNumberGenerator.INST.generate());

			return new Card(this);
		}

		public static Card newUpdatedCard(Card card, CardBalance newBalance) {
			return New()
				.number(card.getNumber())
				.balance(newBalance.value.doubleValue())
				.build();
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		Card other = (Card) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Card [number=" + number + ", amount=" + amount + "]";
	}

}
