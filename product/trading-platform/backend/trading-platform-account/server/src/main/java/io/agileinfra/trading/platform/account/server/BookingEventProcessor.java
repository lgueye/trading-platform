package io.agileinfra.trading.platform.account.server;

import com.google.common.collect.Sets;
import io.agileinfra.trading.platform.account.model.AccountDto;
import io.agileinfra.trading.platform.account.model.OrderEventDto;
import io.agileinfra.trading.platform.account.persistence.AccountPersistenceService;
import io.agileinfra.trading.platform.account.persistence.OrderEventPersistenceService;
import io.agileinfra.trading.platform.instrument.model.InstrumentApi;
import io.agileinfra.trading.platform.messaging.consumer.order.OrderEventReactor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class BookingEventProcessor implements OrderEventReactor {
	private final OrderEventPersistenceService service;
	private final InstrumentApi instrumentApi;
	private final AccountPersistenceService accountService;
	private final AccountNotificationService notificationService;

	@Override
	public void process(OrderEventDto message) {
		service.save(message);
		final AccountDto account = accountService.findById(message.getAccount());
		final double price = instrumentApi.findInstrumentsByIds(Sets.newHashSet(message.getInstrument())).get(0).getPrice();
		account.setCash(Double.sum(account.getCash(), -message.getQuantity() * price));
		accountService.save(account);
		notificationService.onAccountEvent(account.getId());
	}
}
