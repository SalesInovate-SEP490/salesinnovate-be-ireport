package fpt.capstone.iReport.repository;

import fpt.capstone.iReport.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
}
