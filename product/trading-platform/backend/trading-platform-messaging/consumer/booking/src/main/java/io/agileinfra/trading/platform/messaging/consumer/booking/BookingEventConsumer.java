package io.agileinfra.trading.platform.messaging.consumer.booking;

import io.agileinfra.trading.plateform.booking.model.BookingEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;

@RequiredArgsConstructor
public class BookingEventConsumer {

	private final BookingEventReactor bookingEventReactor;

	@JmsListener(destination = "booking.events", concurrency = "1", containerFactory = "topicListenerContainerFactory")
	public void onBookingEvent(@Payload final BookingEventDto message) {
		bookingEventReactor.process(message);
	}
}
