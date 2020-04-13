package io.agileinfra.trading.platform.instrument.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PriceEventDto {
	private String instrument;
	private double price;
	private Instant timestamp;
}
