package io.agileinfra.trading.platform.e2e.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.agileinfra.trading.platform.account.model.AccountApi;
import io.agileinfra.trading.platform.account.model.AccountDto;
import io.agileinfra.trading.platform.account.model.OrderEventApi;
import io.agileinfra.trading.platform.account.model.OrderEventDto;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class AccountApiSteps implements En {

	public AccountApiSteps(AccountApi accountApi, OrderEventApi orderEventApi, ObjectMapper objectMapper) {
		DataTableType((Map<String, String> row) -> objectMapper.convertValue(row, OrderEventDto.class));
		DataTableType((Map<String, String> row) -> objectMapper.convertValue(row, AccountDto.class));

		Given("accounts", (final DataTable datatable) -> {
			final List<AccountDto> accounts = datatable.asList(AccountDto.class);
			accountApi.saveAll(accounts);
		});
		Given("order events", (final DataTable datatable) -> {
			final List<OrderEventDto> events = datatable.asList(OrderEventDto.class);
			orderEventApi.saveAll(events);
		});

		Then("within {}, accounts",
				(final String durationAsString, final DataTable datatable) -> {
					final Duration timeout = Duration.parse(durationAsString);
					final List<String> jsons = datatable.asList(String.class);
					final List<AccountDto> expected = jsons.stream().map(json -> {
						try {
							return objectMapper.readValue(json, AccountDto.class);
						} catch (JsonProcessingException e) {
							e.printStackTrace();
							return null;
						}
					}).collect(Collectors.toList());
					Awaitility.await().atMost(timeout.toMillis(), TimeUnit.MILLISECONDS).pollDelay(50, TimeUnit.MILLISECONDS)
							.pollInterval(500, TimeUnit.MILLISECONDS).until(() -> {
								final List<AccountDto> actual = accountApi.findAllAccounts();
								// log.info("expected => {}", expected);
								// log.info("actual   => {}", actual);
									return expected.equals(actual);
								});
				});

		Then("within {}, order events",
				(final String durationAsString, final DataTable datatable) -> {
					final Duration timeout = Duration.parse(durationAsString);
					final List<OrderEventDto> expected = datatable.asList(OrderEventDto.class);
					Awaitility.await().atMost(timeout.toMillis(), TimeUnit.MILLISECONDS).pollDelay(50, TimeUnit.MILLISECONDS)
							.pollInterval(500, TimeUnit.MILLISECONDS).until(() -> {
								final List<OrderEventDto> actual = orderEventApi.findAllEvents();
								// log.info("expected => {}", expected);
								// log.info("actual   => {}", actual);
									return expected.equals(actual) && expected.equals(actual);
								});
				});
	}
}
