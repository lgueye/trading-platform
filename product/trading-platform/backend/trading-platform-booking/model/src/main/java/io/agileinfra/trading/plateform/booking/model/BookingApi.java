package io.agileinfra.trading.plateform.booking.model;

import java.util.List;

public interface BookingApi {
	List<BookingDto> findAllBookings();
}
