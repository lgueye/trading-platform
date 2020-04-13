package io.agileinfra.trading.platform.iam.configuration.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.agileinfra.trading.platform.iam.model.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtProvider {
	private final String secretKey;
	private final Duration expiration;
	private final ObjectMapper objectMapper;

	public String serialize(final UserDto user, final Instant now) {
		Claims claims = Jwts.claims().setSubject(user.getId());
		try {
			claims.put("auth", objectMapper.writeValueAsString(user));
		} catch (JsonProcessingException e) {
			// ignored
			log.debug("Failed to serialize user {} to json", user);
			return null;
		}
		final Date issuedAt = new Date(now.toEpochMilli());
		final Date validUntil = new Date(now.plus(expiration).toEpochMilli());
		return Jwts.builder()//
				.setClaims(claims)//
				.setIssuedAt(issuedAt)//
				.setExpiration(validUntil)//
				.signWith(SignatureAlgorithm.HS256, secretKey)//
				.compact();
	}

	Optional<String> extract(String header) {
		if (Strings.isNullOrEmpty(header))
			return Optional.empty();
		return Lists.newArrayList(Splitter.on(" ").split(header)).stream() //
				.filter(token -> !Strings.isNullOrEmpty(token) && !"Bearer".equals(token)) //
				.findFirst();
	}

	Optional<UserDto> deserialize(String token, final Instant now) {
		try {
			final String auth = (String) Jwts.parser() //
					.setClock(() -> new Date(now.toEpochMilli())) //
					.setSigningKey(secretKey) //
					.parseClaimsJws(token).getBody() //
					.get("auth");
			return Optional.of(objectMapper.readValue(auth, UserDto.class));
		} catch (JwtException | IllegalArgumentException | IOException e) {
			return Optional.empty();
		}
	}

}
