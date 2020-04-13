package io.agileinfra.trading.platform.e2e.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.agileinfra.trading.plateform.booking.model.BookingApi;
import io.agileinfra.trading.plateform.booking.model.BookingDto;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BookingApiSteps implements En {

	public BookingApiSteps(BookingApi bookingApi, ObjectMapper objectMapper) {
		DataTableType((Map<String, String> row) -> objectMapper.convertValue(row, BookingDto.class));

		Then("within {}, bookings",
				(final String durationAsString, final DataTable datatable) -> {
					final Duration timeout = Duration.parse(durationAsString);
					final List<BookingDto> expected = datatable.asList(BookingDto.class);
					Awaitility.await().atMost(timeout.toMillis(), TimeUnit.MILLISECONDS).pollDelay(50, TimeUnit.MILLISECONDS)
							.pollInterval(500, TimeUnit.MILLISECONDS).until(() -> {
								final List<BookingDto> actual = bookingApi.findAllBookings();
								return expected.equals(actual);
							});
				});
	}
}
