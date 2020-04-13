package io.agileinfra.trading.platform.instrument.persistence;

import io.agileinfra.trading.platform.instrument.model.PriceEventDto;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class InstrumentEventPersistenceService {
	private final InstrumentEventRepository repository;

	public String save(final PriceEventDto dto) {
		if (dto == null)
			throw new IllegalArgumentException("Expected non null price event but was null");
		final InstrumentEvent detached = InstrumentEvent.builder() //
				.id(UUID.randomUUID().toString()).instrument(dto.getInstrument()).price(dto.getPrice()) //
				.timestamp(dto.getTimestamp()) //
				.build();
		final InstrumentEvent persisted = repository.save(detached);
		return persisted.getId();
	}

	public List<PriceEventDto> findAll() {
		return repository.findAll()
				.stream()
				//
				.map(entity -> PriceEventDto.builder().instrument(entity.getInstrument()).price(entity.getPrice()).timestamp(entity.getTimestamp())
						.build()) //
				.collect(Collectors.toList());
	}
}
