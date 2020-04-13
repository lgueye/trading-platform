package io.agileinfra.trading.platform.booking.persistence;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.agileinfra.trading.plateform.booking.model.BookingDto;
import io.agileinfra.trading.plateform.booking.model.BookingEventDto;
import io.agileinfra.trading.platform.account.model.OrderEventApi;
import io.agileinfra.trading.platform.account.model.OrderEventDto;
import io.agileinfra.trading.platform.instrument.model.InstrumentApi;
import io.agileinfra.trading.platform.instrument.model.InstrumentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BookingPersistenceService {
	private final BookingRepository repository;
	private final OrderEventApi orderEventApi;
	private final InstrumentApi instrumentApi;

	public void save(final BookingEventDto dto) {
		final OrderEventDto order = orderEventApi.findOrderById(dto.getOrder());
		final InstrumentDto instrument = instrumentApi.findInstrumentsByIds(Sets.newHashSet(order.getInstrument())).iterator().next();
		final Booking owner = Booking.builder() //
				.id(UUID.randomUUID().toString()) //
				.orderId(order.getOrderId()) //
				.account(order.getAccount()) //
				.instrument(order.getInstrument()) //
				.price(instrument.getPrice()) //
				.quantity(order.getQuantity()) //
				.timestamp(dto.getTimestamp()).build();
		final Booking counterpart = owner.toBuilder() //
				.id(UUID.randomUUID().toString()) //
				.account(dto.getCounterpart()) //
				.build();
		repository.saveAll(Lists.newArrayList(owner, counterpart));
	}

	public List<BookingDto> findByOrder(final String orderId) {
		return repository.findAll(Example.of(Booking.builder().orderId(orderId).build())).stream().map(entity -> BookingDto.builder() //
				.orderId(orderId) //
				.account(entity.getAccount()) //
				.instrument(entity.getInstrument()) //
				.quantity(entity.getQuantity()) //
				.timestamp(entity.getTimestamp()).build()) //
				.collect(Collectors.toList());
	}

	public List<BookingDto> findAll() {
		return repository.findAll().stream() //
				.map(entity -> BookingDto.builder() //
						.orderId(entity.getOrderId()) //
						.account(entity.getAccount()) //
						.instrument(entity.getInstrument()) //
						.quantity(entity.getQuantity()) //
						.timestamp(entity.getTimestamp()).build()) //
				.collect(Collectors.toList());
	}
}
