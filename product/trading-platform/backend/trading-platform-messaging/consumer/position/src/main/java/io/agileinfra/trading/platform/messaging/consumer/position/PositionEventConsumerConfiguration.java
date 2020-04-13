package io.agileinfra.trading.platform.messaging.consumer.position;

import io.agileinfra.trading.platform.clock.api.ClockApiConfiguration;
import io.agileinfra.trading.platform.clock.model.ClockApi;
import io.agileinfra.trading.platform.messaging.consumer.configuration.TradingPlatformMessagingConsumerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author louis.gueye@gmail.com
 */
@Import({TradingPlatformMessagingConsumerConfiguration.class, ClockApiConfiguration.class})
@Configuration
public class PositionEventConsumerConfiguration {
	/**
	 *
	 * @param clockApi
	 * @param positionEventReactor
	 * @return
	 */
	@Bean
	public PositionEventConsumer positionEventConsumer(final ClockApi clockApi, final PositionEventReactor positionEventReactor) {
		return new PositionEventConsumer(clockApi, positionEventReactor);
	}
}
