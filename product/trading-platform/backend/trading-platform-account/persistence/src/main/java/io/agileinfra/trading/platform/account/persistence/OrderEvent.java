package io.agileinfra.trading.platform.account.persistence;

import io.agileinfra.trading.platform.account.model.OrderStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "order_events")
@EqualsAndHashCode(of = {"orderId", "account", "timestamp"})
public class OrderEvent {
	@Id
	private String id;

	@Column(name = "order_id", nullable = false)
	@NotNull
	private String orderId;

	@Column(nullable = false)
	@NotNull
	private String account;

	@Column(nullable = false)
	@NotNull
	private String instrument;

	@Column(nullable = false)
	@NotNull
	private Double quantity;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private OrderStatus status;

	@Column(columnDefinition = "varchar(255)", nullable = false)
	@NotNull
	private Instant timestamp;

}
