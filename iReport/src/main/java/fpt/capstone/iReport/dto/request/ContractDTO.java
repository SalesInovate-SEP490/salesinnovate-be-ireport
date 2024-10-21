package fpt.capstone.iReport.dto.request;

import fpt.capstone.iReport.model.AddressInformation;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {
    private Long contractId;
    private Long userId;
    private Date contractStartDate;
    private Long contractTerm;
    private Date ownerExpirationNotice;
    private String specialTerms;
    private String description;
    private Long accountId;
    private Long priceBookId;
    private AddressInformationDTO billingAddressId;
    private AddressInformationDTO shippingAddressId;
    private Long contactId;
    private String customerSignedTitle;
    private Date customerSignedDate;
    private Long companyId;
    private Date companySignedDate;
    private Long contractStatus;
    private String contractNumber;
}
