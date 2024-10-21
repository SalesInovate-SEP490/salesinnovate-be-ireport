package fpt.capstone.iReport.dto.request;

import fpt.capstone.iReport.model.opportunities.Forecast;
import fpt.capstone.iReport.model.opportunities.Stage;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OpportunityDTO {
    private Long opportunityId;
    private String opportunityName;
    private Long accountId;
    private String userId;
    private Float probability;
    private String nextStep;
    private BigDecimal amount;
    private LocalDateTime closeDate;
    private Long primaryCampaignSourceId;
    private String description;
    private String forecast;
    private String stage;
    private String type;
    private String lastModifiedBy ;
    private String leadSource ;
    private LocalDateTime createDate ;
    private LocalDateTime editDate ;
    private String createBy ;
    private Boolean isDeleted ;

}
