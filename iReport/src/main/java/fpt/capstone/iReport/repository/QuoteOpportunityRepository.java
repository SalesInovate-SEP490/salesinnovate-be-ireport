//package fpt.capstone.iReport.repository;
//
//import fpt.capstone.iReport.model.OrderContract;
//import fpt.capstone.iReport.model.QuoteOpportunity;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//
//public interface QuoteOpportunityRepository extends JpaRepository<QuoteOpportunity,Long> , JpaSpecificationExecutor<QuoteOpportunity> {
//    Page<QuoteOpportunity> findAllByIsDelete(int isDelete, Pageable pageable);
//    Page<QuoteOpportunity> findByOpportunityIdAndIsDelete(Long opportunityId, int isDelete, Pageable pageable);
//}
