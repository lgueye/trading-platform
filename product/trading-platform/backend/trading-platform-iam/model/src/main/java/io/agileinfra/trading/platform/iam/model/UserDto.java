package io.agileinfra.trading.platform.iam.model;

import com.google.common.collect.Sets;
import lombok.*;

import java.util.Set;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
public class UserDto {
	private String id;
	private String login;
	private Set<Role> roles = Sets.newHashSet();
	public void addRoles(final Set<Role> newRoles) {
		if (roles == null)
			roles = Sets.newHashSet();
		roles.addAll(newRoles);
	}
}
