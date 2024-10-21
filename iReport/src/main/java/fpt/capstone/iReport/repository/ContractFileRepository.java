package fpt.capstone.iReport.repository;

import fpt.capstone.iReport.model.ContractFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractFileRepository extends JpaRepository<ContractFile,Long> {
    ContractFile findByContractId(Long contractId);
    ContractFile findByFileId(String fileId);
}
