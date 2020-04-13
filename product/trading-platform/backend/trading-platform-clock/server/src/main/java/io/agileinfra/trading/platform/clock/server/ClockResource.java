package io.agileinfra.trading.platform.clock.server;

import io.agileinfra.trading.platform.clock.model.ClockApi;
import io.agileinfra.trading.platform.clock.model.ClockDto;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/clock")
public class ClockResource implements ClockApi {
	private Instant instant;

	@PostMapping("freeze")
	@Override
	public void freeze(@RequestBody ClockDto request) {
		this.instant = request.getInstant();
	}

	@GetMapping("now")
	public Instant now() {
		return instant;
	}

}
