package io.agileinfra.trading.platform.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class InitiationEventDto {
	private String order;
	private String account;
	private String instrument;
	private double quantity;
	private Instant timestamp;
}
