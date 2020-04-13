package io.agileinfra.trading.platform.iam.persistence;

import io.agileinfra.trading.platform.clock.model.ClockApi;
import io.agileinfra.trading.platform.iam.configuration.server.JwtProvider;
import io.agileinfra.trading.platform.iam.model.AuthenticatedUserDto;
import io.agileinfra.trading.platform.iam.model.BusinessException;
import io.agileinfra.trading.platform.iam.model.SigninRequestDto;
import io.agileinfra.trading.platform.iam.model.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class IamPersistenceService {
	private final UserRepository repository;
	private final UserRoleRepository roleRepository;
	private final JwtProvider jwtProvider;
	private final ClockApi clockApi;

	/**
	 * Throws exception
	 * Builds jwt token
	 *
	 * @param request
	 * @return true if a
	 */
	public AuthenticatedUserDto signin(final SigninRequestDto request) {
		final String login = request.getLogin();
		final User persisted = repository //
				.findOne(Example.of(User.builder().login(login).password(request.getPassword()).build())) //
				.orElseThrow(() -> new BusinessException("not.found", "User " + login + " was not found"));
		final List<UserRole> roles = roleRepository.findAll(Example.of(UserRole.builder().user(persisted.getId()).build()));
		final UserDto dto = UserDto.builder().id(persisted.getId()).login(persisted.getLogin())
				.roles(roles.stream().map(UserRole::getRole).collect(Collectors.toSet())).build();
		final String token = jwtProvider.serialize(dto, clockApi.now());
		return AuthenticatedUserDto.builder().token(token).principal(dto).build();
	}

}
