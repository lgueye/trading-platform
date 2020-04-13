package io.agileinfra.trading.platform.messaging.producer.order;

import io.agileinfra.trading.platform.account.model.InitiationEventDto;
import io.agileinfra.trading.platform.account.model.MatchingEventDto;
import io.agileinfra.trading.platform.account.model.OrderEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class OrderEventProducer {

	private final JmsTemplate jmsTemplate;

	public void send(final OrderEventDto message) {
		jmsTemplate.convertAndSend("order.events", message);
	}

	public void send(final InitiationEventDto message) {
		jmsTemplate.convertAndSend("initiation.events", message);
	}

	public void send(final MatchingEventDto message) {
		jmsTemplate.convertAndSend("matching.events", message);
	}
}
