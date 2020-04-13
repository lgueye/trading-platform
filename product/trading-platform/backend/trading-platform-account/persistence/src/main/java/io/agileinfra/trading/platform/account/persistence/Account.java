package io.agileinfra.trading.platform.account.persistence;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "accounts")
@EqualsAndHashCode(of = {"id"})
public class Account {
	@Id
	private String id;

	@Column(nullable = false)
	@NotNull
	private String owner;

	@Column(nullable = false)
	@NotNull
	private Double cash;

}
