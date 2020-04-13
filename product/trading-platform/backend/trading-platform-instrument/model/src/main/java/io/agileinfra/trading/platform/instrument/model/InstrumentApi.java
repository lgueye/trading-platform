package io.agileinfra.trading.platform.instrument.model;

import java.util.List;
import java.util.Set;

public interface InstrumentApi {
	void saveAll(List<InstrumentDto> instruments);

	List<InstrumentDto> findAllInstruments();

	List<PriceEventDto> findAllPriceEvents();

	List<InstrumentDto> findInstrumentsByIds(Set<String> instrmentIds);
}
