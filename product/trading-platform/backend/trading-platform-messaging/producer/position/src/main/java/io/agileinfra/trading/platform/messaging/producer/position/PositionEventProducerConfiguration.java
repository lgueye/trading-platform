package io.agileinfra.trading.platform.messaging.producer.position;

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
public class PositionEventProducerConfiguration {
	@Bean
	public PositionEventProducer positionEventProducer(final JmsTemplate jmsTemplate) {
		return new PositionEventProducer(jmsTemplate);
	}
}
