package fpt.capstone.iReport.dto.response;

import fpt.capstone.iReport.dto.request.AccountDTO;
import fpt.capstone.iReport.dto.request.LeadDTO;
import fpt.capstone.iReport.dto.request.OpportunityDTO;
import fpt.capstone.iReport.model.accounts.Account;
import fpt.capstone.iReport.model.leads.Leads;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportResponse {
    private Map<String, String> accountField;
    private List<AccountDTO> accountReprot;
    private Map<String, String> leadField;
    private List<LeadDTO> leadsReport;
    private Map<String, String> opportunityField;
    private List<OpportunityDTO> opportunityReport;
    private boolean toggleChart;
    private String typeChart;
    private String reportType;
}