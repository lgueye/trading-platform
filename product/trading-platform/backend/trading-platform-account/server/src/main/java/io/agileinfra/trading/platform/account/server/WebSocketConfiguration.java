package io.agileinfra.trading.platform.account.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

	@Autowired
	private ObjectMapper objectMapper;

	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/portfolio").withSockJS();
	}

	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/queue/");
		registry.setPreservePublishOrder(true);
	}

	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
		final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		// Avoid creating many ObjectMappers which have the same configuration.
		converter.setObjectMapper(objectMapper);
		messageConverters.add(converter);

		// Don't add default converters.
		return false;
	}

}
