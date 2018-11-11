package trip;

import java.time.Instant;
import java.util.Objects;

import card.Card;
import settings.constants.Station;

public class Trip {

	public final Direction direction;
	public final JourneyType journeyType;
	public final Station station;
	public final Card card;
	public final Instant time;

	private Trip(Builder b) {
		this.direction		= b.direction;
		this.journeyType	= b.journeyType;
		this.station		= b.station;
		this.card			= b.card;
		this.time			= null == b.time ? Instant.now() : b.time;
	}

	public boolean isCheckingInToBus() {
		return isCheckingIn() && isTravelingThroughBus();
	}

	public boolean isCheckingInToTube() {
		return isCheckingIn() && isTravelingThroughTube();
	}

	public boolean isCheckingIn() {
		return (Direction.IN == this.direction);
	}

	public boolean isCheckingOut() {
		return (Direction.OUT == this.direction);
	}

	public boolean isTravelingThroughBus() {
		return (JourneyType.BUS == this.journeyType);
	}

	public boolean isTravelingThroughTube() {
		return (JourneyType.TUBE == this.journeyType);
	}

	@Override
	public String toString() {
		return "Trip [direction=" + direction + ", journeyType=" + journeyType + ", station=" + station + ", card="
				+ card + ", time=" + time + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((card == null) ? 0 : card.hashCode());
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((journeyType == null) ? 0 : journeyType.hashCode());
		result = prime * result + ((station == null) ? 0 : station.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		Trip other = (Trip) obj;
		if (card == null) {
			if (other.card != null)
				return false;
		} else if (!card.equals(other.card))
			return false;
		if (direction != other.direction)
			return false;
		if (journeyType != other.journeyType)
			return false;
		if (station != other.station)
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
	
/* --------------------------- INNER CLASSES --------------------------- */

	public static enum Direction {
		IN, OUT
	}

	public static enum JourneyType {
		BUS, TUBE
	}

	public static class Builder {
		Direction direction;
		JourneyType journeyType;
		Station station;
		Card card;
		Instant time;

		private Builder() {
		}

		public static Builder New() {
			return new Builder();
		}

		public static Trip newUpdatedTrip(Trip trip, Card updatedCard) {
			return New()
				.journeyType(trip.journeyType)
				.direction(trip.direction)
				.station(trip.station)
				.time(trip.time)
				.card(updatedCard)
				.build();
		}

		public Builder direction(Direction direction) {
			Objects.requireNonNull(direction, "direction Can't be null");
			this.direction = direction;

			return this;
		}

		public Builder journeyType(JourneyType journeyType) {
			Objects.requireNonNull(journeyType, "Journey Type Can't be null");
			this.journeyType = journeyType;

			return this;
		}

		public Builder station(Station station) {
			Objects.requireNonNull(station, "station Can't be null");
			this.station = station;

			return this;
		}

		public Builder card(Card card) {
			Objects.requireNonNull(card, "card Can't be null");
			this.card = card;

			return this;
		}

		public Builder time(Instant time) {
			this.time = time;

			return this;
		}

		public Trip build() {
			verifyData();

			return new Trip(this);
		}

		private void verifyData() {
			Objects.requireNonNull(this.direction, "direction Can't be null");
			Objects.requireNonNull(this.journeyType, "Journey Type Can't be null");
			Objects.requireNonNull(this.station, "station Can't be null");
			Objects.requireNonNull(this.card, "card Can't be null");
		}

	}
}
