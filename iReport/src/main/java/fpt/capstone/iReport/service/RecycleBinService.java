package fpt.capstone.iReport.service;

import fpt.capstone.iReport.dto.response.PageResponse;

import java.util.List;

public interface RecycleBinService {

    PageResponse<?> getAllDeleteLeads(int pageNo, int pageSize);
    PageResponse<?> getAllDeleteAccount(int pageNo, int pageSize);
    PageResponse<?> getAllDeleteOpportunity(int pageNo, int pageSize);
    PageResponse<?> getAllDeleteContact(int pageNo, int pageSize);
    PageResponse<?> getAllDeleteEmailTemplate(int pageNo, int pageSize);
    boolean DeleteLeads(Long leadId);
    boolean DeleteAccount(Long accountId);
    boolean DeleteOpportunity(Long opportunityId);
    boolean DeleteEmailTemplate(Long emailTempateId);
    boolean DeleteContact(Long contactId);
    boolean DeleteListLeads(List<Long> listLeadId);
    boolean DeletelistAccount(List<Long> listAccountId);
    boolean DeletelistOpportunity(List<Long> listOpportunityId);
    boolean DeletelistContactt(List<Long> listContactId);
    boolean DeletelistEmailTemplate(List<Long> listEmailTempateId);
    boolean RestoreLeads(Long leadId);
    boolean RestoreAccount(Long accountId);
    boolean RestoreOpportunity(Long opportunityId);
    boolean RestoreEmailTemplate(Long emailTempateId);
    boolean RestoreContact(Long contactId);
    boolean RestoreListLeads(List<Long> listLeadId);
    boolean RestoreListAccount(List<Long> listAccountId);
    boolean RestoreListOpportunity(List<Long> listOpportunityId);
    boolean RestoreListEmailTemplate(List<Long> listEmailTempateId);
    boolean RestoreListContact(List<Long> listContactId);
}
