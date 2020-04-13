package io.agileinfra.trading.platform.instrument.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface InstrumentRepository extends JpaRepository<Instrument, String> {
	List<Instrument> findByIdIn(Set<String> ids);
}
