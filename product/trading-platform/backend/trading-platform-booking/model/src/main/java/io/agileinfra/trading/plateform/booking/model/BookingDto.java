package io.agileinfra.trading.plateform.booking.model;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(of = {"orderId", "account", "timestamp"})
public class BookingDto {
	private String orderId;
	private String account;
	private String instrument;
	private double quantity;
	private Instant timestamp;
}
