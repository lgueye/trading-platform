package io.agileinfra.trading.platform.traffic.model;

import io.agileinfra.trading.plateform.booking.model.BookingEventDto;
import io.agileinfra.trading.platform.account.model.InitiationEventDto;
import io.agileinfra.trading.platform.account.model.MatchingEventDto;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder(toBuilder = true)
public class TradeScenario {
	private InitiationEventDto initiation;
	private MatchingEventDto matching;
	private BookingEventDto booking;

	public Instant getEnd() {
		return booking.getTimestamp();
	}

}
