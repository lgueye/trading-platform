package io.agileinfra.trading.platform.account.model;

import java.util.List;

public interface OrderEventApi {
	List<OrderEventDto> findAllEvents();

	OrderEventDto findOrderById(String order);

	void saveAll(List<OrderEventDto> events);
}
