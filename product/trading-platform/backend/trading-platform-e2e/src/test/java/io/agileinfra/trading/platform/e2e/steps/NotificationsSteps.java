package io.agileinfra.trading.platform.e2e.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import io.agileinfra.trading.platform.account.model.AccountDto;
import io.agileinfra.trading.platform.account.model.AccountNotificationApi;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class NotificationsSteps implements En {

	public NotificationsSteps(final AccountNotificationApi notificationApi, ObjectMapper objectMapper) {
		Given("subscribed to notifications", notificationApi::subscribe);

		Then("within {}, notifications",
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
					final List<AccountDto> actual = Lists.newArrayList();
					Awaitility.await().atMost(timeout.toMillis(), TimeUnit.MILLISECONDS).pollDelay(50, TimeUnit.MILLISECONDS)
							.pollInterval(500, TimeUnit.MILLISECONDS).until(() -> {
								actual.clear();
								actual.addAll(notificationApi.getAllNotifications());
								// log.info("expected => {}", expected);
								// log.info("actual   => {}", actual);
									return expected.equals(actual);
								});
					for (int i = 0; i < expected.size(); i++) {
						Assertions.assertEquals(expected.get(i).getValuation(), actual.get(i).getValuation());
					}
				});
	}
}
