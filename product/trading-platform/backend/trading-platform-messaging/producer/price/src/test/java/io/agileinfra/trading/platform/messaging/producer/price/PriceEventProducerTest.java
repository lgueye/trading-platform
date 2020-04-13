package io.agileinfra.trading.platform.messaging.producer.price;

import io.agileinfra.trading.platform.instrument.model.PriceEventDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PriceEventProducerTest {
	@Mock
	private JmsTemplate jmsTemplate;
	@InjectMocks
	private PriceEventProducer underTest;

	@Test
	public void send_price_event_ok() {
		// Given
		final PriceEventDto message = PriceEventDto.builder().build();

		// When
		underTest.send(message);

		// Then
		verify(jmsTemplate).convertAndSend("price.events", message);
	}

}