package fpt.capstone.iReport.dto.request;

import fpt.capstone.iReport.model.AddressInformation;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Data
@Builder
@Getter
@Setter
public class QuoteOpportunityDTO {
    private Long id;
    private String quoteName;
    private String opportunityName;
    private Date expirationDate;
    private Long statusQuoteOpportunityId;
    private String description;
    private Long opportunityId;
    private String accountName;
    private Double discount;
    private Double tax;
    private Double shippingHandling;
    private Double grandTotal;
    private Long contactId;
    private String email;
    private String phone;
    private String fax;
    private String billingName;
    private String shippingName;
    private AddressInformationDTO billingAddressInformation;
    private AddressInformationDTO shippingInformationId;
}
