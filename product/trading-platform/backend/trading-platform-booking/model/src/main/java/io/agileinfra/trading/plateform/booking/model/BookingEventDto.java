package io.agileinfra.trading.plateform.booking.model;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(of = {"order", "counterpart", "timestamp"})
public class BookingEventDto {
	private String order;
	private String counterpart;
	private Instant timestamp;
}
