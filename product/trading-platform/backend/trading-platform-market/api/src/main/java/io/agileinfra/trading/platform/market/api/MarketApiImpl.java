package io.agileinfra.trading.platform.market.api;

import io.agileinfra.trading.platform.market.model.MarketApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Slf4j
public class MarketApiImpl implements MarketApi {
	private final String apiUrl;
	private final RestTemplate restTemplate;
}
