package io.agileinfra.trading.platform.traffic.producer;

import com.google.common.collect.Lists;
import io.agileinfra.trading.platform.instrument.model.InstrumentDto;
import io.agileinfra.trading.platform.instrument.model.PriceEventDto;
import io.agileinfra.trading.platform.traffic.model.PriceContext;
import io.agileinfra.trading.platform.traffic.model.PriceScenario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Produces various scenarios within the bounds of the context constraints
 */
@RequiredArgsConstructor
@Slf4j
public class PriceScenarioProducer {

	public List<PriceScenario> produce(final PriceContext context) {
		final List<PriceScenario> scenarios = Lists.newArrayList();
		final Instant start = context.getStart();
		final Instant end = start.plus(context.getDuration());

		final List<InstrumentDto> instruments = context.getInstruments();
		final Duration minDurationBeforeNextPriceEvent = context.getMinDurationBeforeNextPriceEvent();
		final Duration maxDurationBeforeNextPriceEvent = context.getMaxDurationBeforeNextPriceEvent();
		final int maxVariation = context.getMaxPriceVariation();
		final List<Integer> variations = IntStream.range(-maxVariation, maxVariation).boxed().collect(Collectors.toList());

		Instant currentTime = start;
		while (end.compareTo(currentTime) > 0) {
			Collections.shuffle(instruments);
			Collections.shuffle(variations);
			final InstrumentDto instrument = instruments.get(0);
			final String id = instrument.getId();
			final PriceEventDto event = PriceEventDto.builder() //
					.instrument(id) //
					.price(instrument.getPrice() + variations.get(0)) //
					.timestamp(currentTime) //
					.build();
			final PriceScenario scenario = PriceScenario.builder() //
					.event(event) //
					.build();
			scenarios.add(scenario);
			currentTime = scenario.getEnd().plus(
					Duration.ofMillis(RandomUtils.nextInt((int) minDurationBeforeNextPriceEvent.toMillis(),
							(int) maxDurationBeforeNextPriceEvent.toMillis())));
		}
		return scenarios;
	}
}
