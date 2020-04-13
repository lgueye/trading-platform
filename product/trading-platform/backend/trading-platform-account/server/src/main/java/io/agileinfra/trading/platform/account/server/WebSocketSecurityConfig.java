package io.agileinfra.trading.platform.account.server;

import io.agileinfra.trading.platform.iam.model.Role;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

import static org.springframework.messaging.simp.SimpMessageType.MESSAGE;
import static org.springframework.messaging.simp.SimpMessageType.SUBSCRIBE;

//@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
	@Override
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
		messages.nullDestMatcher().authenticated().simpSubscribeDestMatchers("/user/queue/errors").permitAll()
				//
				.simpSubscribeDestMatchers("/user/**").hasRole(Role.user.name()).simpTypeMatchers(SUBSCRIBE, MESSAGE).denyAll().anyMessage()
				.denyAll();
	}

	@Override
	protected boolean sameOriginDisabled() {
		// While CSRF is disabled..
		return true;
	}
}
