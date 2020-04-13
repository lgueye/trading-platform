package io.agileinfra.trading.platform.account.server;

import io.agileinfra.trading.platform.account.model.PositionEventDto;
import io.agileinfra.trading.platform.account.persistence.AccountPersistenceService;
import io.agileinfra.trading.platform.messaging.consumer.position.PositionEventReactor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class PositionEventProcessor implements PositionEventReactor {

	private final AccountPersistenceService accountService;
	private final AccountNotificationService notificationService;

	@Override
	public void process(PositionEventDto message) {
		accountService.findAllAccounts()
				.stream()
				//
				.filter(account -> account.getAssets().stream().anyMatch(asset -> asset.getInstrument().equals(message.getInstrument()))
						|| account.getOrders().stream().anyMatch(order -> order.getInstrument().equals(message.getInstrument()))) //
				.forEach(account -> {
					notificationService.onAccountEvent(account.getId());
					log.info("New price event {} => sent account {} notification", message, account.getId());
				});
	}
}
