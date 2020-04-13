package io.agileinfra.trading.platform.iam.configuration.client;

import io.agileinfra.trading.platform.iam.model.AuthenticatedUserDto;
import io.agileinfra.trading.platform.iam.model.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class SecurityClientHelper {

	public void updateSecurityContext(AuthenticatedUserDto authenticatedUser) {
		final UserDto user = authenticatedUser.getPrincipal();
		final List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> (GrantedAuthority) role::name).collect(Collectors.toList());
		final UserDetails userDetails = new User(user.getId(), authenticatedUser.getToken(), true, false, true, true, authorities);
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, authenticatedUser, authorities));
	}

	public void clearSecurityContext() {
		SecurityContextHolder.clearContext();
	}

	public Optional<String> getBusinessId() {
		if (SecurityContextHolder.getContext() == null) {
			return Optional.empty();
		}
		final SecurityContext context = SecurityContextHolder.getContext();
		if (context.getAuthentication() == null) {
			return Optional.empty();
		}
		Authentication authentication = context.getAuthentication();
		if (authentication == null) {
			return Optional.empty();
		}
		final Object credentials = authentication.getCredentials();
		if (!(credentials instanceof AuthenticatedUserDto))
			return Optional.empty();
		final UserDto dto = ((AuthenticatedUserDto) credentials).getPrincipal();
		if (dto == null) {
			return Optional.empty();
		}
		return Optional.ofNullable(dto.getId());
	}

	public Optional<String> getToken() {
		if (SecurityContextHolder.getContext() == null) {
			return Optional.empty();
		}
		final SecurityContext context = SecurityContextHolder.getContext();
		if (context.getAuthentication() == null) {
			return Optional.empty();
		}
		Authentication authentication = context.getAuthentication();
		if (authentication == null) {
			return Optional.empty();
		}
		final Object credentials = authentication.getCredentials();
		if (!(credentials instanceof AuthenticatedUserDto)) {
			return Optional.empty();
		}

		return Optional.ofNullable(((AuthenticatedUserDto) credentials).getToken());
	}
	public Optional<UserDto> getUser() {
		if (SecurityContextHolder.getContext() == null) {
			return Optional.empty();
		}
		final SecurityContext context = SecurityContextHolder.getContext();
		if (context.getAuthentication() == null) {
			return Optional.empty();
		}
		Authentication authentication = context.getAuthentication();
		if (authentication == null) {
			return Optional.empty();
		}
		final Object credentials = authentication.getCredentials();
		if (!(credentials instanceof AuthenticatedUserDto))
			return Optional.empty();
		final UserDto dto = ((AuthenticatedUserDto) credentials).getPrincipal();
		if (dto == null) {
			return Optional.empty();
		}
		return Optional.of(dto);
	}
}
