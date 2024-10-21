package fpt.capstone.iReport.repository.leads;

import fpt.capstone.iReport.model.leads.Leads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadsRepository extends JpaRepository<Leads,Long>, JpaSpecificationExecutor<Leads> {

}
