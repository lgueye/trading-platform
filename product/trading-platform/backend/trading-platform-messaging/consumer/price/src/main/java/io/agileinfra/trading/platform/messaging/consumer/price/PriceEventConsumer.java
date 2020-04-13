package io.agileinfra.trading.platform.messaging.consumer.price;

import io.agileinfra.trading.platform.clock.model.ClockApi;
import io.agileinfra.trading.platform.instrument.model.PriceEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;

@RequiredArgsConstructor
public class PriceEventConsumer {

	private final ClockApi clockApi;
	private final PriceEventReactor priceEventReactor;

	@JmsListener(destination = "price.events", concurrency = "1", containerFactory = "topicListenerContainerFactory")
	public void onPriceEvent(@Payload final PriceEventDto message) {
		final PriceEventDto event = message.toBuilder().timestamp(clockApi.now()).build();
		priceEventReactor.process(event);
	}
}
