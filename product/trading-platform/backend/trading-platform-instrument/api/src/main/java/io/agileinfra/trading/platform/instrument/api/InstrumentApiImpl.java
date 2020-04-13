package io.agileinfra.trading.platform.instrument.api;

import com.google.common.base.Joiner;
import io.agileinfra.trading.platform.instrument.model.InstrumentApi;
import io.agileinfra.trading.platform.instrument.model.InstrumentDto;
import io.agileinfra.trading.platform.instrument.model.PriceEventDto;
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
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor
@Slf4j
public class InstrumentApiImpl implements InstrumentApi {
	private final String apiUrl;
	private final RestTemplate restTemplate;

	@Override
	public void saveAll(List<InstrumentDto> instruments) {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(APPLICATION_JSON);
		final HttpEntity<List<InstrumentDto>> request = new HttpEntity<>(instruments, headers);
		final URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl).path("/api/v1/instruments").build().encode().toUri();
		restTemplate.exchange(uri, HttpMethod.POST, request, Void.class);
	}

	@Override
	public List<InstrumentDto> findAllInstruments() {
		final HttpHeaders headers = new HttpHeaders();
		final HttpEntity<Void> request = new HttpEntity<>(headers);
		final URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl).path("/api/v1/instruments").build().encode().toUri();
		return restTemplate.exchange(uri, HttpMethod.GET, request, new ParameterizedTypeReference<List<InstrumentDto>>() {
		}).getBody();
	}

	@Override
	public List<PriceEventDto> findAllPriceEvents() {
		final HttpHeaders headers = new HttpHeaders();
		final HttpEntity<Void> request = new HttpEntity<>(headers);
		final URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl).path("/api/v1/instruments/events").build().encode().toUri();
		return restTemplate.exchange(uri, HttpMethod.GET, request, new ParameterizedTypeReference<List<PriceEventDto>>() {
		}).getBody();
	}

	@Override
	public List<InstrumentDto> findInstrumentsByIds(Set<String> instrmentIds) {
		final HttpHeaders headers = new HttpHeaders();
		final HttpEntity<Void> request = new HttpEntity<>(headers);
		final URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl).path("/api/v1/instruments/search")
				.queryParam("ids", Joiner.on(",").join(instrmentIds)).build().encode().toUri();
		return restTemplate.exchange(uri, HttpMethod.GET, request, new ParameterizedTypeReference<List<InstrumentDto>>() {
		}).getBody();
	}
}
