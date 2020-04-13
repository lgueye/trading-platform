package io.agileinfra.trading.platform.instrument.server;

import io.agileinfra.trading.platform.clock.api.ClockApiConfiguration;
import io.agileinfra.trading.platform.instrument.persistence.InstrumentEventPersistenceService;
import io.agileinfra.trading.platform.instrument.persistence.InstrumentPersistenceConfiguration;
import io.agileinfra.trading.platform.instrument.persistence.InstrumentPersistenceService;
import io.agileinfra.trading.platform.messaging.consumer.price.PriceEventConsumerConfiguration;
import io.agileinfra.trading.platform.messaging.consumer.price.PriceEventReactor;
import io.agileinfra.trading.platform.messaging.producer.position.PositionEventProducer;
import io.agileinfra.trading.platform.messaging.producer.position.PositionEventProducerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author louis.gueye@gmail.com
 */
@Configuration
@Import({InstrumentPersistenceConfiguration.class, PriceEventConsumerConfiguration.class, PositionEventProducerConfiguration.class,
		ClockApiConfiguration.class})
public class InstrumentServerConfiguration {

	@Bean
	public PriceEventReactor priceEventProcessor(final InstrumentPersistenceService service, final InstrumentEventPersistenceService eventService,
			final PositionEventProducer positionEventProducer) {
		return new PriceEventProcessor(service, eventService, positionEventProducer);
	}

}
