package io.agileinfra.trading.platform.messaging.consumer.configuration;

import io.agileinfra.trading.platform.shared.configuration.messaging.TradingPlatformSharedMessagingConfiguration;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.ConnectionFactory;

/**
 * @author louis.gueye@gmail.com
 */
@EnableJms
@Import({TradingPlatformSharedMessagingConfiguration.class})
@Configuration
public class TradingPlatformMessagingConsumerConfiguration {

	@Bean("queueListenerContainerFactory")
	public JmsListenerContainerFactory queueListenerContainerFactory(final ConnectionFactory connectionFactory,
			final MessageConverter messageConverter) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(messageConverter);
		factory.setConcurrency("1-1");
		return factory;
	}

	@Bean("topicListenerContainerFactory")
	public JmsListenerContainerFactory topicListenerContainerFactory(final ConnectionFactory connectionFactory,
			final MessageConverter messageConverter, final DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		factory.setMessageConverter(messageConverter);
		factory.setConcurrency("1-1");
		factory.setPubSubDomain(true);
		return factory;
	}

}
