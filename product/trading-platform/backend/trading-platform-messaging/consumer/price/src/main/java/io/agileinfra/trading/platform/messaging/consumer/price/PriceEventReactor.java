package io.agileinfra.trading.platform.messaging.consumer.price;

import io.agileinfra.trading.platform.instrument.model.PriceEventDto;

public interface PriceEventReactor {
	void process(PriceEventDto message);
}
