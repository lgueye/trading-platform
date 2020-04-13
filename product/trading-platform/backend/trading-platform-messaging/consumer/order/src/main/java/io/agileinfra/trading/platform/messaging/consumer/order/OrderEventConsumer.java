package io.agileinfra.trading.platform.messaging.consumer.order;

import io.agileinfra.trading.platform.account.model.OrderEventDto;
import io.agileinfra.trading.platform.clock.model.ClockApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

	private final Map<String, OrderEventReactor> reactors;
	private final ClockApi clockApi;

	@JmsListener(destination = "order.events", concurrency = "1", containerFactory = "topicListenerContainerFactory")
	public void onOrderEvent(@Payload final OrderEventDto message) {
		Optional.of(reactors.get(message.getStatus().name())).ifPresent(
				reactor -> reactor.process(message.toBuilder().timestamp(clockApi.now()).build()));
	}
}
