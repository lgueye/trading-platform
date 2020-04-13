package io.agileinfra.trading.platform.market.server;

import io.agileinfra.trading.platform.account.model.*;
import io.agileinfra.trading.platform.clock.model.ClockApi;
import io.agileinfra.trading.platform.messaging.producer.order.OrderEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;

@RequiredArgsConstructor
@Slf4j
public class MarketEventConsumer {

	private final ClockApi clockApi;
	private final OrderEventProducer orderEventProducer;
	private final OrderEventApi orderEventApi;

	@JmsListener(destination = "initiation.events", concurrency = "1", containerFactory = "topicListenerContainerFactory")
	public void onInitiationEvent(@Payload final InitiationEventDto message) {
		final OrderEventDto event = OrderEventDto.builder().orderId(message.getOrder()).account(message.getAccount())
				.instrument(message.getInstrument()).quantity(message.getQuantity()).status(OrderStatus.initiated).timestamp(clockApi.now()).build();
		log.info(">>>> INTPUT {}", event);
		orderEventProducer.send(event);
	}

	@JmsListener(destination = "matching.events", concurrency = "1", containerFactory = "topicListenerContainerFactory")
	public void onMatchingEvent(@Payload final MatchingEventDto message) {
		final OrderEventDto orderEvent = orderEventApi.findOrderById(message.getOrder());
		final OrderEventDto event = OrderEventDto.builder().orderId(message.getOrder()).account(orderEvent.getAccount())
				.instrument(orderEvent.getInstrument()).quantity(orderEvent.getQuantity()).status(OrderStatus.matched).timestamp(clockApi.now())
				.build();
		log.info(">>>> INTPUT {}", event);
		orderEventProducer.send(event);
	}
}
