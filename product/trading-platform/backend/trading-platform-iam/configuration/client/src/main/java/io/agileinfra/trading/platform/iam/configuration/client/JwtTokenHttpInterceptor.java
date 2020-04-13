package io.agileinfra.trading.platform.iam.configuration.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenHttpInterceptor implements ClientHttpRequestInterceptor {
	final SecurityClientHelper helper;
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		helper.getToken().ifPresent(token -> request.getHeaders().set("Authorization", String.format("Bearer %s", token)));
		return execution.execute(request, body);
	}
}
