package io.agileinfra.trading.platform.clock.api;

import io.agileinfra.trading.platform.clock.model.ClockApi;
import io.agileinfra.trading.platform.clock.model.ClockDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@RequiredArgsConstructor
@Slf4j
public class ClockApiImpl implements ClockApi {
	@Override
	public void freeze(ClockDto newClock) {
		throw new UnsupportedOperationException(
				"Freezing clock is not supported as a regular behavior, only during tests to make them predictable (ie e2e profile).");
	}

	@Override
	public Instant now() {
		return Instant.now();
	}
}
