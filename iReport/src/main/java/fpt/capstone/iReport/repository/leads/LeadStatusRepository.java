package fpt.capstone.iReport.repository.leads;

import fpt.capstone.iReport.model.leads.LeadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadStatusRepository extends JpaRepository<LeadStatus,Long> {
}
