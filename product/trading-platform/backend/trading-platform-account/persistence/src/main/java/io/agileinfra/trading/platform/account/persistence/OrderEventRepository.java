package io.agileinfra.trading.platform.account.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEventRepository extends JpaRepository<OrderEvent, String> {
}
