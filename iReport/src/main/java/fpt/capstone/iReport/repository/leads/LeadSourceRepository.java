package fpt.capstone.iReport.repository.leads;

import fpt.capstone.iReport.model.leads.LeadSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadSourceRepository extends JpaRepository<LeadSource,Long> {
}
