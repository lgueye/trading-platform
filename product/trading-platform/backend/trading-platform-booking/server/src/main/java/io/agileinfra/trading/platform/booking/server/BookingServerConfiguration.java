package io.agileinfra.trading.platform.booking.server;

import io.agileinfra.trading.platform.account.model.OrderEventApi;
import io.agileinfra.trading.platform.booking.persistence.BookingPersistenceConfiguration;
import io.agileinfra.trading.platform.booking.persistence.BookingPersistenceService;
import io.agileinfra.trading.platform.clock.api.ClockApiConfiguration;
import io.agileinfra.trading.platform.clock.model.ClockApi;
import io.agileinfra.trading.platform.messaging.consumer.booking.BookingEventConsumerConfiguration;
import io.agileinfra.trading.platform.messaging.producer.order.OrderEventProducer;
import io.agileinfra.trading.platform.messaging.producer.order.OrderEventProducerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author louis.gueye@gmail.com
 */
@Configuration
@Import({BookingEventConsumerConfiguration.class, OrderEventProducerConfiguration.class, BookingPersistenceConfiguration.class,
		ClockApiConfiguration.class})
public class BookingServerConfiguration {
	@Bean
	public BookingEventProcessor bookingEventProcessor(final ClockApi clockApi, final OrderEventProducer orderEventProducer,
			final OrderEventApi orderEventApi, final BookingPersistenceService service) {
		return new BookingEventProcessor(clockApi, orderEventProducer, orderEventApi, service);
	}
}
