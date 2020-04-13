package io.agileinfra.trading.platform.iam.api;

import io.agileinfra.trading.platform.iam.configuration.client.SecurityClientHelper;
import io.agileinfra.trading.platform.iam.configuration.client.TradingPlatformSecurityClientConfiguration;
import io.agileinfra.trading.platform.shared.api.TradingPlatformSharedApiConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Configuration
@Import({TradingPlatformSharedApiConfiguration.class, TradingPlatformSecurityClientConfiguration.class})
public class IamApiConfiguration {
	@Bean
	public IamApiImpl iamApi(@Value("${iam.server.url}") final String apiUrl, final RestTemplate restTemplate, final SecurityClientHelper helper) {
		return new IamApiImpl(apiUrl, restTemplate, helper);
	}
}
