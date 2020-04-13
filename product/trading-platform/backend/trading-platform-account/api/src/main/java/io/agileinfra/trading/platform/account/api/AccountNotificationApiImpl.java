package io.agileinfra.trading.platform.account.api;

import com.google.common.collect.Lists;
import io.agileinfra.trading.platform.account.model.AccountDto;
import io.agileinfra.trading.platform.account.model.AccountNotificationApi;
import io.agileinfra.trading.platform.iam.configuration.client.SecurityClientHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
public class AccountNotificationApiImpl implements AccountNotificationApi {

	private final String apiUrl;
	private final SecurityClientHelper securityHelper;
	private final WebSocketStompClient stompClient;
	private List<AccountDto> notifications = Lists.newArrayList();

	@Override
	public void subscribe() {
		final String token = securityHelper.getToken().orElseThrow(
				() -> new IllegalStateException(
						"Expected user to be authenticated. Check credentials or make sure there is a signin steps before using this one"));
		final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
		headers.set("Authorization", String.format("Bearer %s", token));
		notifications = Lists.newArrayList(); // reset notifications each time we subscribe
		// reset frame handler and session handler instances each time we subscribe
		AccountNotificationSessionHandler sessionHandler = new AccountNotificationSessionHandler(new AccountNotificationStompFrameHandler(
				notifications));
		stompClient.connect(apiUrl, headers, sessionHandler);

		Awaitility.await() //
				.atMost(Duration.parse("PT1S").toMillis(), TimeUnit.MILLISECONDS) //
				.pollDelay(50, TimeUnit.MILLISECONDS) //
				.pollInterval(50, TimeUnit.MILLISECONDS) //
				.until(sessionHandler::connected);
	}

	@Override
	public List<AccountDto> getAllNotifications() {
		return notifications;
	}

	@PreDestroy
	public void preDestroy() {
		stompClient.stop();
	}
}
