package io.agileinfra.trading.platform.account.api;

import io.agileinfra.trading.platform.account.model.AccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import java.lang.reflect.Type;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class AccountNotificationStompFrameHandler implements StompFrameHandler {
	private final List<AccountDto> notifications;

	@Override
	public Type getPayloadType(StompHeaders headers) {
		return AccountDto.class;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		// headers.forEach((k, v) -> log.info("(k, v) ==> ({}, {})", k, v));
		notifications.add((AccountDto) payload);
	}
}
