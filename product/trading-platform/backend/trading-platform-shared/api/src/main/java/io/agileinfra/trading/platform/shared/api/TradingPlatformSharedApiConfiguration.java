package io.agileinfra.trading.platform.shared.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.agileinfra.trading.platform.shared.configuration.serialization.TradingPlatformSharedSerializationConfiguration;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Configuration
@Import(TradingPlatformSharedSerializationConfiguration.class)
public class TradingPlatformSharedApiConfiguration {
	@Bean
	public CloseableHttpClient closeableHttpClient() throws NoSuchAlgorithmException, KeyManagementException {
		final SSLContext sslContext = SSLContext.getInstance("SSL");
		TrustManager[] trustManagers = new TrustManager[]{new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}

		}};
		sslContext.init(null, trustManagers, new SecureRandom());
		return HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).setSslcontext(sslContext).build();
	}

	@Bean
	public RestTemplate restTemplate(final CloseableHttpClient httpClient, final ObjectMapper objectMapper) {
		final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		final RestTemplate restTemplate = new RestTemplate(requestFactory);
		restTemplate.getMessageConverters().forEach(converter -> {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				((MappingJackson2HttpMessageConverter) converter).setObjectMapper(objectMapper);
			}
		});
		return restTemplate;
	}
}
