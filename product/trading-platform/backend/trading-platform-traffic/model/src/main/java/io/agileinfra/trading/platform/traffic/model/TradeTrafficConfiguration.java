package io.agileinfra.trading.platform.traffic.model;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Data
@Builder(toBuilder = true)
public class TradeTrafficConfiguration {
	private Duration minDurationBeforeNextTrade;
	private Duration maxDurationBeforeNextTrade;

	private Duration stateTransitionMinDuration;
	private Duration stateTransitionMaxDuration;
	private int maxQuantity;
}
