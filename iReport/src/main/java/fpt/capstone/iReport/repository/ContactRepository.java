package fpt.capstone.iReport.repository;

import fpt.capstone.iReport.model.Contact;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ContactRepository extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {
    @Modifying
    @Transactional
    @Query("UPDATE Contact c SET c.accountId = NULL WHERE c.accountId = :accountId")
    void setAccountIdToNull(Long accountId);
}
