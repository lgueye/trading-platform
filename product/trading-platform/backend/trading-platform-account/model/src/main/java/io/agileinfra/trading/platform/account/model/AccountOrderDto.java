package io.agileinfra.trading.platform.account.model;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(of = {"orderId", "timestamp"})
public class AccountOrderDto {
	private String orderId;
	private String instrument;
	private double price;
	private double quantity;
	private OrderStatus status;
	private Instant timestamp;
}
