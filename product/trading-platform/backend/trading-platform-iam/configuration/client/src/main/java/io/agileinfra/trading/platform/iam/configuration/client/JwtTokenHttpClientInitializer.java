package io.agileinfra.trading.platform.iam.configuration.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.client.support.InterceptingHttpAccessor;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Registers correlation interceptors to all http client (ie RestTemplate)
 *
 */
@RequiredArgsConstructor
@Slf4j
public class JwtTokenHttpClientInitializer implements InitializingBean {

	private final JwtTokenHttpInterceptor jwtTokenHttpInterceptor;
	private final List<InterceptingHttpAccessor> httpClients;

	@Override
	public void afterPropertiesSet() {
		if (CollectionUtils.isEmpty(httpClients)) {
			log.warn("No RestTemplate found => not adding JwtTokenHttpInterceptor to any RestTemplate");
			return;
		}
		for (InterceptingHttpAccessor client : httpClients) {
			client.getInterceptors().add(jwtTokenHttpInterceptor);
		}
	}
}
