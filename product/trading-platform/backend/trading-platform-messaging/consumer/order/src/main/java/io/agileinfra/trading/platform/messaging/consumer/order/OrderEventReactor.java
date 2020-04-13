package io.agileinfra.trading.platform.messaging.consumer.order;

import io.agileinfra.trading.platform.account.model.OrderEventDto;

public interface OrderEventReactor {
	void process(OrderEventDto message);
}
