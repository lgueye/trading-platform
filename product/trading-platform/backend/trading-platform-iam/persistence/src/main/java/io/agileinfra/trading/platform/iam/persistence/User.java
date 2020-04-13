package io.agileinfra.trading.platform.iam.persistence;

import com.google.common.base.MoreObjects;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
@Entity
@Table(name = "users", indexes = {@Index(name = "idx_user_login", columnList = "login", unique = true)})
public class User {
	@Id
	private String id;
	@Column
	@Size(max = 255)
	private String login;
	@Column
	@Size(max = 255)
	private String password;

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("id", id).add("login", login).add("password", "***************").toString();
	}
}
