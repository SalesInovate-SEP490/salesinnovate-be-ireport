package fpt.capstone.iReport.repository;

import fpt.capstone.iReport.model.Contract;
import fpt.capstone.iReport.model.OrderContract;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderContractRepository extends JpaRepository<OrderContract, Long> , JpaSpecificationExecutor<OrderContract> {
    @Transactional
    @Query("select ct from OrderContract ct where ct.orderId = :orderId")
    OrderContract findByContractNumber(Long orderId);
    Page<OrderContract> findAllByIsDelete(int isDelete, Pageable pageable);
    List<OrderContract> findAllByIsDeleteAndContractNumber(int isDelete, String contractNumber);
}
