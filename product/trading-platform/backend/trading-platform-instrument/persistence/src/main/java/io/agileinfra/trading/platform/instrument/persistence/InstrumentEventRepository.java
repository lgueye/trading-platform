package io.agileinfra.trading.platform.instrument.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentEventRepository extends JpaRepository<InstrumentEvent, String> {
}
