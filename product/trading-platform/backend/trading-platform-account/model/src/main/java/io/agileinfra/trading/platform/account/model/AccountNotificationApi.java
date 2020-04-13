package io.agileinfra.trading.platform.account.model;

import java.util.List;

public interface AccountNotificationApi {
	void subscribe();

	List<AccountDto> getAllNotifications();
}
