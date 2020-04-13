package io.agileinfra.trading.platform.account.persistence;

import io.agileinfra.trading.platform.account.model.OrderEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderEventPersistenceService {
	private final OrderEventRepository repository;

	public OrderEventDto findById(final String orderId) {
		return repository
				.findAll(Example.of(OrderEvent.builder().orderId(orderId).build()))
				.stream()
				.max(Comparator.comparing(OrderEvent::getTimestamp))
				.map(entity -> OrderEventDto.builder().orderId(orderId).instrument(entity.getInstrument()).account(entity.getAccount())
						.status(entity.getStatus()).quantity(entity.getQuantity()).build()).get();
	}

	public void save(OrderEventDto event) {
		repository.save(OrderEvent.builder() //
				.id(UUID.randomUUID().toString()) //
				.orderId(event.getOrderId()) //
				.account(event.getAccount()) //
				.instrument(event.getInstrument()) //
				.quantity(event.getQuantity()) //
				.status(event.getStatus()) //
				.timestamp(event.getTimestamp()) //
				.build());
	}

	public List<OrderEventDto> findAll() {
		return repository.findAll().stream() //
				.map(event -> OrderEventDto.builder() //
						.orderId(event.getOrderId()) //
						.account(event.getAccount()) //
						.instrument(event.getInstrument()) //
						.quantity(event.getQuantity()) //
						.status(event.getStatus()) //
						.timestamp(event.getTimestamp()).build()) //
				.collect(Collectors.toList());
	}

	public void saveAll(List<OrderEventDto> events) {
		repository.saveAll(events.stream().map(event -> OrderEvent.builder() //
				.id(UUID.randomUUID().toString()) //
				.orderId(event.getOrderId()) //
				.account(event.getAccount()) //
				.instrument(event.getInstrument()) //
				.quantity(event.getQuantity()) //
				.status(event.getStatus()) //
				.timestamp(event.getTimestamp()) //
				.build()).collect(Collectors.toList()));
	}
}
