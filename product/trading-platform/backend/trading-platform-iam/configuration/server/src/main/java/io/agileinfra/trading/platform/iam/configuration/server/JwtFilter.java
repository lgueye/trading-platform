package io.agileinfra.trading.platform.iam.configuration.server;

import io.agileinfra.trading.platform.clock.model.ClockApi;
import io.agileinfra.trading.platform.iam.model.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
	final JwtProvider jwtProvider;
	final ClockApi clockApi;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			final Optional<String> tokenOptional = jwtProvider.extract(httpServletRequest.getHeader("Authorization"));
			tokenOptional.ifPresent(token -> {
				final Optional<UserDto> userOptional = jwtProvider.deserialize(token, clockApi.now());
				userOptional.ifPresent(credentials -> {
					final List<GrantedAuthority> authorities = credentials.getRoles().stream().map(role -> (GrantedAuthority) role::name)
							.collect(Collectors.toList());
					final UserDetails userDetails = new User(credentials.getId(), token, true, false, true, true, authorities);
					SecurityContextHolder.getContext().setAuthentication(
							new UsernamePasswordAuthenticationToken(userDetails, credentials, authorities));
					// Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					// log.info("principal = " + authentication.getPrincipal());
					// log.info("name = " + authentication.getName());
					// log.info("authorities = " + authentication.getAuthorities());
					// log.info("credentials = " + authentication.getCredentials());
					// log.info("details = " + authentication.getDetails());
					});
			});
		} catch (Exception ex) {
			// this is very important, since it guarantees the user is not authenticated at all
			SecurityContextHolder.clearContext();
			return;
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

}
