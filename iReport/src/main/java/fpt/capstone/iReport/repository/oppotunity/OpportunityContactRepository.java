package fpt.capstone.iReport.repository.oppotunity;

import fpt.capstone.iReport.model.opportunities.OpportunityContact;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OpportunityContactRepository extends JpaRepository<OpportunityContact, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM OpportunityContact oc WHERE oc.contactId = :contactId")
    void deleteByContactId(Long contactId);
}
