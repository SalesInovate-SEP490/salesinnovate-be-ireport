package fpt.capstone.iReport.repository;

import fpt.capstone.iReport.model.ProcessingOrderPriceBook;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcessingOrderPriceBookRepository extends JpaRepository<ProcessingOrderPriceBook, Long> {
    @Transactional
    @Query("select p from ProcessingOrderPriceBook p where p.orderId = :orderId")
    ProcessingOrderPriceBook GetProcessingOrderPriceBookByOrderId(Long orderId);

}
