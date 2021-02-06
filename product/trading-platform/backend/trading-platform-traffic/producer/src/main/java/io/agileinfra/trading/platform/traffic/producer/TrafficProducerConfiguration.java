package io.agileinfra.trading.platform.traffic.producer;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.agileinfra.trading.platform.account.api.AccountApiConfiguration;
import io.agileinfra.trading.platform.account.model.AccountApi;
import io.agileinfra.trading.platform.account.model.AccountDto;
import io.agileinfra.trading.platform.instrument.api.InstrumentApiConfiguration;
import io.agileinfra.trading.platform.instrument.model.InstrumentApi;
import io.agileinfra.trading.platform.instrument.model.InstrumentDto;
import io.agileinfra.trading.platform.messaging.producer.booking.BookingEventProducer;
import io.agileinfra.trading.platform.messaging.producer.booking.BookingEventProducerConfiguration;
import io.agileinfra.trading.platform.messaging.producer.order.OrderEventProducer;
import io.agileinfra.trading.platform.messaging.producer.order.OrderEventProducerConfiguration;
import io.agileinfra.trading.platform.messaging.producer.price.PriceEventProducer;
import io.agileinfra.trading.platform.messaging.producer.price.PriceEventProducerConfiguration;
import io.agileinfra.trading.platform.traffic.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

/**
 * @author louis.gueye@gmail.com
 */
@Configuration
@Import({PriceEventProducerConfiguration.class, OrderEventProducerConfiguration.class, BookingEventProducerConfiguration.class,
		InstrumentApiConfiguration.class, AccountApiConfiguration.class})
public class TrafficProducerConfiguration {

	@Bean
	public ThreadFactory threadFactory() {
		return new ThreadFactoryBuilder().setNameFormat("traffic-pool-%d").build();
	}

	@Bean
	public ScheduledExecutorService scheduledExecutorService(final ThreadFactory threadFactory) {
		return Executors.newScheduledThreadPool(2, threadFactory);
	}

	@Bean
	public ScenarioTrafficConfiguration scenarioTrafficConfiguration(
			@Value("${start:}") final String startAsString, //
			@Value("${duration:PT10M}") final String durationAsString, //
			@Value("${trades.delay-interval:PT0.2S,PT0.3S}") final String tradesDelayInterval, //
			@Value("${trades.transition-interval:PT0.5S,PT1S}") final String tradesTransitionInterval, //
			@Value("${trades.max-quantity:100}") final int tradesMaxQuantity,
			@Value("${prices.delay-interval:PT0.5S,PT1S}") final String pricesDelayInterval, //
			@Value("${prices.max-variation:10}") final int pricesMaxVariation) {
		final Instant start = Strings.isNullOrEmpty(startAsString) ? Instant.now() : Instant.parse(startAsString);
		final Duration duration = Duration.parse(durationAsString);
		final List<String> pricesDelayIntervalList = Lists.newArrayList(Splitter.on(",").split(pricesDelayInterval));
		final PriceTrafficConfiguration priceTrafficConfiguration = PriceTrafficConfiguration.builder()
				.minDurationBeforeNextPriceEvent(Duration.parse(pricesDelayIntervalList.get(0)))
				.maxDurationBeforeNextPriceEvent(Duration.parse(pricesDelayIntervalList.get(1))).maxVariation(pricesMaxVariation).build();
		final List<String> tradesDelayIntervalList = Lists.newArrayList(Splitter.on(",").split(tradesDelayInterval));
		final List<String> tradesTransitionIntervalList = Lists.newArrayList(Splitter.on(",").split(tradesTransitionInterval));
		final TradeTrafficConfiguration tradeTrafficConfiguration = TradeTrafficConfiguration.builder()
				.minDurationBeforeNextTrade(Duration.parse(tradesDelayIntervalList.get(0)))
				.maxDurationBeforeNextTrade(Duration.parse(tradesDelayIntervalList.get(1)))
				.stateTransitionMinDuration(Duration.parse(tradesTransitionIntervalList.get(0)))
				.stateTransitionMaxDuration(Duration.parse(tradesTransitionIntervalList.get(1))).maxQuantity(tradesMaxQuantity).build();
		return ScenarioTrafficConfiguration.builder().start(start).duration(duration).prices(priceTrafficConfiguration)
				.trades(tradeTrafficConfiguration).build();
	}

	@Bean
	public TradeContext tradeContext(final ScenarioTrafficConfiguration scenarioTrafficConfiguration, final AccountApi accountApi, //
			final InstrumentApi instrumentApi) {
		final TradeTrafficConfiguration tradeTrafficConfiguration = scenarioTrafficConfiguration.getTrades();
		final List<InstrumentDto> instruments = instrumentApi.findAllInstruments();
		final List<String> accounts = accountApi.findAllAccounts().stream().map(AccountDto::getId).collect(Collectors.toList());

		return TradeContext.builder() //
				.start(scenarioTrafficConfiguration.getStart()) //
				.duration(scenarioTrafficConfiguration.getDuration()) //
				.accounts(accounts) //
				.instruments(instruments.stream().map(InstrumentDto::getId).collect(Collectors.toList())) //
				.minDurationBeforeNextTrade(tradeTrafficConfiguration.getMinDurationBeforeNextTrade()) //
				.maxDurationBeforeNextTrade(tradeTrafficConfiguration.getMaxDurationBeforeNextTrade()) //
				.stateTransitionMinDuration(tradeTrafficConfiguration.getStateTransitionMinDuration()) //
				.stateTransitionMaxDuration(tradeTrafficConfiguration.getStateTransitionMaxDuration()) //
				.maxQuantity(tradeTrafficConfiguration.getMaxQuantity()) //
				.build();
	}

	@Bean
	public PriceContext priceContext(final ScenarioTrafficConfiguration scenarioTrafficConfiguration, final InstrumentApi instrumentApi) {
		final List<InstrumentDto> instruments = instrumentApi.findAllInstruments();
		final PriceTrafficConfiguration priceTrafficConfiguration = scenarioTrafficConfiguration.getPrices();
		return PriceContext.builder() //
				.start(scenarioTrafficConfiguration.getStart()) //
				.duration(scenarioTrafficConfiguration.getDuration()) //
				.instruments(instruments) //
				.minDurationBeforeNextPriceEvent(priceTrafficConfiguration.getMinDurationBeforeNextPriceEvent()) //
				.maxDurationBeforeNextPriceEvent(priceTrafficConfiguration.getMaxDurationBeforeNextPriceEvent()) //
				.maxPriceVariation(priceTrafficConfiguration.getMaxVariation()) //
				.build();
	}

	@Bean
	public TradeScenarioProducer tradeScenarioProducer(final TradeContext tradeContext) {
		return new TradeScenarioProducer(tradeContext);
	}

	@Bean
	public PriceScenarioProducer priceScenarioProducer(final PriceContext priceContext) {
		return new PriceScenarioProducer(priceContext);
	}

	@Bean
	public TrafficScheduler trafficScheduler(final ScheduledExecutorService scheduledExecutorService, //
			final TradeScenarioProducer tradeScenarioProducer, //
			final PriceScenarioProducer priceScenarioProducer, //
			final OrderEventProducer orderEventProducer, //
			final BookingEventProducer bookingEventProducer, //
			final PriceEventProducer priceEventProducer) {
		TrafficScheduler trafficScheduler = new TrafficScheduler(scheduledExecutorService, tradeScenarioProducer, priceScenarioProducer,
				orderEventProducer, bookingEventProducer, priceEventProducer);
		trafficScheduler.schedule();
		return trafficScheduler;
	}
}
