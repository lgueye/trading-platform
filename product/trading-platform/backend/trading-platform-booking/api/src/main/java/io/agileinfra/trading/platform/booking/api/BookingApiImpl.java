package io.agileinfra.trading.platform.booking.api;

import io.agileinfra.trading.plateform.booking.model.BookingApi;
import io.agileinfra.trading.plateform.booking.model.BookingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class BookingApiImpl implements BookingApi {
	private final String apiUrl;
	private final RestTemplate restTemplate;

	@Override
	public List<BookingDto> findAllBookings() {
		final HttpHeaders headers = new HttpHeaders();
		final HttpEntity<Void> request = new HttpEntity<>(headers);
		final URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl).path("/api/v1/bookings").build().encode().toUri();
		return restTemplate.exchange(uri, HttpMethod.GET, request, new ParameterizedTypeReference<List<BookingDto>>() {
		}).getBody();
	}

}
