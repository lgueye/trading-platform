package io.agileinfra.trading.platform.messaging.consumer.position;

import io.agileinfra.trading.platform.account.model.PositionEventDto;

public interface PositionEventReactor {
	void process(PositionEventDto message);
}
