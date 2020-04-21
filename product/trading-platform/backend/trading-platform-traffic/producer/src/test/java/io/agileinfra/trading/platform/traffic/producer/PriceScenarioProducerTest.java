package io.agileinfra.trading.platform.traffic.producer;

import io.agileinfra.trading.platform.instrument.model.InstrumentDto;
import io.agileinfra.trading.platform.traffic.model.PriceContext;
import io.agileinfra.trading.platform.traffic.model.PriceScenario;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

@Slf4j
public class PriceScenarioProducerTest {

	private PriceScenarioProducer underTest;

	@Before
	public void before() {
		underTest = new PriceScenarioProducer();
	}

	@Test
	public void test() {
		final PriceContext context = PriceContext
				.builder()
				//
				.start(Instant.parse("2020-04-20T10:00:00.000Z"))
				//
				.duration(Duration.ofMinutes(15))
				//
				.minDurationBeforeNextPriceEvent(Duration.ofMillis(200))
				//
				.maxDurationBeforeNextPriceEvent(Duration.ofMillis(400))
				//
				.maxPriceVariation(10)
				//
				.instruments(
						Lists.newArrayList(InstrumentDto.builder().id(UUID.randomUUID().toString()).price(150).build(),
								InstrumentDto.builder().id(UUID.randomUUID().toString()).price(200).build(),
								InstrumentDto.builder().id(UUID.randomUUID().toString()).price(250).build(),
								InstrumentDto.builder().id(UUID.randomUUID().toString()).price(300).build())) //
				.build();
		List<PriceScenario> scenarios = underTest.produce(context);
		log.info("Produced {} scenarios", scenarios.size());
		// log.info("Generated SQL");
		// for (int i = 0; i < 10; i++) {
		// log.info("insert into instruments (id, price) values ('{}', {});", UUID.randomUUID().toString(), (float) (i+1) * 50);
		// }
		assertTrue(scenarios.size() >= 2000);
	}

}