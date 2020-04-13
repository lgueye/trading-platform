package io.agileinfra.trading.platform.iam.api;

import io.agileinfra.trading.platform.iam.configuration.client.SecurityClientHelper;
import io.agileinfra.trading.platform.iam.model.AuthenticatedUserDto;
import io.agileinfra.trading.platform.iam.model.IamApi;
import io.agileinfra.trading.platform.iam.model.SigninRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
public class IamApiImpl implements IamApi {
	private final String apiUrl;
	private final RestTemplate restTemplate;
	private final SecurityClientHelper helper;

	@Override
	public AuthenticatedUserDto signin(SigninRequestDto request) {
		final HttpHeaders headers = new HttpHeaders();
		final HttpEntity<SigninRequestDto> entity = new HttpEntity<>(request, headers);
		final URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl).path("/api/v1/iam/signin").build().encode().toUri();
		final AuthenticatedUserDto authenticatedUser = restTemplate.exchange(uri, HttpMethod.POST, entity, AuthenticatedUserDto.class).getBody();
		helper.updateSecurityContext(authenticatedUser);
		return authenticatedUser;
	}

	public void signout() {
		helper.clearSecurityContext();
	}
}
