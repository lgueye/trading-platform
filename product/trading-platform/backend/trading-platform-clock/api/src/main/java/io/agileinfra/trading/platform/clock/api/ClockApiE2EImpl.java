package io.agileinfra.trading.platform.clock.api;

import io.agileinfra.trading.platform.clock.model.ClockApi;
import io.agileinfra.trading.platform.clock.model.ClockDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor
@Slf4j
public class ClockApiE2EImpl implements ClockApi {
	private final String apiUrl;
	private final RestTemplate restTemplate;

	@Override
	public void freeze(ClockDto newClock) {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(APPLICATION_JSON);
		final HttpEntity<ClockDto> request = new HttpEntity<>(newClock, headers);
		final URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl).path("/api/v1/clock/freeze").build().encode().toUri();
		restTemplate.exchange(uri, HttpMethod.POST, request, Void.class);

	}

	@Override
	public Instant now() {
		try {
			final HttpHeaders headers = new HttpHeaders();
			final HttpEntity<Void> request = new HttpEntity<>(headers);
			final URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl).path("/api/v1/clock/now").build().encode().toUri();
			final ResponseEntity<Instant> response = restTemplate.exchange(uri, HttpMethod.GET, request, Instant.class);
			if (response.getBody() == null) {
				return Instant.now();
			}
			return response.getBody();
		} catch (Exception e) {
			log.debug("Could not connect to clock server. Returning actual Instant", e);
			return Instant.now();
		}
	}
}
