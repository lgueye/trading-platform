package io.agileinfra.trading.platform.account.persistence;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.agileinfra.trading.platform.account.model.AccountDto;
import io.agileinfra.trading.platform.account.model.AccountOrderDto;
import io.agileinfra.trading.platform.account.model.AssetDto;
import io.agileinfra.trading.platform.account.model.OrderStatus;
import io.agileinfra.trading.platform.instrument.model.InstrumentApi;
import io.agileinfra.trading.platform.instrument.model.InstrumentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AccountPersistenceService {
	private final AccountRepository accountRepository;
	private final OrderEventRepository orderEventRepository;
	private final InstrumentApi instrumentApi;

	public void save(AccountDto dto) {
		accountRepository.save(Account.builder().id(dto.getId()).owner(dto.getOwner()).cash(dto.getCash()).build());
	}

	public AccountDto findById(String id) {
		final Account account = accountRepository.findById(id).get();
		final List<OrderEvent> events = orderEventRepository.findAll(Example.of(OrderEvent.builder().account(account.getId()).build()));

		// We are only interested in latest order status
		final Map<String, Optional<OrderEvent>> latestEventByOrder = events.stream().collect(
				Collectors.groupingBy(OrderEvent::getOrderId, Collectors.maxBy(Comparator.comparing(OrderEvent::getTimestamp))));

		// Fetch prices (not all instruments, only the ones involved in order events)
		final Set<String> instrumentIds = events.stream().map(OrderEvent::getInstrument).collect(Collectors.toSet());
		final Map<String, InstrumentDto> instrumentsById = instrumentApi.findInstrumentsByIds(instrumentIds).stream()
				.collect(Collectors.toMap(InstrumentDto::getId, Function.identity()));

		// Compute assets: filter events (retain booked), then sum quantities when same instrument
		final List<AssetDto> assets = Lists.newArrayList(latestEventByOrder.values().stream() //
				.filter(optionalEvent -> optionalEvent.get().getStatus() == OrderStatus.booked).map(Optional::get) //
				.collect(Collectors.groupingBy(OrderEvent::getInstrument)) // group by instrument
				.entrySet().stream() //
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> { // map and reduce booked events grouped by instrument to sum the quantities
							final List<OrderEvent> value = entry.getValue();
							OrderEvent orderEvent = entry.getValue().get(0);
							// initialize accumulator with 0 quantity and empty order ids
						final AssetDto accumulator = AssetDto.builder() //
								.instrument(orderEvent.getInstrument()) //
								.quantity(0) //
								.price(instrumentsById.get(orderEvent.getInstrument()).getPrice()) //
								.orders(Sets.newHashSet()) //
								.build();
						value.forEach(evt -> {
							accumulator.setQuantity(accumulator.getQuantity() + evt.getQuantity());
							accumulator.getOrders().add(evt.getOrderId());
						});
						return accumulator;
					})).values());

		// Compute orders: filter events (exclude booked), each order is unique so no need to do any grouping
		final List<AccountOrderDto> orders = latestEventByOrder.values().stream() //
				.map(Optional::get) //
				.filter(evt -> evt.getStatus() != OrderStatus.booked) //
				.map(orderEvent -> AccountOrderDto.builder() //
						.orderId(orderEvent.getOrderId()) //
						.instrument(orderEvent.getInstrument()) //
						.quantity(orderEvent.getQuantity()) //
						.price(instrumentsById.get(orderEvent.getInstrument()).getPrice()) //
						.status(orderEvent.getStatus()) //
						.timestamp(orderEvent.getTimestamp()) //
						.build()) //
				.collect(Collectors.toList());

		return AccountDto.builder() //
				.id(account.getId()) //
				.owner(account.getOwner()).cash(account.getCash()) //
				.assets(assets) //
				.orders(orders) //
				.build();
	}

	public List<AccountDto> findAllAccounts() {
		return accountRepository.findAll().stream() //
				.map(account -> findById(account.getId())).collect(Collectors.toList());
	}

	public void saveAll(List<AccountDto> accounts) {
		accountRepository.saveAll(accounts.stream().map(dto -> Account.builder().id(dto.getId()).owner(dto.getOwner()).cash(dto.getCash()).build())
				.collect(Collectors.toList()));
	}
}
