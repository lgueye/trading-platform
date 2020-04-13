package io.agileinfra.trading.platform.instrument.persistence;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "instruments")
@EqualsAndHashCode(of = {"id"})
public class Instrument {
	@Id
	private String id;

	@Column(nullable = false)
	@NotNull
	private Double price;

}
