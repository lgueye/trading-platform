package io.agileinfra.trading.platform.instrument.persistence;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "instrument_events")
@EqualsAndHashCode(of = {"instrument", "timestamp"})
public class InstrumentEvent {
	@Id
	private String id;

	@Column(nullable = false)
	@NotNull
	private String instrument;

	@Column(nullable = false)
	@NotNull
	private Double price;

	@Column(columnDefinition = "varchar(255)", nullable = false)
	@NotNull
	private Instant timestamp;

}
