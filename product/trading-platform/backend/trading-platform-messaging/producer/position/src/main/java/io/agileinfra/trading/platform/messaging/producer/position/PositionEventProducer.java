package io.agileinfra.trading.platform.messaging.producer.position;

import io.agileinfra.trading.platform.account.model.PositionEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class PositionEventProducer {

	private final JmsTemplate jmsTemplate;

	public void send(final PositionEventDto message) {
		jmsTemplate.convertAndSend("position.events", message);
	}
}
