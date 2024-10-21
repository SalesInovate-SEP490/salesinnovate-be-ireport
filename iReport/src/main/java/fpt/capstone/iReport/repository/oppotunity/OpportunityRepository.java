package fpt.capstone.iReport.repository.oppotunity;

import fpt.capstone.iReport.model.opportunities.Opportunity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> , JpaSpecificationExecutor<Opportunity> {
    @Modifying
    @Transactional
    @Query("UPDATE Opportunity o SET o.leadSourceId = NULL WHERE o.leadSourceId = :leadSourceId")
    void updateLeadSourceIdToNull(Long leadSourceId);

    @Modifying
    @Transactional
    @Query("UPDATE Opportunity o SET o.accountId = null WHERE o.accountId = :accountId")
    void updateAccountIdToNull(Long accountId);
}
