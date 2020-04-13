package io.agileinfra.trading.platform.instrument.server;

import io.agileinfra.trading.platform.account.model.PositionEventDto;
import io.agileinfra.trading.platform.instrument.model.InstrumentDto;
import io.agileinfra.trading.platform.instrument.model.PriceEventDto;
import io.agileinfra.trading.platform.instrument.persistence.InstrumentEventPersistenceService;
import io.agileinfra.trading.platform.instrument.persistence.InstrumentPersistenceService;
import io.agileinfra.trading.platform.messaging.consumer.price.PriceEventReactor;
import io.agileinfra.trading.platform.messaging.producer.position.PositionEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class PriceEventProcessor implements PriceEventReactor {
	private final InstrumentPersistenceService service;
	private final InstrumentEventPersistenceService eventService;
	private final PositionEventProducer positionEventProducer;

	@Override
	public void process(PriceEventDto message) {
		// update / add instrument
		service.save(InstrumentDto.builder().id(message.getInstrument()).price(message.getPrice()).build());
		// persist event
		eventService.save(message);
		positionEventProducer.send(PositionEventDto.builder().instrument(message.getInstrument()).build());
	}
}
