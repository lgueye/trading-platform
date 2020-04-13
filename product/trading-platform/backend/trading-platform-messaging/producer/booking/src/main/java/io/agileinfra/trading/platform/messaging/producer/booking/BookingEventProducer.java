package io.agileinfra.trading.platform.messaging.producer.booking;

import io.agileinfra.trading.plateform.booking.model.BookingEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class BookingEventProducer {

	private final JmsTemplate jmsTemplate;

	public void send(final BookingEventDto message) {
		jmsTemplate.convertAndSend("booking.events", message);
	}
}
