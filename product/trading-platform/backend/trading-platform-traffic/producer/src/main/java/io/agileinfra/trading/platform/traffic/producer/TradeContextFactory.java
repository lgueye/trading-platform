package io.agileinfra.trading.platform.traffic.producer;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.agileinfra.trading.platform.account.model.AccountApi;
import io.agileinfra.trading.platform.account.model.AccountDto;
import io.agileinfra.trading.platform.instrument.model.InstrumentApi;
import io.agileinfra.trading.platform.instrument.model.InstrumentDto;
import io.agileinfra.trading.platform.traffic.model.TradeContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Produces a trade context from various command line parameters
 * The context controls the trades traffic behavior
 */
@RequiredArgsConstructor
@Slf4j
public class TradeContextFactory {
	private final String start;
	private final String durationAsString;
	private final String tradesDelayInterval;
	private final String tradesTransitionInterval;
	private final int tradesMaxQuantity;
	private final AccountApi accountApi;
	private final InstrumentApi instrumentApi;

	public TradeContext build() {
		final Duration duration = Duration.parse(durationAsString);
		final List<InstrumentDto> instruments = instrumentApi.findAllInstruments();
		final List<String> accounts = accountApi.findAllAccounts().stream().map(AccountDto::getId).collect(Collectors.toList());
		final List<String> tradesDelayInterval = Lists.newArrayList(Splitter.on(",").split(this.tradesDelayInterval));
		final List<String> tradesTransitionInterval = Lists.newArrayList(Splitter.on(",").split(this.tradesTransitionInterval));
		final Instant start = Strings.isNullOrEmpty(this.start) ? Instant.now() : Instant.parse(this.start);

		return TradeContext.builder() //
				.duration(duration) //
				.start(start) //
				.accounts(accounts) //
				.instruments(instruments.stream().map(InstrumentDto::getId).collect(Collectors.toList())) //
				.minDurationBeforeNextTrade(Duration.parse(tradesDelayInterval.get(0))) //
				.maxDurationBeforeNextTrade(Duration.parse(tradesDelayInterval.get(1))) //
				.stateTransitionMinDuration(Duration.parse(tradesTransitionInterval.get(0))) //
				.stateTransitionMaxDuration(Duration.parse(tradesTransitionInterval.get(1))) //
				.maxQuantity(tradesMaxQuantity) //
				.build();

	}
}
