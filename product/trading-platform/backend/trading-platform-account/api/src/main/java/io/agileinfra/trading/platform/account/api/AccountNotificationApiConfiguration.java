package io.agileinfra.trading.platform.account.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import io.agileinfra.trading.platform.account.model.AccountNotificationApi;
import io.agileinfra.trading.platform.iam.api.IamApiConfiguration;
import io.agileinfra.trading.platform.iam.configuration.client.SecurityClientHelper;
import io.agileinfra.trading.platform.shared.api.TradingPlatformSharedApiConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@Configuration
@Import({TradingPlatformSharedApiConfiguration.class, IamApiConfiguration.class})
public class AccountNotificationApiConfiguration {

	@Bean
	public SockJsClient sockJsClient(final RestTemplate restTemplate) {
		return new SockJsClient(Lists.newArrayList(new WebSocketTransport(new StandardWebSocketClient()), new RestTemplateXhrTransport(restTemplate)));
	}

	@Bean
	public WebSocketStompClient stompClient(final SockJsClient sockJsClient, final ObjectMapper objectMapper) {
		final WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
		final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setObjectMapper(objectMapper);
		stompClient.setMessageConverter(converter);
		return stompClient;
	}

	@Bean
	public AccountNotificationApi accountNotificationApi(@Value("${account.server.ws.url}") final String apiUrl,
			final SecurityClientHelper securityHelper, final WebSocketStompClient stompClient) {
		return new AccountNotificationApiImpl(apiUrl, securityHelper, stompClient);
	}

}
