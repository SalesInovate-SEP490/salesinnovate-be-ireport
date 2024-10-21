package fpt.capstone.iReport.repository;

import fpt.capstone.iReport.dto.ProcessingQuotePriceBook;
import fpt.capstone.iReport.model.ProcessingOrderPriceBook;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProcessingQuotePriceBookRepository extends JpaRepository<ProcessingQuotePriceBook,Long> {
    @Query("select p from ProcessingQuotePriceBook p where p.quoteId = :quoteId")
    ProcessingQuotePriceBook GetProcessingQuotePriceBookByOrderId(Long quoteId);

}
