package io.agileinfra.trading.platform.traffic.model;

import io.agileinfra.trading.platform.instrument.model.InstrumentDto;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class PriceContext {
	private Instant start;
	private Duration duration;
	private Duration minDurationBeforeNextPriceEvent;
	private Duration maxDurationBeforeNextPriceEvent;
	private int maxPriceVariation;

	private List<InstrumentDto> instruments;
}
