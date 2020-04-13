package io.agileinfra.trading.platform.iam.model;

import lombok.*;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(of = {"role"})
public class UserRoleDto {
	private Role role;
}
