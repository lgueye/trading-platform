package io.agileinfra.trading.platform.messaging.producer.configuration;

import io.agileinfra.trading.platform.shared.configuration.messaging.TradingPlatformSharedMessagingConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.ConnectionFactory;

/**
 * @author louis.gueye@gmail.com
 */
@Import({TradingPlatformSharedMessagingConfiguration.class})
@Configuration
public class TradingPlatformMessagingProducerConfiguration {

	@Bean
	public JmsTemplate jmsTemplate(final ConnectionFactory connectionFactory, final MessageConverter messageConverter) {
		final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		jmsTemplate.setMessageConverter(messageConverter);
		jmsTemplate.setPubSubDomain(true);
		return jmsTemplate;

	}

}
