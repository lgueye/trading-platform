package io.agileinfra.trading.platform.instrument.persistence;

import com.google.common.base.Strings;
import io.agileinfra.trading.platform.instrument.model.InstrumentDto;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class InstrumentPersistenceService {
	private final InstrumentRepository repository;

	public String save(final InstrumentDto dto) {
		Instrument detached = Instrument.builder().id(dto.getId()).price(dto.getPrice()).build();
		if (Strings.isNullOrEmpty(dto.getId())) {
			detached = Instrument.builder().id(UUID.randomUUID().toString()).price(dto.getPrice()).build();
		}
		final Instrument persisted = repository.save(detached);
		return persisted.getId();
	}

	public InstrumentDto findById(final String id) {
		return repository.findById(id).map(entity -> InstrumentDto.builder().id(entity.getId()).price(entity.getPrice()).build()).orElse(null);
	}

	public List<InstrumentDto> findAll() {
		return repository.findAll().stream() //
				.map(entity -> InstrumentDto.builder().id(entity.getId()).price(entity.getPrice()).build()) //
				.collect(Collectors.toList());
	}

	public void saveAll(List<InstrumentDto> instruments) {
		repository.saveAll(instruments.stream().map(dto -> Instrument.builder().id(dto.getId()).price(dto.getPrice()).build())
				.collect(Collectors.toList()));
	}

	public List<InstrumentDto> findInstrumentsByIds(Set<String> ids) {
		return repository.findByIdIn(ids).stream() //
				.map(entity -> InstrumentDto.builder().id(entity.getId()).price(entity.getPrice()).build()) //
				.collect(Collectors.toList());
	}
}
