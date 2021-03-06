package io.agileinfra.trading.platform.messaging.producer.price;

import io.agileinfra.trading.platform.messaging.producer.configuration.TradingPlatformMessagingProducerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author louis.gueye@gmail.com
 */
@Import(TradingPlatformMessagingProducerConfiguration.class)
@Configuration
public class PriceEventProducerConfiguration {
	@Bean
	public PriceEventProducer priceEventProducer(final JmsTemplate jmsTemplate) {
		return new PriceEventProducer(jmsTemplate);
	}
}
