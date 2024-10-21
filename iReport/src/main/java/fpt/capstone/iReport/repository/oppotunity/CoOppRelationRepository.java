package fpt.capstone.iReport.repository.oppotunity;

import fpt.capstone.iReport.model.opportunities.CoOppRelation;
import fpt.capstone.iReport.model.opportunities.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CoOppRelationRepository extends JpaRepository<CoOppRelation,Long>, JpaSpecificationExecutor<Opportunity> {
}
