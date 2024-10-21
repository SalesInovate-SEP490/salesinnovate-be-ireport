package fpt.capstone.iReport.repository;

import fpt.capstone.iReport.model.PriceBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceBookRepository extends JpaRepository<PriceBook, Long> {
}
