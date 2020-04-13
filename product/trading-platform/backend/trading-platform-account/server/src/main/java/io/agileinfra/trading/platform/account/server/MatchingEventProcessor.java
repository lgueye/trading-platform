package io.agileinfra.trading.platform.account.server;

import io.agileinfra.trading.platform.account.model.AccountDto;
import io.agileinfra.trading.platform.account.model.OrderEventDto;
import io.agileinfra.trading.platform.account.persistence.AccountPersistenceService;
import io.agileinfra.trading.platform.account.persistence.OrderEventPersistenceService;
import io.agileinfra.trading.platform.messaging.consumer.order.OrderEventReactor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class MatchingEventProcessor implements OrderEventReactor {
	private final OrderEventPersistenceService service;
	private final AccountPersistenceService accountService;
	private final AccountNotificationService notificationService;
	@Override
	public void process(OrderEventDto message) {
		service.save(message);
		final AccountDto account = accountService.findById(message.getAccount());
		notificationService.onAccountEvent(account.getId());
	}
}
