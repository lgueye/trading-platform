package io.agileinfra.trading.platform.messaging.consumer.booking;

import io.agileinfra.trading.plateform.booking.model.BookingEventDto;

public interface BookingEventReactor {
	void process(BookingEventDto message);
}
