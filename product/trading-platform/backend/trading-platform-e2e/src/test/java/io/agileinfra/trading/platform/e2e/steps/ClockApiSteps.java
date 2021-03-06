package io.agileinfra.trading.platform.e2e.steps;

import io.agileinfra.trading.platform.clock.model.ClockApi;
import io.agileinfra.trading.platform.clock.model.ClockDto;
import io.cucumber.java8.En;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
public class ClockApiSteps implements En {

	public ClockApiSteps(ClockApi clockApiE2E) {
		When("clock {}", (final String timestampAsString) -> clockApiE2E.freeze(ClockDto.builder().instant(Instant.parse(timestampAsString)).build()));
	}
}
