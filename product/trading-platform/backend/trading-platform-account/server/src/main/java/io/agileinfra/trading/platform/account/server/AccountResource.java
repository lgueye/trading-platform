package io.agileinfra.trading.platform.account.server;

import io.agileinfra.trading.platform.account.model.AccountApi;
import io.agileinfra.trading.platform.account.model.AccountDto;
import io.agileinfra.trading.platform.account.persistence.AccountPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountResource implements AccountApi {
	private final AccountPersistenceService service;

	@PostMapping
	@Override
	public void saveAll(@RequestBody List<AccountDto> accounts) {
		service.saveAll(accounts);
	}

	@GetMapping
	@Override
	public List<AccountDto> findAllAccounts() {
		return service.findAllAccounts();
	}
}
