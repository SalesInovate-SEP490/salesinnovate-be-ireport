package fpt.capstone.iReport.repository;

import fpt.capstone.iReport.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
