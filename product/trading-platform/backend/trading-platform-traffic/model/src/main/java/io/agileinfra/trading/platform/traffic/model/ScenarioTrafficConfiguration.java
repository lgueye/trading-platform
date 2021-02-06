package io.agileinfra.trading.platform.traffic.model;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;

@Data
@Builder(toBuilder = true)
public class ScenarioTrafficConfiguration {
	private Instant start;
	private Duration duration;
	private TradeTrafficConfiguration trades;
	private PriceTrafficConfiguration prices;
}
