package io.agileinfra.trading.platform.iam.persistence;

import lombok.*;
import io.agileinfra.trading.platform.iam.model.Role;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"role"})
@Builder(toBuilder = true)
@Entity
@Table(name = "user_roles", indexes = {@Index(name = "idx_user_roles_uniq", columnList = "user,role", unique = true)})
public class UserRole {
	@Id
	private String id;

	@Column(nullable = false)
	private String user;

	@Column(length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

}
