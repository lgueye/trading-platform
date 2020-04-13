package io.agileinfra.trading.platform.account.server;

import io.agileinfra.trading.platform.account.model.AccountDto;
import io.agileinfra.trading.platform.account.persistence.AccountPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@RequiredArgsConstructor
@Slf4j
public class AccountNotificationService {
	private final AccountPersistenceService accountService;
	private final SimpMessagingTemplate simpMessagingTemplate;

	public void onAccountEvent(final String accountId) {
		// fetch latest view from store
		AccountDto account = accountService.findById(accountId);
		try {
			simpMessagingTemplate.convertAndSendToUser(account.getOwner(), "/queue/account-notifications", account);
		} catch (Exception e) {
			log.error("Failed to notify user {} of account update", accountId);
		}
	}
}
