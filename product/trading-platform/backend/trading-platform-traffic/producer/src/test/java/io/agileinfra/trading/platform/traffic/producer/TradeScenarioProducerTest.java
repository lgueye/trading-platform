package io.agileinfra.trading.platform.traffic.producer;

import com.google.common.collect.Lists;
import io.agileinfra.trading.platform.traffic.model.TradeContext;
import io.agileinfra.trading.platform.traffic.model.TradeScenario;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

@Slf4j
public class TradeScenarioProducerTest {

	private TradeScenarioProducer underTest;

	@Test
	public void test() {
		final TradeContext context = TradeContext.builder() //
				.start(Instant.parse("2020-04-20T10:00:00.000Z")) //
				.duration(Duration.ofMinutes(15)) //
				.stateTransitionMinDuration(Duration.ofMillis(100)) //
				.stateTransitionMaxDuration(Duration.ofMillis(200)) //
				.minDurationBeforeNextTrade(Duration.ofMillis(300)) //
				.maxDurationBeforeNextTrade(Duration.ofMillis(600)) //
				.maxQuantity(100) //
				.instruments(Lists.newArrayList("DE1000000", "FR2000000", "LU3000000", "GB4000000")) //
				.accounts(Lists.newArrayList(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())) //
				.build();
		underTest = new TradeScenarioProducer(context);
		List<TradeScenario> scenarios = underTest.produce();
		log.info("Produced {} scenarios", scenarios.size());
		// log.info("Generated SQL");
		// final List<String> names = Lists.newArrayList("xavier", "julie", "olivier");
		// for (int i = 0; i < 3; i++) {
		// final String name = names.get(i);
		// log.info("insert into users (id, login, password) values ('usr-{}', '{}', '{}');", name, name, name);
		// log.info("insert into user_roles (id, user, role) values ('{}-role', 'usr-{}', 'user');", name, name);
		// log.info("insert into accounts (id, owner, cash) values ('{}', 'usr-{}', {});", UUID.randomUUID().toString(), name, (float) 100000 * (i +
		// 1));
		// }

		assertTrue(scenarios.size() >= 1000);
	}

}