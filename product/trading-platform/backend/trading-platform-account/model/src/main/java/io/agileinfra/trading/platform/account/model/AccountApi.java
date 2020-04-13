package io.agileinfra.trading.platform.account.model;

import java.util.List;

public interface AccountApi {
	void saveAll(List<AccountDto> accounts);
	List<AccountDto> findAllAccounts();
}
