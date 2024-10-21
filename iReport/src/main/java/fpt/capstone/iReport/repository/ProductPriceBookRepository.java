package fpt.capstone.iReport.repository;

import fpt.capstone.iReport.model.ProductPriceBook;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductPriceBookRepository extends JpaRepository<ProductPriceBook, Long> {
    @Transactional
    @Query("select p from ProductPriceBook p where p.priceBookId = :priceBookId")
    List<ProductPriceBook> getProductPriceBookByPriceBookId(Long priceBookId);
}
