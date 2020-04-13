package io.agileinfra.trading.platform.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OrderDto {
	private String id;
	private String account;
	private String instrument;
	private double quantity;
	private OrderStatus status;
}
