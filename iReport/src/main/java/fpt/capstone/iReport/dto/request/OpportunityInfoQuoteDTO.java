package fpt.capstone.iReport.dto.request;

import fpt.capstone.iReport.model.AddressInformation;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
public class OpportunityInfoQuoteDTO {
    private Long opportunityId;
    private String opportunityName;
    private Long accountId;
    private String accountName;
    private String userId;
    private AddressInformation billingAddress;
    private AddressInformation shippingAddress;

}
