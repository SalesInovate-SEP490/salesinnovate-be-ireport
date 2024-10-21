package fpt.capstone.iReport.repository.account;

import fpt.capstone.iReport.model.accounts.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
}
