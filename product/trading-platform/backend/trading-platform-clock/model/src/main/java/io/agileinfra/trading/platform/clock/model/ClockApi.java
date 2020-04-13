package io.agileinfra.trading.platform.clock.model;

import java.time.Instant;

public interface ClockApi {
	void freeze(ClockDto request);
	Instant now();
}
