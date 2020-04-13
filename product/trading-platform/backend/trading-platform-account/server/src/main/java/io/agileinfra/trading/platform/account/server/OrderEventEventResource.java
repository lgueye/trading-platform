package io.agileinfra.trading.platform.account.server;

import io.agileinfra.trading.platform.account.model.OrderEventApi;
import io.agileinfra.trading.platform.account.model.OrderEventDto;
import io.agileinfra.trading.platform.account.persistence.OrderEventPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderEventEventResource implements OrderEventApi {
	private final OrderEventPersistenceService service;

	@GetMapping("events")
	@Override
	public List<OrderEventDto> findAllEvents() {
		return service.findAll();
	}

	@GetMapping("{id}")
	@Override
	public OrderEventDto findOrderById(@PathVariable("id") String id) {
		return service.findById(id);
	}

	@PostMapping("events")
	@Override
	public void saveAll(@RequestBody List<OrderEventDto> events) {
		service.saveAll(events);
	}
}
