package io.agileinfra.trading.platform.messaging.producer.booking;

import io.agileinfra.trading.plateform.booking.model.BookingEventDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BookingEventProducerTest {
	@Mock
	private JmsTemplate jmsTemplate;
	@InjectMocks
	private BookingEventProducer underTest;

	@Test
	public void send_booking_event_ok() {
		// Given
		final BookingEventDto message = BookingEventDto.builder().build();

		// When
		underTest.send(message);

		// Then
		verify(jmsTemplate).convertAndSend("booking.events", message);
	}

}