package io.agileinfra.trading.platform.traffic.model;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class TradeContext {
	private Instant start;
	private Duration duration;
	private Duration minDurationBeforeNextTrade;
	private Duration maxDurationBeforeNextTrade;
	private Duration stateTransitionMinDuration;
	private Duration stateTransitionMaxDuration;
	private int maxQuantity;
	private List<String> accounts;
	private List<String> instruments;
}
