package fpt.capstone.iReport.repository.leads;

import fpt.capstone.iReport.model.leads.LogCallLeads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogCallLeadRepository extends JpaRepository<LogCallLeads,Long> {
    List<LogCallLeads> findByLead_LeadId(Long leadId);
}
