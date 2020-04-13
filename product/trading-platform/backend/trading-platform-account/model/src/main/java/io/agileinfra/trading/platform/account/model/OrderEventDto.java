package io.agileinfra.trading.platform.account.model;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(of = {"orderId", "account", "timestamp"})
public class OrderEventDto {
	private String orderId;
	private String account;
	private String instrument;
	private double quantity;
	private OrderStatus status;
	private Instant timestamp;
}
