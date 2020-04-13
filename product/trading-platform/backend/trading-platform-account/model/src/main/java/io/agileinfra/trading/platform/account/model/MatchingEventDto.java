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
public class MatchingEventDto {
	private String order;
	private String counterpart;
	private Instant timestamp;
}
