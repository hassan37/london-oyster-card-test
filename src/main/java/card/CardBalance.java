package card;

import java.math.BigDecimal;
import java.util.Objects;

import settings.AppConfig;

public final class CardBalance {

	public final BigDecimal value;

	private CardBalance(BigDecimal value) {
		Objects.requireNonNull(value, "Card Balance can not be null");

		int scale = AppConfig.INST.getMoneyPrecisionScale();
		this.value = value.setScale(scale, BigDecimal.ROUND_HALF_UP);
	}

	public CardBalance debit(BigDecimal amount) {
		int scale = AppConfig.INST.getMoneyPrecisionScale();
		BigDecimal scaledAmount = amount.setScale(scale, BigDecimal.ROUND_HALF_UP);

		return CardBalance.New(this.value.subtract(scaledAmount).doubleValue());
	}

	public CardBalance credit(BigDecimal amount) {
		int scale = AppConfig.INST.getMoneyPrecisionScale();
		BigDecimal scaledAmount = amount.setScale(scale, BigDecimal.ROUND_HALF_UP);

		return CardBalance.New(this.value.add(scaledAmount).doubleValue());
	}

	public static CardBalance New(Double value) {
		return new CardBalance(BigDecimal.valueOf(value));
	}

	@Override
	public String toString() {
		return "CardBalance [value=" + value + "]";
	}

}
