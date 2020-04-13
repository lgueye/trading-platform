package io.agileinfra.trading.platform.booking.server;

import io.agileinfra.trading.plateform.booking.model.BookingEventDto;
import io.agileinfra.trading.platform.account.model.OrderEventApi;
import io.agileinfra.trading.platform.account.model.OrderEventDto;
import io.agileinfra.trading.platform.account.model.OrderStatus;
import io.agileinfra.trading.platform.booking.persistence.BookingPersistenceService;
import io.agileinfra.trading.platform.clock.model.ClockApi;
import io.agileinfra.trading.platform.messaging.consumer.booking.BookingEventReactor;
import io.agileinfra.trading.platform.messaging.producer.order.OrderEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class BookingEventProcessor implements BookingEventReactor {
	private final ClockApi clockApi;
	private final OrderEventProducer orderEventProducer;
	private final OrderEventApi orderEventApi;
	private final BookingPersistenceService service;

	@Override
	public void process(BookingEventDto message) {
		service.save(message.toBuilder().timestamp(clockApi.now()).build());
		final OrderEventDto orderEvent = orderEventApi.findOrderById(message.getOrder());
		final OrderEventDto event = OrderEventDto.builder().orderId(message.getOrder()).account(orderEvent.getAccount())
				.instrument(orderEvent.getInstrument()).quantity(orderEvent.getQuantity()).status(OrderStatus.booked).timestamp(clockApi.now())
				.build();
		log.info(">>>> INTPUT {}", event);
		orderEventProducer.send(event);
		orderEventProducer.send(event.toBuilder().account(message.getCounterpart()).quantity(-orderEvent.getQuantity()).build());

	}
}
