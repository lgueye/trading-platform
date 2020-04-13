package io.agileinfra.trading.platform.booking.server;

import io.agileinfra.trading.plateform.booking.model.BookingApi;
import io.agileinfra.trading.plateform.booking.model.BookingDto;
import io.agileinfra.trading.platform.booking.persistence.BookingPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingResource implements BookingApi {
	private final BookingPersistenceService service;
	@GetMapping
	@Override
	public List<BookingDto> findAllBookings() {
		return service.findAll();
	}
}
