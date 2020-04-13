package io.agileinfra.trading.platform.e2e.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.agileinfra.trading.platform.instrument.model.InstrumentApi;
import io.agileinfra.trading.platform.instrument.model.InstrumentDto;
import io.agileinfra.trading.platform.instrument.model.PriceEventDto;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class InstrumentApiSteps implements En {

	public InstrumentApiSteps(InstrumentApi instrumentApi, ObjectMapper objectMapper) {
		DataTableType((Map<String, String> row) -> objectMapper.convertValue(row, InstrumentDto.class));

		Given("instruments", (final DataTable datatable) -> {
			final List<InstrumentDto> instruments = datatable.asList(InstrumentDto.class);
			instrumentApi.saveAll(instruments);
		});

		Then("within {}, instruments",
				(final String durationAsString, final DataTable datatable) -> {
					final Duration timeout = Duration.parse(durationAsString);
					final List<InstrumentDto> expected = datatable.asList(InstrumentDto.class);
					Awaitility.await().atMost(timeout.toMillis(), TimeUnit.MILLISECONDS).pollDelay(50, TimeUnit.MILLISECONDS)
							.pollInterval(500, TimeUnit.MILLISECONDS).until(() -> {
								final List<InstrumentDto> actual = instrumentApi.findAllInstruments();
								// log.info("expected => {}", expected);
								// log.info("actual   => {}", actual);
									return expected.equals(actual);
								});
				});

		Then("within {}, price events",
				(final String durationAsString, final DataTable datatable) -> {
					final Duration timeout = Duration.parse(durationAsString);
					final List<PriceEventDto> expected = datatable.asList(PriceEventDto.class);
					Awaitility.await().atMost(timeout.toMillis(), TimeUnit.MILLISECONDS).pollDelay(50, TimeUnit.MILLISECONDS)
							.pollInterval(500, TimeUnit.MILLISECONDS).until(() -> {
								final List<PriceEventDto> actual = instrumentApi.findAllPriceEvents();
								return expected.equals(actual);
							});
				});

	}
}
