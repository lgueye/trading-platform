package io.agileinfra.trading.platform.messaging.consumer.position;

import io.agileinfra.trading.platform.clock.model.ClockApi;
import io.agileinfra.trading.platform.account.model.PositionEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;

@RequiredArgsConstructor
public class PositionEventConsumer {

	private final ClockApi clockApi;
	private final PositionEventReactor positionEventReactor;

	@JmsListener(destination = "position.events", concurrency = "1", containerFactory = "topicListenerContainerFactory")
	public void onPositionEvent(@Payload final PositionEventDto message) {
		final PositionEventDto event = message.toBuilder().timestamp(clockApi.now()).build();
		positionEventReactor.process(event);
	}
}
