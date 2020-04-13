package io.agileinfra.trading.platform.instrument.server;

import io.agileinfra.trading.platform.instrument.model.InstrumentApi;
import io.agileinfra.trading.platform.instrument.model.InstrumentDto;
import io.agileinfra.trading.platform.instrument.model.PriceEventDto;
import io.agileinfra.trading.platform.instrument.persistence.InstrumentEventPersistenceService;
import io.agileinfra.trading.platform.instrument.persistence.InstrumentPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/instruments")
@RequiredArgsConstructor
public class InstrumentResource implements InstrumentApi {
	private final InstrumentPersistenceService service;
	private final InstrumentEventPersistenceService eventService;

	@PostMapping
	@Override
	public void saveAll(@RequestBody List<InstrumentDto> instruments) {
		service.saveAll(instruments);
	}

	@GetMapping
	@Override
	public List<InstrumentDto> findAllInstruments() {
		return service.findAll();
	}

	@GetMapping
	@RequestMapping("events")
	@Override
	public List<PriceEventDto> findAllPriceEvents() {
		return eventService.findAll();
	}

	@GetMapping("search")
	@Override
	public List<InstrumentDto> findInstrumentsByIds(@RequestParam("ids") Set<String> ids) {
		return service.findInstrumentsByIds(ids);
	}
}
