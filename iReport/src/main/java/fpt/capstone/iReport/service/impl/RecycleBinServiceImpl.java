package fpt.capstone.iReport.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.capstone.iReport.dto.Convert;
import fpt.capstone.iReport.dto.response.PageResponse;
import fpt.capstone.iReport.model.AddressInformation;
import fpt.capstone.iReport.model.Contact;
import fpt.capstone.iReport.model.EmailTemplate;
import fpt.capstone.iReport.model.accounts.Account;
import fpt.capstone.iReport.model.leads.Leads;
import fpt.capstone.iReport.model.opportunities.Opportunity;
import fpt.capstone.iReport.repository.AddressInformationRepository;
import fpt.capstone.iReport.repository.ContactRepository;
import fpt.capstone.iReport.repository.EmailTemplateRepository;
import fpt.capstone.iReport.repository.account.AccountRepository;
import fpt.capstone.iReport.repository.leads.LeadsRepository;
import fpt.capstone.iReport.repository.leads.LogCallLeadRepository;
import fpt.capstone.iReport.repository.oppotunity.OpportunityContactRepository;
import fpt.capstone.iReport.repository.oppotunity.OpportunityRepository;
import fpt.capstone.iReport.service.RecycleBinService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class RecycleBinServiceImpl implements RecycleBinService {
    private LeadsRepository leadsRepository;
    private OpportunityRepository opportunityRepository;
    private AccountRepository accountRepository;
    private ContactRepository contactRepository;
    private EmailTemplateRepository emailTemplateRepository;
    private LogCallLeadRepository logCallLeadsRepository;
    private AddressInformationRepository addressInformationRepository;
    private OpportunityContactRepository opportunityContactRepository;
    private Convert converter;


    @Override
    public PageResponse<?> getAllDeleteLeads(int pageNo, int pageSize) {
        int page = 0;
        if (pageNo > 0) {
            page = pageNo - 1;
        }

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(new Sort.Order(Sort.Direction.DESC, "createDate"));

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));

        Specification<Leads> spec = new Specification<Leads>() {
            @Override
            public Predicate toPredicate(Root<Leads> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.notEqual(root.get("isDelete"), 0);
            }
        };

        Page<Leads> leads = leadsRepository.findAll(spec, pageable);
        return converter.ConvertLeadsToPageResponse(leads, pageable);
    }

    @Override
    public PageResponse<?> getAllDeleteAccount(int pageNo, int pageSize) {
        try{
            int page = 0;
            if (pageNo > 0) {
                page = pageNo - 1;
            }

            List<Sort.Order> sorts = new ArrayList<>();
            sorts.add(new Sort.Order(Sort.Direction.DESC, "createDate"));

            Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));

            Specification<Account> spec = new Specification<Account>() {
                @Override
                public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.notEqual(root.get("isDeleted"), 0);
                }
            };

            Page<Account> accounts = accountRepository.findAll(spec,pageable);
            return converter.ConvertAccountToPageResponse(accounts, pageable);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PageResponse<?> getAllDeleteOpportunity(int pageNo, int pageSize) {
        int page = 0;
        if (pageNo > 0) {
            page = pageNo - 1;
        }

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(new Sort.Order(Sort.Direction.DESC, "createDate"));

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));

        Specification<Opportunity> spec = new Specification<Opportunity>() {
            @Override
            public Predicate toPredicate(Root<Opportunity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("isDeleted"), true);
            }
        };

        Page<Opportunity> opportunities = opportunityRepository.findAll(spec, pageable);
        return converter.ConvertOpportunityToPageResponse(opportunities, pageable);
    }

    @Override
    public PageResponse<?> getAllDeleteContact(int pageNo, int pageSize) {
        int page = 0;
        if (pageNo > 0) {
            page = pageNo - 1;
        }

        Pageable pageable = PageRequest.of(page, pageSize);

        Specification<Contact> spec = new Specification<Contact>() {
            @Override
            public Predicate toPredicate(Root<Contact> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.notEqual(root.get("isDeleted"), 0);
            }
        };

        Page<Contact> leads = contactRepository.findAll(spec, pageable);
        return converter.ConvertContactToPageResponse(leads, pageable);
    }

    @Override
    public PageResponse<?> getAllDeleteEmailTemplate(int pageNo, int pageSize) {
        try {
            int page = 0;
            if (pageNo > 0) {
                page = pageNo - 1;
            }

            Pageable pageable = PageRequest.of(page, pageSize);

            Specification<EmailTemplate> spec = new Specification<EmailTemplate>() {
                @Override
                public Predicate toPredicate(Root<EmailTemplate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.notEqual(root.get("isDeleted"), 0);
                }
            };
            Page<EmailTemplate> emailTemplates = emailTemplateRepository.findAll(spec, pageable);
            return converter.ConvertEmailTemplatePageResponse(emailTemplates, pageable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean DeleteLeads(Long leadId) {
        try {
            logCallLeadsRepository.deleteAll(logCallLeadsRepository.findByLead_LeadId(leadId));

            Optional<Leads> leads = leadsRepository.findById(leadId);


            if (leads.isPresent()) {
                Leads lead = leads.get();
                if(lead.getLeadSource() != null) {
                    opportunityRepository.updateLeadSourceIdToNull(lead.getLeadSource().getLeadSourceId());
                }
                lead.setIndustry(null);
                lead.setLeadSource(null);
                lead.setLeadStatus(null);
                lead.setAddressInformation(null);
                lead.setLeadRating(null);
                lead.setLeadSalution(null);

                leadsRepository.save(lead);

                leads.ifPresent(leadsRepository::delete);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean DeleteAccount(Long accountId) {
        try {
            opportunityRepository.updateAccountIdToNull(accountId);
            Optional<Account> accounts = accountRepository.findById(accountId);
            if (accounts.isPresent()){
                Account account = accounts.get();
                if (account.getBillingInformation() != null) {
                    addressInformationRepository.
                            setBillingInformationToNull(account.getBillingInformation()
                                    .getAddressInformationId());
                }

                if (account.getShippingInformation() != null) {
                    addressInformationRepository.
                            setShippingInformationToNull(account.getShippingInformation()
                                    .getAddressInformationId());
                }
                contactRepository.setAccountIdToNull(accountId);
                account.setAccountType(null);
                account.setIndustry(null);

                accountRepository.save(account);
                accounts.ifPresent(accountRepository::delete);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean DeleteOpportunity(Long opportunityId) {
        try {
            Optional<Opportunity> opportunities = opportunityRepository.findById(opportunityId);
            if(opportunities.isPresent()) {
                Opportunity opportunity = opportunities.get();

                opportunity.setLeadSourceId(null);
                opportunity.setForecast(null);
                opportunity.setStage(null);
                opportunity.setType(null);
                opportunity.setPrimaryCampaignSourceId(null);

                opportunityRepository.save(opportunity);
                opportunities.ifPresent(opportunityRepository::delete);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean DeleteEmailTemplate(Long emailTempateId) {
        try {
            Optional<EmailTemplate> emailTemplates = emailTemplateRepository.findById(emailTempateId);
            if(emailTemplates.isPresent()) {
                EmailTemplate emailTemplate = emailTemplates.get();

                emailTemplate.setUserId(null);

                emailTemplateRepository.save(emailTemplate);
                emailTemplates.ifPresent(emailTemplateRepository::delete);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean DeleteContact(Long contactId) {
        try {
            opportunityContactRepository.deleteByContactId(contactId);
            Optional<Contact> contacts = contactRepository.findById(contactId);
            if(contacts.isPresent()) {
                Contact contact = contacts.get();
                Long addressInformationId = contact.getAddressInformation() != null
                        ? contact.getAddressInformation().getAddressInformationId() : null;
                if (addressInformationId != null) {
                    addressInformationRepository.setAddressInformationToNullInContact(addressInformationId);
                    addressInformationRepository.setAddressInformationToNull(addressInformationId);
                }

                contact.setUserId(null);
                contact.setContactSalution(null);
                contact.setCreatedBy(null);
                contact.setEditBy(null);

                contactRepository.save(contact);
                contacts.ifPresent(contactRepository::delete);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean DeleteListLeads(List<Long> listLeadId) {
        try {
            for (Long leadId : listLeadId) {
                logCallLeadsRepository.deleteAll(logCallLeadsRepository.findByLead_LeadId(leadId));
            }

            for (Long leadId : listLeadId) {
                Optional<Leads> leads = leadsRepository.findById(leadId);
                if (leads.isPresent()) {
                    Leads lead = leads.get();
                    if (lead.getLeadSource() != null) {
                        opportunityRepository.updateLeadSourceIdToNull(lead.getLeadSource().getLeadSourceId());
                    }
                    lead.setIndustry(null);
                    lead.setLeadSource(null);
                    lead.setLeadStatus(null);
                    lead.setAddressInformation(null);
                    lead.setLeadRating(null);
                    lead.setLeadSalution(null);

                    leadsRepository.save(lead);
                    leadsRepository.delete(lead);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean DeletelistAccount(List<Long> listAccountId) {
        try {
            for (Long accountId : listAccountId) {
                Optional<Account> accounts = accountRepository.findById(accountId);
                if (accounts.isPresent()) {
                    Account account = accounts.get();
                    if (account.getBillingInformation() != null) {
                        addressInformationRepository.setBillingInformationToNull(account.getBillingInformation().getAddressInformationId());
                    }

                    if (account.getShippingInformation() != null) {
                        addressInformationRepository.setShippingInformationToNull(account.getShippingInformation().getAddressInformationId());
                    }
                    contactRepository.setAccountIdToNull(accountId);
                    account.setAccountType(null);
                    account.setIndustry(null);

                    accountRepository.save(account);
                    accountRepository.delete(account);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean DeletelistOpportunity(List<Long> listOpportunityId) {
        try {
            for (Long opportunityId : listOpportunityId) {
                Optional<Opportunity> opportunities = opportunityRepository.findById(opportunityId);
                if (opportunities.isPresent()) {
                    Opportunity opportunity = opportunities.get();

                    opportunity.setLeadSourceId(null);
                    opportunity.setForecast(null);
                    opportunity.setStage(null);
                    opportunity.setType(null);
                    opportunity.setPrimaryCampaignSourceId(null);

                    opportunityRepository.save(opportunity);
                    opportunityRepository.delete(opportunity);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean DeletelistContactt(List<Long> listContactId) {
        try {
            for (Long contactId : listContactId) {
                Optional<Contact> contacts = contactRepository.findById(contactId);
                if (contacts.isPresent()) {
                    Contact contact = contacts.get();
                    Long addressInformationId = contact.getAddressInformation() != null
                            ? contact.getAddressInformation().getAddressInformationId() : null;
                    if (addressInformationId != null) {
                        addressInformationRepository.setAddressInformationToNullInContact(addressInformationId);
                        addressInformationRepository.setAddressInformationToNull(addressInformationId);
                    }

                    contact.setUserId(null);
                    contact.setContactSalution(null);
                    contact.setCreatedBy(null);
                    contact.setEditBy(null);

                    contactRepository.save(contact);
                    contactRepository.delete(contact);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean DeletelistEmailTemplate(List<Long> listEmailTempateId) {
        try {
            for (Long emailTemplateId : listEmailTempateId) {
                Optional<EmailTemplate> emailTemplates = emailTemplateRepository.findById(emailTemplateId);
                if (emailTemplates.isPresent()) {
                    EmailTemplate emailTemplate = emailTemplates.get();

                    emailTemplate.setUserId(null);

                    emailTemplateRepository.save(emailTemplate);
                    emailTemplateRepository.delete(emailTemplate);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean RestoreLeads(Long leadId) {
        try {
            Optional<Leads> leads = leadsRepository.findById(leadId);
            leads.ifPresent(acc -> {
                acc.setIsDelete(0);
                leadsRepository.save(acc);
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean RestoreAccount(Long accountId) {
        try {
            Optional<Account> account = accountRepository.findById(accountId);
            account.ifPresent(acc -> {
                acc.setIsDeleted(0);
                accountRepository.save(acc);
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean RestoreOpportunity(Long opportunityId) {
        try {
            Optional<Opportunity> opportunity = opportunityRepository.findById(opportunityId);
            opportunity.ifPresent(acc -> {
                acc.setIsDeleted(false);
                opportunityRepository.save(acc);
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean RestoreEmailTemplate(Long emailTempateId) {
        try {
            Optional<EmailTemplate> emailTemplate = emailTemplateRepository.findById(emailTempateId);
            emailTemplate.ifPresent(acc -> {
                acc.setIsDeleted(0);
                emailTemplateRepository.save(acc);
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean RestoreContact(Long contactId) {
        try {
            Optional<Contact> contact = contactRepository.findById(contactId);
            contact.ifPresent(acc -> {
                acc.setIsDeleted(0);
                contactRepository.save(acc);
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean RestoreListLeads(List<Long> listLeadId) {
        try {
            List<Leads> leads = leadsRepository.findAllById(listLeadId);
            leads.forEach(acc -> acc.setIsDelete(0));
            leadsRepository.saveAll(leads);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean RestoreListAccount(List<Long> listAccountId) {
        try {
            List<Account> accounts = accountRepository.findAllById(listAccountId);
            accounts.forEach(acc -> acc.setIsDeleted(0));
            accountRepository.saveAll(accounts);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean RestoreListOpportunity(List<Long> listOpportunityId) {
        try {
            List<Opportunity> opportunities = opportunityRepository.findAllById(listOpportunityId);
            opportunities.forEach(acc -> acc.setIsDeleted(false));
            opportunityRepository.saveAll(opportunities);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean RestoreListEmailTemplate(List<Long> listEmailTempateId) {
        try {
            List<EmailTemplate> emailTemplates = emailTemplateRepository.findAllById(listEmailTempateId);
            emailTemplates.forEach(acc -> acc.setIsDeleted(0));
            emailTemplateRepository.saveAll(emailTemplates);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean RestoreListContact(List<Long> listContactId) {
        try {
            List<Contact> contacts = contactRepository.findAllById(listContactId);
            contacts.forEach(acc -> acc.setIsDeleted(0));
            contactRepository.saveAll(contacts);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
