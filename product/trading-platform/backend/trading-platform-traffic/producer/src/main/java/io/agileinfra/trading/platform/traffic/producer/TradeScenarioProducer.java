package io.agileinfra.trading.platform.traffic.producer;

import com.google.common.collect.Lists;
import io.agileinfra.trading.plateform.booking.model.BookingEventDto;
import io.agileinfra.trading.platform.account.model.InitiationEventDto;
import io.agileinfra.trading.platform.account.model.MatchingEventDto;
import io.agileinfra.trading.platform.traffic.model.TradeContext;
import io.agileinfra.trading.platform.traffic.model.TradeScenario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Produces various scenarios within the bounds of the context constraints
 */
@RequiredArgsConstructor
@Slf4j
public class TradeScenarioProducer {

	public List<TradeScenario> produce(final TradeContext context) {
		final List<TradeScenario> scenarios = Lists.newArrayList();
		final Instant start = context.getStart();
		final Instant end = start.plus(context.getDuration());

		final List<String> accounts = context.getAccounts();
		final List<String> instruments = context.getInstruments();
		final Duration minDurationBeforeNextTrade = context.getMinDurationBeforeNextTrade();
		final Duration maxDurationBeforeNextTrade = context.getMaxDurationBeforeNextTrade();
		final Duration stateTransitionMinDuration = context.getStateTransitionMinDuration();
		final Duration stateTransitionMaxDuration = context.getStateTransitionMaxDuration();
		final int maxQuantity = context.getMaxQuantity();
		final List<Integer> quantities = IntStream.range(-maxQuantity, maxQuantity).boxed().collect(Collectors.toList());

		Instant currentTime = start;
		while (end.compareTo(currentTime) > 0) {
			Collections.shuffle(accounts);
			Collections.shuffle(instruments);
			Collections.shuffle(quantities);
			final String order = UUID.randomUUID().toString();
			final String account = accounts.get(0);
			final List<String> counterparts = accounts.subList(1, accounts.size() - 1);
			final String counterpart = counterparts.get(0);
			final String instrument = instruments.get(0);
			// Max 100 buy or sell
			final double quantity = quantities.get(0);
			if (quantity != 0) {
				final InitiationEventDto initiation = InitiationEventDto.builder() //
						.order(order) //
						.account(account) //
						.quantity(quantity) //
						.instrument(instrument) //
						.timestamp(currentTime) //
						.build();
				final MatchingEventDto matching = MatchingEventDto.builder() //
						.order(order) //
						.counterpart(counterpart)
						//
						.timestamp(
								initiation.getTimestamp().plus(
										Duration.ofMillis(RandomUtils.nextInt((int) stateTransitionMinDuration.toMillis(),
												(int) stateTransitionMaxDuration.toMillis())))).build();
				final BookingEventDto booking = BookingEventDto.builder() //
						.order(order) //
						.counterpart(counterpart)
						//
						.timestamp(
								matching.getTimestamp().plus(
										Duration.ofMillis(RandomUtils.nextInt((int) stateTransitionMinDuration.toMillis(),
												(int) stateTransitionMaxDuration.toMillis())))).build();
				final TradeScenario scenario = TradeScenario.builder() //
						.initiation(initiation) //
						.matching(matching) //
						.booking(booking) //
						.build();
				scenarios.add(scenario);
				currentTime = scenario.getEnd().plus(
						Duration.ofMillis(RandomUtils.nextInt((int) minDurationBeforeNextTrade.toMillis(),
								(int) maxDurationBeforeNextTrade.toMillis())));
			}
		}
		return scenarios;
	}
}
