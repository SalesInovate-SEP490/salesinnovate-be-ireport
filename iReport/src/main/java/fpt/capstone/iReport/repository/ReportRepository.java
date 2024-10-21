package fpt.capstone.iReport.repository;

import fpt.capstone.iReport.model.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findListByUserId(String userId);
    Report findByUserId(String userId);
}
