package io.agileinfra.trading.platform.account.api;

import io.agileinfra.trading.platform.account.model.AccountApi;
import io.agileinfra.trading.platform.account.model.AccountDto;
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
public class AccountApiImpl implements AccountApi {
	private final String apiUrl;
	private final RestTemplate restTemplate;

	@Override
	public void saveAll(List<AccountDto> accounts) {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(APPLICATION_JSON);
		final HttpEntity<List<AccountDto>> request = new HttpEntity<>(accounts, headers);
		final URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl).path("/api/v1/accounts").build().encode().toUri();
		restTemplate.exchange(uri, HttpMethod.POST, request, Void.class);
	}

	@Override
	public List<AccountDto> findAllAccounts() {
		final HttpHeaders headers = new HttpHeaders();
		final HttpEntity<Void> request = new HttpEntity<>(headers);
		final URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl).path("/api/v1/accounts").build().encode().toUri();
		return restTemplate.exchange(uri, HttpMethod.GET, request, new ParameterizedTypeReference<List<AccountDto>>() {
		}).getBody();
	}

}
