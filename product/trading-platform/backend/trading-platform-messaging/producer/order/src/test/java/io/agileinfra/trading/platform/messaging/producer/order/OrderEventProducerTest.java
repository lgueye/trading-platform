package io.agileinfra.trading.platform.messaging.producer.order;

import io.agileinfra.trading.platform.account.model.OrderEventDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderEventProducerTest {
	@Mock
	private JmsTemplate jmsTemplate;
	@InjectMocks
	private OrderEventProducer underTest;

	@Test
	public void send_order_event_ok() {
		// Given
		final OrderEventDto message = OrderEventDto.builder().build();

		// When
		underTest.send(message);

		// Then
		verify(jmsTemplate).convertAndSend("order.events", message);
	}

}