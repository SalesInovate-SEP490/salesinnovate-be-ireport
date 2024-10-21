package fpt.capstone.iReport.repository;

import fpt.capstone.iReport.model.AddressInformation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AddressInformationRepository extends JpaRepository<AddressInformation, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.billingInformation = NULL WHERE a.billingInformation.addressInformationId = :addressInformationId")
    void setBillingInformationToNull(Long addressInformationId);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.shippingInformation = NULL WHERE a.shippingInformation.addressInformationId = :addressInformationId")
    void setShippingInformationToNull(Long addressInformationId);

    @Modifying
    @Transactional
    @Query("UPDATE Contact c SET c.addressInformation = NULL " +
            "WHERE c.addressInformation.addressInformationId = :addressInformationId")
    void setAddressInformationToNullInContact(Long addressInformationId);

    @Modifying
    @Transactional
    @Query("UPDATE Leads l SET l.addressInformation = NULL WHERE " +
            "l.addressInformation.addressInformationId = :addressInformationId")
    void setAddressInformationToNull(Long addressInformationId);
}
