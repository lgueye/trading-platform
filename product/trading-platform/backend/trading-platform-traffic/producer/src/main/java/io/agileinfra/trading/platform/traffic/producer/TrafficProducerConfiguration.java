package io.agileinfra.trading.platform.traffic.producer;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.agileinfra.trading.platform.account.api.AccountApiConfiguration;
import io.agileinfra.trading.platform.account.model.AccountApi;
import io.agileinfra.trading.platform.instrument.api.InstrumentApiConfiguration;
import io.agileinfra.trading.platform.instrument.model.InstrumentApi;
import io.agileinfra.trading.platform.messaging.producer.booking.BookingEventProducer;
import io.agileinfra.trading.platform.messaging.producer.booking.BookingEventProducerConfiguration;
import io.agileinfra.trading.platform.messaging.producer.order.OrderEventProducer;
import io.agileinfra.trading.platform.messaging.producer.order.OrderEventProducerConfiguration;
import io.agileinfra.trading.platform.messaging.producer.price.PriceEventProducer;
import io.agileinfra.trading.platform.messaging.producer.price.PriceEventProducerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

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
	public TradeScenarioProducer tradeScenariosProducer() {
		return new TradeScenarioProducer();
	}

	@Bean
	public PriceScenarioProducer priceScenarioProducer() {
		return new PriceScenarioProducer();
	}

	@Bean
	public TradeContextFactory tradeContextFactory(@Value("${start:}") final String start, //
			@Value("${duration:PT10M}") final String durationAsString, //
			@Value("${trades.delay-interval:PT0.2S,PT0.3S}") final String tradesDelayInterval, //
			@Value("${trades.transition-interval:PT0.5S,PT1S}") final String tradesTransitionInterval, //
			@Value("${trades.max-quantity:100}") final int tradesMaxQuantity, //
			final AccountApi accountApi, //
			final InstrumentApi instrumentApi) {
		return new TradeContextFactory(start, durationAsString, tradesDelayInterval, tradesTransitionInterval, tradesMaxQuantity, accountApi,
				instrumentApi);
	}

	@Bean
	public PriceContextFactory priceContextFactory(@Value("${start:}") final String start, //
			@Value("${duration:PT10M}") final String durationAsString, //
			@Value("${prices.delay-interval:PT0.5S,PT1S}") final String pricesDelayInterval, //
			@Value("${prices.max-variation:10}") final int pricesMaxVariation, //
			final InstrumentApi instrumentApi) {
		return new PriceContextFactory(start, durationAsString, pricesDelayInterval, pricesMaxVariation, instrumentApi);
	}

	@Bean
	public TrafficScheduler trafficScheduler(final ScheduledExecutorService scheduledExecutorService, //
			final TradeScenarioProducer tradeScenarioProducer, //
			final PriceScenarioProducer priceScenarioProducer, //
			final OrderEventProducer orderEventProducer, //
			final BookingEventProducer bookingEventProducer, //
			final PriceEventProducer priceEventProducer, //
			final TradeContextFactory tradeContextFactory, //
			final PriceContextFactory priceContextFactory //
	) {
		return new TrafficScheduler(scheduledExecutorService, tradeScenarioProducer, priceScenarioProducer, orderEventProducer, bookingEventProducer,
				priceEventProducer, tradeContextFactory, priceContextFactory);
	}
}
