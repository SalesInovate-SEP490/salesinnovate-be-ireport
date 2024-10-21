package fpt.capstone.iReport.dto.response;

import fpt.capstone.iReport.model.AddressInformation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
public class ContractResponse {
    private Long contractId;
    private Long userId;
    private LocalDateTime contractStartDate;
    private Long contractTerm;
    private LocalDateTime ownerExpirationNotice;
    private String specialTerms;
    private String description;
    private Long accountId;
    private Long priceBookId;
    private AddressInformation billingAddressId;
    private AddressInformation shippingAddressId;
    private Long contactId;
    private String customerSignedTitle;
    private LocalDateTime customerSignedDate;
    private Long companyId;
    private LocalDateTime companySignedDate;
    private Integer isDelete;
    private LocalDateTime deleteDate;
}
