package io.agileinfra.trading.platform.booking.persistence;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "bookings")
@EqualsAndHashCode(of = {"orderId", "account", "timestamp"})
public class Booking {
	@Id
	private String id;

	@Column(nullable = false)
	@NotNull
	private String account;

	@Column(name = "order_id", nullable = false)
	@NotNull
	private String orderId;

	@Column(nullable = false)
	@NotNull
	private String instrument;

	@Column(nullable = false)
	@NotNull
	private Double price;

	@Column(nullable = false)
	@NotNull
	private Double quantity;

	@Column(columnDefinition = "varchar(255)", nullable = false)
	@NotNull
	private Instant timestamp;

}
