package io.agileinfra.trading.platform.e2e.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.agileinfra.trading.plateform.booking.model.BookingEventDto;
import io.agileinfra.trading.platform.account.model.InitiationEventDto;
import io.agileinfra.trading.platform.account.model.MatchingEventDto;
import io.agileinfra.trading.platform.e2e.TradingPlatformE2EConfiguration;
import io.agileinfra.trading.platform.instrument.model.PriceEventDto;
import io.agileinfra.trading.platform.messaging.producer.booking.BookingEventProducer;
import io.agileinfra.trading.platform.messaging.producer.order.OrderEventProducer;
import io.agileinfra.trading.platform.messaging.producer.price.PriceEventProducer;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Map;

@ContextConfiguration(classes = TradingPlatformE2EConfiguration.class)
@Slf4j
public class MessagingProducersSteps implements En {

	public MessagingProducersSteps(OrderEventProducer orderEventProducer, PriceEventProducer priceEventProducer,
			BookingEventProducer bookingEventProducer, ObjectMapper objectMapper) {

		DataTableType((Map<String, String> row) -> objectMapper.convertValue(row, InitiationEventDto.class));
		DataTableType((Map<String, String> row) -> objectMapper.convertValue(row, PriceEventDto.class));
		DataTableType((Map<String, String> row) -> objectMapper.convertValue(row, MatchingEventDto.class));
		DataTableType((Map<String, String> row) -> objectMapper.convertValue(row, BookingEventDto.class));

		When("price event", (final DataTable datatable) -> {
			final List<PriceEventDto> events = datatable.asList(PriceEventDto.class);
			events.forEach(priceEventProducer::send);
		});
		When("initiation event", (final DataTable datatable) -> {
			final List<InitiationEventDto> events = datatable.asList(InitiationEventDto.class);
			events.forEach(orderEventProducer::send);
		});
		When("matching event", (final DataTable datatable) -> {
			final List<MatchingEventDto> events = datatable.asList(MatchingEventDto.class);
			events.forEach(orderEventProducer::send);
		});
		When("booking event", (final DataTable datatable) -> {
			final List<BookingEventDto> events = datatable.asList(BookingEventDto.class);
			events.forEach(bookingEventProducer::send);
		});

	}
}
