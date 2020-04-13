package io.agileinfra.trading.platform.messaging.producer.price;

import io.agileinfra.trading.platform.instrument.model.PriceEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class PriceEventProducer {

	private final JmsTemplate jmsTemplate;

	public void send(final PriceEventDto message) {
		jmsTemplate.convertAndSend("price.events", message);
	}
}
