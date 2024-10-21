package fpt.capstone.iReport.dto.response;

import fpt.capstone.iReport.model.AddressInformation;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Builder
public class QuoteResponse {
    private Long id;
    private String quoteNumber;
    private String quoteName;
    private Date expirationDate;
    private Long statusQuoteOpportunityId;
    private String description;
    private String accountName;
    private Double Total;
    private String opportunityName;
}