package io.agileinfra.trading.platform.messaging.consumer.booking;

import io.agileinfra.trading.platform.messaging.consumer.configuration.TradingPlatformMessagingConsumerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author louis.gueye@gmail.com
 */
@Import(TradingPlatformMessagingConsumerConfiguration.class)
@Configuration
public class BookingEventConsumerConfiguration {
	/**
	 *
	 * @param bookingEventReactor provided by the consumer
	 * @return
	 */
	@Bean
	public BookingEventConsumer bookingEventConsumer(final BookingEventReactor bookingEventReactor) {
		return new BookingEventConsumer(bookingEventReactor);
	}
}
