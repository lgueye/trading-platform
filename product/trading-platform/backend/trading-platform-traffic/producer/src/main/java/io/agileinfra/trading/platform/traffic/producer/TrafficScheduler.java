package io.agileinfra.trading.platform.traffic.producer;

import io.agileinfra.trading.plateform.booking.model.BookingEventDto;
import io.agileinfra.trading.platform.account.model.InitiationEventDto;
import io.agileinfra.trading.platform.account.model.MatchingEventDto;
import io.agileinfra.trading.platform.instrument.model.PriceEventDto;
import io.agileinfra.trading.platform.messaging.producer.booking.BookingEventProducer;
import io.agileinfra.trading.platform.messaging.producer.order.OrderEventProducer;
import io.agileinfra.trading.platform.messaging.producer.price.PriceEventProducer;
import io.agileinfra.trading.platform.traffic.model.PriceScenario;
import io.agileinfra.trading.platform.traffic.model.TradeScenario;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Schedules prices and trades traffic for a given period of time
 */
@Slf4j
public class TrafficScheduler {
	private final ScheduledExecutorService scheduledExecutorService;
	private final TradeScenarioProducer tradeScenarioProducer;
	private final PriceScenarioProducer priceScenarioProducer;
	private final OrderEventProducer orderEventProducer;
	private final BookingEventProducer bookingEventProducer;
	private final PriceEventProducer priceEventProducer;

	public TrafficScheduler(ScheduledExecutorService scheduledExecutorService, TradeScenarioProducer tradeScenarioProducer,
			PriceScenarioProducer priceScenarioProducer, OrderEventProducer orderEventProducer, BookingEventProducer bookingEventProducer,
			PriceEventProducer priceEventProducer) {
		this.scheduledExecutorService = scheduledExecutorService;
		this.tradeScenarioProducer = tradeScenarioProducer;
		this.priceScenarioProducer = priceScenarioProducer;
		this.orderEventProducer = orderEventProducer;
		this.bookingEventProducer = bookingEventProducer;
		this.priceEventProducer = priceEventProducer;
	}

	void schedule() {
		final Instant now = Instant.now();
		final List<TradeScenario> tradeScenarios = tradeScenarioProducer.produce();
		tradeScenarios.forEach(scenario -> {
			final InitiationEventDto initiation = scenario.getInitiation();
			final MatchingEventDto matching = scenario.getMatching();
			final BookingEventDto booking = scenario.getBooking();
			scheduledExecutorService.schedule(() -> orderEventProducer.send(initiation), Duration.between(now, initiation.getTimestamp()).toMillis(),
					TimeUnit.MILLISECONDS);
			scheduledExecutorService.schedule(() -> orderEventProducer.send(matching), Duration.between(now, matching.getTimestamp()).toMillis(),
					TimeUnit.MILLISECONDS);
			scheduledExecutorService.schedule(() -> bookingEventProducer.send(booking), Duration.between(now, booking.getTimestamp()).toMillis(),
					TimeUnit.MILLISECONDS);
		});
		final List<PriceScenario> priceScenarios = priceScenarioProducer.produce();
		priceScenarios.forEach(scenario -> {
			final PriceEventDto event = scenario.getEvent();
			scheduledExecutorService.schedule(() -> priceEventProducer.send(event), Duration.between(now, event.getTimestamp()).toMillis(),
					TimeUnit.MILLISECONDS);
		});
	}

}
