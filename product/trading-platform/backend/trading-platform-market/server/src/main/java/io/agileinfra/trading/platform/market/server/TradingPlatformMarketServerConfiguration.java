package io.agileinfra.trading.platform.market.server;

import io.agileinfra.trading.platform.account.api.AccountApiConfiguration;
import io.agileinfra.trading.platform.account.model.OrderEventApi;
import io.agileinfra.trading.platform.clock.api.ClockApiConfiguration;
import io.agileinfra.trading.platform.clock.model.ClockApi;
import io.agileinfra.trading.platform.messaging.consumer.configuration.TradingPlatformMessagingConsumerConfiguration;
import io.agileinfra.trading.platform.messaging.producer.order.OrderEventProducer;
import io.agileinfra.trading.platform.messaging.producer.order.OrderEventProducerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author louis.gueye@gmail.com
 */
@Configuration
@Import({TradingPlatformMessagingConsumerConfiguration.class, ClockApiConfiguration.class, OrderEventProducerConfiguration.class,
		AccountApiConfiguration.class})
public class TradingPlatformMarketServerConfiguration {
	@Bean
	public MarketEventConsumer marketEventConsumer(final ClockApi clockApi, final OrderEventProducer orderEventProducer,
			final OrderEventApi orderEventApi) {
		return new MarketEventConsumer(clockApi, orderEventProducer, orderEventApi);
	}
}
