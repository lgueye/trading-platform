package io.agileinfra.trading.platform.messaging.consumer.order;

import io.agileinfra.trading.platform.clock.api.ClockApiConfiguration;
import io.agileinfra.trading.platform.clock.model.ClockApi;
import io.agileinfra.trading.platform.messaging.consumer.configuration.TradingPlatformMessagingConsumerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Map;

/**
 * @author louis.gueye@gmail.com
 */
@Import({TradingPlatformMessagingConsumerConfiguration.class, ClockApiConfiguration.class})
@Configuration
public class OrderEventConsumerConfiguration {
	/**
	 *
	 * @param reactors provided by the consumer
	 * @return
	 */
	@Bean
	public OrderEventConsumer orderEventConsumer(final Map<String, OrderEventReactor> reactors, final ClockApi clockApi) {
		return new OrderEventConsumer(reactors, clockApi);
	}
}
