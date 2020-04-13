package io.agileinfra.trading.platform.account.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Slf4j
public class AccountNotificationSessionHandler extends StompSessionHandlerAdapter {
	private final AccountNotificationStompFrameHandler frameHandler;
	private StompSession session;

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		log.info("CONNECT FRAME {}", new String((byte[]) payload, StandardCharsets.UTF_8));
	}

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		this.session = session;
		// log.info("WS session Id = {}", session.getSessionId());
		// connectedHeaders.forEach((k, v) -> log.info("(k, v) ==> ({}, {})", k, v));
		session.subscribe("/user/queue/account-notifications", frameHandler);
	}

	public boolean connected() {
		return session != null;
	}

	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
		log.error("HANDLE EXCEPTION", exception);
	}

	@Override
	public void handleTransportError(StompSession session, Throwable exception) {
		log.info("HANDLE TRANSPORT ERROR {}}", exception.getMessage());
	}

}
