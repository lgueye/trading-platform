package io.agileinfra.trading.platform.traffic.model;

import io.agileinfra.trading.platform.instrument.model.PriceEventDto;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder(toBuilder = true)
public class PriceScenario {
	private PriceEventDto event;

	public Instant getEnd() {
		return event.getTimestamp();
	}

}
