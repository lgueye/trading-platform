package io.agileinfra.trading.platform.traffic.producer;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.agileinfra.trading.platform.instrument.model.InstrumentApi;
import io.agileinfra.trading.platform.instrument.model.InstrumentDto;
import io.agileinfra.trading.platform.traffic.model.PriceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * Produces a price context from various command line parameters
 * The context controls the price traffic behavior
 */
@RequiredArgsConstructor
@Slf4j
public class PriceContextFactory {
	private final String start;
	private final String durationAsString;
	private final String pricesDelayInterval;
	private final int pricesMaxVariation;
	private final InstrumentApi instrumentApi;

	public PriceContext build() {
		final Duration duration = Duration.parse(durationAsString);
		final List<InstrumentDto> instruments = instrumentApi.findAllInstruments();
		final Instant start = Strings.isNullOrEmpty(this.start) ? Instant.now() : Instant.parse(this.start);
		final List<String> pricesDelayInterval = Lists.newArrayList(Splitter.on(",").split(this.pricesDelayInterval));

		return PriceContext.builder() //
				.duration(duration) //
				.start(start) //
				.instruments(instruments) //
				.minDurationBeforeNextPriceEvent(Duration.parse(pricesDelayInterval.get(0))) //
				.maxDurationBeforeNextPriceEvent(Duration.parse(pricesDelayInterval.get(1))) //
				.maxPriceVariation(pricesMaxVariation) //
				.build();

	}
}
