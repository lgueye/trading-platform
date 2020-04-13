package io.agileinfra.trading.platform.account.api;

import io.agileinfra.trading.platform.account.model.OrderEventApi;
import io.agileinfra.trading.platform.account.model.OrderEventDto;
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

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor
@Slf4j
public class OrderEventApiImpl implements OrderEventApi {
	private final String apiUrl;
	private final RestTemplate restTemplate;

	@Override
	public List<OrderEventDto> findAllEvents() {
		final HttpHeaders headers = new HttpHeaders();
		final HttpEntity<Void> request = new HttpEntity<>(headers);
		final URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl).path("/api/v1/orders/events").build().encode().toUri();
		return restTemplate.exchange(uri, HttpMethod.GET, request, new ParameterizedTypeReference<List<OrderEventDto>>() {
		}).getBody();
	}

	@Override
	public OrderEventDto findOrderById(String order) {
		final HttpHeaders headers = new HttpHeaders();
		final HttpEntity<Void> request = new HttpEntity<>(headers);
		final URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl).path("/api/v1/orders/{id}").buildAndExpand(order).encode().toUri();
		return restTemplate.exchange(uri, HttpMethod.GET, request, OrderEventDto.class).getBody();
	}

	@Override
	public void saveAll(List<OrderEventDto> events) {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(APPLICATION_JSON);
		final HttpEntity<List<OrderEventDto>> request = new HttpEntity<>(events, headers);
		final URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl).path("/api/v1/orders/events").build().encode().toUri();
		restTemplate.exchange(uri, HttpMethod.POST, request, Void.class);
	}
}
