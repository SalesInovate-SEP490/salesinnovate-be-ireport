package fpt.capstone.iReport.repository;

import fpt.capstone.iReport.model.OrderContractProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderContractProductRepository extends JpaRepository<OrderContractProduct,Long> {
    List<OrderContractProduct> findByOrderId(Long orderId);
    List<OrderContractProduct> findAllByIsDeleteAndOrderId(int isDelete, Long orderId);
}
