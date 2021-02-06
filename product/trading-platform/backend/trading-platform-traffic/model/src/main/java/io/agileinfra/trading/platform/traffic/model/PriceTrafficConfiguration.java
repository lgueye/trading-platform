package io.agileinfra.trading.platform.traffic.model;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Data
@Builder(toBuilder = true)
public class PriceTrafficConfiguration {
	private Duration minDurationBeforeNextPriceEvent;
	private Duration maxDurationBeforeNextPriceEvent;
	private int maxVariation;
}
