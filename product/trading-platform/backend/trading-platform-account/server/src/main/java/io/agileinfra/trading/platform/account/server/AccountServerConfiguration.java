package io.agileinfra.trading.platform.account.server;

import io.agileinfra.trading.platform.account.persistence.AccountPersistenceConfiguration;
import io.agileinfra.trading.platform.account.persistence.AccountPersistenceService;
import io.agileinfra.trading.platform.account.persistence.OrderEventPersistenceService;
import io.agileinfra.trading.platform.clock.api.ClockApiConfiguration;
import io.agileinfra.trading.platform.iam.configuration.server.TradingPlatformSecurityServerConfiguration;
import io.agileinfra.trading.platform.instrument.model.InstrumentApi;
import io.agileinfra.trading.platform.messaging.consumer.configuration.TradingPlatformMessagingConsumerConfiguration;
import io.agileinfra.trading.platform.messaging.consumer.order.OrderEventConsumerConfiguration;
import io.agileinfra.trading.platform.messaging.consumer.position.PositionEventConsumerConfiguration;
import io.agileinfra.trading.platform.messaging.consumer.position.PositionEventReactor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * @author louis.gueye@gmail.com
 */
@Configuration
@Import({OrderEventConsumerConfiguration.class, TradingPlatformMessagingConsumerConfiguration.class, PositionEventConsumerConfiguration.class,
		AccountPersistenceConfiguration.class, ClockApiConfiguration.class, TradingPlatformSecurityServerConfiguration.class})
public class AccountServerConfiguration {

	@Bean("initiated")
	public InitiationEventProcessor initiationEventProcessor(final OrderEventPersistenceService service,
			final AccountPersistenceService accountService, final AccountNotificationService notificationService) {
		return new InitiationEventProcessor(service, accountService, notificationService);
	}

	@Bean("matched")
	public MatchingEventProcessor matchingEventProcessor(final OrderEventPersistenceService service, final AccountPersistenceService accountService,
			final AccountNotificationService notificationService) {
		return new MatchingEventProcessor(service, accountService, notificationService);
	}

	@Bean("booked")
	public BookingEventProcessor bookingEventProcessor(final OrderEventPersistenceService service, final InstrumentApi instrumentApi,
			final AccountPersistenceService accountService, final AccountNotificationService notificationService) {
		return new BookingEventProcessor(service, instrumentApi, accountService, notificationService);
	}

	@Bean
	public AccountNotificationService accountNotificationService(final AccountPersistenceService accountService,
			final SimpMessagingTemplate simpMessagingTemplate) {
		return new AccountNotificationService(accountService, simpMessagingTemplate);
	}

	@Bean
	public PositionEventReactor positionEventReactor(final AccountPersistenceService accountService,
			final AccountNotificationService notificationService) {
		return new PositionEventProcessor(accountService, notificationService);
	}

}
