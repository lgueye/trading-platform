package io.agileinfra.trading.platform.account.model;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(of = {"instrument"})
public class PositionEventDto {
	private String instrument;
	private Instant timestamp;
}
