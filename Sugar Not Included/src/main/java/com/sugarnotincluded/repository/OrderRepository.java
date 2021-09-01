package com.sugarnotincluded.repository;

import com.sugarnotincluded.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
