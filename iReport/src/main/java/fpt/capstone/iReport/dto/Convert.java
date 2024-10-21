package fpt.capstone.iReport.dto;

import fpt.capstone.iReport.dto.request.*;
import fpt.capstone.iReport.dto.response.*;
import fpt.capstone.iReport.model.*;
import fpt.capstone.iReport.model.accounts.Account;
import fpt.capstone.iReport.model.accounts.AccountType;
import fpt.capstone.iReport.model.leads.LeadSource;
import fpt.capstone.iReport.model.leads.Leads;
import fpt.capstone.iReport.model.opportunities.Forecast;
import fpt.capstone.iReport.model.opportunities.Opportunity;
import fpt.capstone.iReport.model.opportunities.Stage;
import fpt.capstone.iReport.model.opportunities.Type;
import fpt.capstone.iReport.model.report.Report;
import fpt.capstone.iReport.repository.*;
import fpt.capstone.iReport.repository.account.AccountTypeRepository;
import fpt.capstone.iReport.repository.leads.*;
import fpt.capstone.iReport.repository.oppotunity.ForecastRepository;
import fpt.capstone.iReport.repository.oppotunity.StageRepository;
import fpt.capstone.iReport.repository.oppotunity.TypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class Convert {
    private final ContractStatusRepository contractStatusRepository;
    private final ForecastRepository forecastRepository;
    private final StageRepository stageRepository;
    private final TypeRepository typeRepository;
    private final LeadSourceRepository leadSourceRepository;
    private final ReportRepository repository;
    private final LeadStatusRepository leadStatusRepository;
    private final LeadsRepository leadsRepository;
    private final LeadRatingRepository leadRatingRepository;
    private final LeadSalutionRepository leadSalutionRepository ;
    private final IndustryRepository industryRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final OrderStatusRepository orderStatusRepository;
//    private final ProductPriceBookRepository productPriceBookRepository;
    private final PriceBookRepository priceBookRepository;
    private final ProductRepository productRepository;
//    private final QuoteOpportunityRepository quoteOpportunityRepository;
    private final ProcessingQuotePriceBookRepository processingQuotePriceBookRepository;
//    private final QuoteOpportunityProductRepository quoteOpportunityProductRepository;
    public OpportunityDTO ConvertOpportunityToDTO(Opportunity opportunity){
        String forecast = opportunity.getForecast() != null &&
                opportunity.getForecast().getForecastCategoryId() != null ? forecastRepository
                .findById(opportunity.getForecast().getForecastCategoryId())
                .map(Forecast::getForecastName).orElse("") : "";
        String stage =opportunity.getStage() != null &&
                opportunity.getStage().getStageId() != null ?  stageRepository
                .findById(opportunity.getStage().getStageId())
                .map(Stage::getStageName).orElse("") : "";
        String type =opportunity.getType() != null &&
                opportunity.getType().getTypeId() != null?  typeRepository
                .findById(opportunity.getType().getTypeId())
                .map(Type::getTypeName).orElse("") : "";
        String leadSource =opportunity.getLeadSourceId() != null ?  leadSourceRepository.
                findById(opportunity.getLeadSourceId())
                .map(LeadSource::getLeadSourceName).orElse("") : "";

        if (opportunity == null) return null ;
        return OpportunityDTO.builder()
                .opportunityId(opportunity.getOpportunityId())
                .userId(opportunity.getUserId())
                .accountId(opportunity.getAccountId())
                .opportunityName(opportunity.getOpportunityName())
                .probability(opportunity.getProbability())
                .forecast(Optional.ofNullable(forecast).orElse(""))
                .nextStep(opportunity.getNextStep())
                .amount(opportunity.getAmount())
                .closeDate(opportunity.getCloseDate())
                .stage(Optional.ofNullable(stage).orElse(""))
                .type(Optional.ofNullable(type).orElse(""))
                .leadSource(Optional.ofNullable(leadSource).orElse(""))
                .primaryCampaignSourceId(opportunity.getPrimaryCampaignSourceId())
                .description(opportunity.getDescription())
                .lastModifiedBy(opportunity.getLastModifiedBy())
                .editDate(opportunity.getEditDate())
                .createBy(opportunity.getCreateBy())
                .createDate(opportunity.getCreateDate())
                .isDeleted(opportunity.getIsDeleted())
                .build();
    }
    public ReportDTO SaveDataFileToDTO(String fileName,
                                       String fileCloudId,
                                       String userId
    ){
        return ReportDTO.builder()
                .fileId(fileCloudId)
                .fileName(fileName)
                .userId(userId)
                .build();
    }
    public Report ReportDTOToEntity(ReportDTO reportDTO){
        return Report.builder()
                .fileId(reportDTO.getFileId())
                .userId(reportDTO.getUserId())
                .fileName(reportDTO.getFileName())
                .build();

    }
    public List<OpportunityDTO> ConvertListOpportunitiesToListDTOs(List<Opportunity> opportunities) {
        return opportunities.stream()
                .map(this::ConvertOpportunityToDTO)
                .collect(Collectors.toList());
    }
    public List<LeadDTO> ConvertLeadsToDTOs(List<Leads> leads) {
        return leads.stream()
                .map(this::LeadToLeadReportDTO)
                .collect(Collectors.toList());
    }

    public LeadDTO LeadToLeadReportDTO(Leads lead) {
        if (lead == null) return null;

        return LeadDTO.builder()
                .leadId(lead.getLeadId())
                .userId(lead.getUserId())
                .firstName(lead.getFirstName())
                .lastName(lead.getLastName())
                .middleName(lead.getMiddleName())
                .gender(lead.getGender())
                .title(lead.getTitle())
                .email(lead.getEmail())
                .phone(lead.getPhone())
                .website(lead.getWebsite())
                .company(lead.getCompany())
                .noEmployee(lead.getNoEmployee())
                .createdBy(lead.getCreatedBy())
                .createDate(lead.getCreateDate())
                .editDate(lead.getEditDate())
                .editBy(lead.getEditBy())
                .isDelete(lead.getIsDelete())
                .build();
    }
    public List<AccountDTO> ConvertAccountsToDTOs(List<Account> accounts) {
        return accounts.stream()
                .map(this::AccountToAccountDTO)
                .collect(Collectors.toList());
    }

    public AccountDTO AccountToAccountDTO(Account account) {
        if (account == null) return null;
        String accountType = account.getAccountType() != null &&
                account.getAccountType().getAccountTypeId() != null ? accountTypeRepository
                .findById(account.getAccountType().getAccountTypeId())
                .map(AccountType::getAccountTypeName).orElse("") : "";
        String industry = account.getIndustry() != null &&
                account.getIndustry().getIndustryId() != null ? industryRepository
                .findById(account.getIndustry().getIndustryId())
                .map(Industry::getIndustryStatusName).orElse("") : "";
        return AccountDTO.builder()
                .accountId(account.getAccountId())
                .accountName(account.getAccountName())
                .userId(account.getUserId())
                .parentAccountId(account.getParentAccountId())
                .industry(Optional.ofNullable(industry).orElse(""))
                .accountType(Optional.ofNullable(accountType).orElse(""))
                .website(account.getWebsite())
                .phone(account.getPhone())
                .description(account.getDescription())
                .noEmployee(account.getNoEmployee())
                .createdBy(account.getCreatedBy())
                .createDate(account.getCreateDate())
                .editDate(account.getEditDate())
                .editBy(account.getEditBy())
                .isDeleted(account.getIsDeleted())
                .build();
    }

    public ContactDTO ContactToDTO(Contact contact) {
        if (contact == null) return null;

        return ContactDTO.builder()
                .contactId(contact.getContactId())
                .accountId(contact.getContactId())
                .userId(contact.getUserId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .middleName(contact.getMiddleName())
                .report_to(contact.getReport_to())
                .title(contact.getTitle())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .department(contact.getDepartment())
                .mobile(contact.getMobile())
                .fax(contact.getFax())
                .createdBy(contact.getCreatedBy())
                .createDate(contact.getCreateDate())
                .editBy(contact.getEditBy())
                .editDate(contact.getEditDate())
                .isDeleted(contact.getIsDeleted())
                .build();
    }

    public EmailTemplateDTO EmailTeamplateToDTO(EmailTemplate emailTemplate) {
        if (emailTemplate == null) return null;

        return EmailTemplateDTO.builder()
                .id(emailTemplate.getEmailTemplateId())
                .sendTo(emailTemplate.getSendTo())
                .mailSubject(emailTemplate.getMailSubject())
                .htmlContent(emailTemplate.getHtmlContent())
                .isDeleted(emailTemplate.getIsDeleted())
                .build();
    }

    public PageResponse<?> ConvertLeadsToPageResponse(Page<Leads> leads, Pageable pageable) {
        List<LeadDTO> response = leads.stream().map(this::LeadToLeadReportDTO).toList();
        return PageResponse.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .total(leads.getTotalPages())
                .items(response)
                .build();
    }
    public PageResponse<?> ConvertAccountToPageResponse(Page<Account> accounts, Pageable pageable) {
        List<AccountDTO> response = accounts.stream().map(this::AccountToAccountDTO).toList();
        return PageResponse.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .total(accounts.getTotalPages())
                .items(response)
                .build();
    }
    public PageResponse<?> ConvertOpportunityToPageResponse(Page<Opportunity> opportunities, Pageable pageable) {
        List<OpportunityDTO> response = opportunities.stream().map(this::ConvertOpportunityToDTO).toList();
        return PageResponse.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .total(opportunities.getTotalPages())
                .items(response)
                .build();
    }
    public PageResponse<?> ConvertContactToPageResponse(Page<Contact> contacts, Pageable pageable) {
        List<ContactDTO> response = contacts.stream().map(this::ContactToDTO).toList();
        return PageResponse.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .total(contacts.getTotalPages())
                .items(response)
                .build();
    }

    public PageResponse<?> ConvertEmailTemplatePageResponse(Page<EmailTemplate> emailTemplates, Pageable pageable) {
        List<EmailTemplateDTO> response = emailTemplates.stream().map(this::EmailTeamplateToDTO).toList();
        return PageResponse.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .total(emailTemplates.getTotalPages())
                .items(response)
                .build();
    }
    public Contract convertContractDtoToEntity(ContractDTO contractDTO){
        return Contract.builder()
                .userId(contractDTO.getUserId())
                .contractStartDate(contractDTO.getContractStartDate())
                .contractTerm(contractDTO.getContractTerm())
                .ownerExpirationNotice(contractDTO.getOwnerExpirationNotice())
                .specialTerms(contractDTO.getSpecialTerms())
                .description(contractDTO.getDescription())
                .accountId(contractDTO.getAccountId())
                .priceBookId(contractDTO.getPriceBookId())
                .contactId(contractDTO.getContactId())
                .customerSignedTitle(contractDTO.getCustomerSignedTitle())
                .customerSignedDate(contractDTO.getCustomerSignedDate())
                .companyId(contractDTO.getCompanyId())
                .companySignedDate(contractDTO.getCompanySignedDate())
                .contractStatus(contractStatusRepository.findById(contractDTO.getContractStatus()).orElse(null))
                .build();
    }

    public ContractDTO convertContractDtoToEntity(Contract contract){
        AddressInformationDTO billingInformation = contract.getBillingInformation() != null ?
                AddressInformationToDTO(contract.getBillingInformation()) : null;
        AddressInformationDTO shippAddressInformation = contract.getShippingInformation() != null ?
                AddressInformationToDTO(contract.getBillingInformation()) : null;
        Long contractStatus;
        if(contract.getContractStatus() == null){
            contractStatus = null;
        }
        else{
            contractStatus = contract.getContractStatus().getContractStatusId();
        }
        return ContractDTO.builder()
                .contractId(contract.getContractId())
                .userId(contract.getUserId())
                .contractStartDate(contract.getContractStartDate())
                .contractTerm(contract.getContractTerm())
                .ownerExpirationNotice(contract.getOwnerExpirationNotice())
                .specialTerms(contract.getSpecialTerms())
                .description(contract.getDescription())
                .shippingAddressId(billingInformation)
                .billingAddressId(shippAddressInformation)
                .accountId(contract.getAccountId())
                .priceBookId(contract.getPriceBookId())
                .contactId(contract.getContactId())
                .customerSignedTitle(contract.getCustomerSignedTitle())
                .customerSignedDate(contract.getCustomerSignedDate())
                .companyId(contract.getCompanyId())
                .companySignedDate(contract.getCompanySignedDate())
                .contractStatus(contractStatus)
                .contractNumber(contract.getContractNumber())
                .build();
    }
    public Contract UpdateContractFromContactDTO(Contract contract, ContractDTO contractDTO){
        if(contractDTO.getUserId() != null)
        contract.setUserId(contractDTO.getUserId());
        if(contractDTO.getContractStartDate() != null)
        contract.setContractStartDate(contractDTO.getContractStartDate());
        if(contractDTO.getContractTerm() != null)
        contract.setContractTerm(contractDTO.getContractTerm());
        if( contractDTO.getOwnerExpirationNotice()!= null)
        contract.setOwnerExpirationNotice(contractDTO.getOwnerExpirationNotice());
        if( contractDTO.getSpecialTerms()!= null)
        contract.setSpecialTerms(contractDTO.getSpecialTerms());
        if( contractDTO.getDescription()!= null)
        contract.setDescription(contractDTO.getDescription());
        if( contractDTO.getAccountId()!= null)
        contract.setAccountId(contractDTO.getAccountId());
        if(contractDTO.getPriceBookId() != null)
        contract.setPriceBookId(contractDTO.getPriceBookId());
        if(contractDTO.getContactId() != null)
        contract.setContactId(contractDTO.getContactId());
        if(contractDTO.getCustomerSignedTitle() != null)
        contract.setCustomerSignedTitle(contractDTO.getCustomerSignedTitle());
        if( contractDTO.getCustomerSignedDate()!= null)
        contract.setCustomerSignedDate(contractDTO.getCustomerSignedDate());
        if(contractDTO.getCompanyId() != null)
        contract.setCompanyId(contractDTO.getCompanyId());
        if(contractDTO.getCompanySignedDate() != null)
        contract.setCompanySignedDate(contractDTO.getCompanySignedDate());
        if(contractDTO.getContractStatus() != null)
        contract.setContractStatus(contractStatusRepository.findById(contractDTO.getContractStatus()).orElse(null));
        return contract;
    }
    public AddressInformation DTOToAddressInformation(AddressInformationDTO addressInformation) {
        if (addressInformation == null) return null ;
        return AddressInformation.builder()
                .street(addressInformation.getStreet())
                .city(addressInformation.getCity())
                .province(addressInformation.getProvince())
                .postalCode(addressInformation.getPostalCode())
                .country(addressInformation.getCountry())
                .build();
    }
    public AddressInformationDTO AddressInformationToDTO(AddressInformation addressInformation) {
        if (addressInformation == null) return null ;
        return AddressInformationDTO.builder()
                .addressInformationId(addressInformation.getAddressInformationId())
                .street(addressInformation.getStreet())
                .city(addressInformation.getCity())
                .province(addressInformation.getProvince())
                .postalCode(addressInformation.getPostalCode())
                .country(addressInformation.getCountry())
                .build();
    }
    public AddressInformation UpdateDTOToAddressInformation(AddressInformation updateAddress,
                                                            AddressInformationDTO addressInformation)
    {
        updateAddress.setStreet(addressInformation.getStreet());
        updateAddress.setCity(addressInformation.getCity());
        updateAddress.setProvince(addressInformation.getProvince());
        updateAddress.setPostalCode(addressInformation.getPostalCode());
        updateAddress.setCountry(addressInformation.getCountry());
        return updateAddress;
    }
    public PageResponse<?> convertContractToPageFileReponse(Page<Contract> contracts, Pageable pageable) {
        List<ContractDTO> response = contracts.stream().map(this::convertContractDtoToEntity).toList();
        return PageResponse.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .total(contracts.getTotalPages())
                .items(response)
                .build();
    }

    public List<OrderContractDTO> ConvertListOrderContractToDto(List<OrderContract> orderContract) {
        return orderContract.stream()
                .map(this::convertOrderContractToDto)
                .collect(Collectors.toList());
    }
    public OrderContractDTO convertOrderContractToDto(OrderContract orderContract){
        AddressInformationDTO billingInformation = orderContract.getBillingInformation() != null ?
                AddressInformationToDTO(orderContract.getBillingInformation()) : null;
        AddressInformationDTO shippAddressInformation = orderContract.getShippingInformation() != null ?
                AddressInformationToDTO(orderContract.getBillingInformation()) : null;
        return OrderContractDTO.builder()
                .orderId(orderContract.getOrderId())
                .accountId(orderContract.getAccountId())
                .orderStartDate(orderContract.getOrderStartDate())
                .orderStatus(orderContract.getOrderStatus().getOrderStatusId())
                .companyId(orderContract.getCompanyId())
                .description(orderContract.getDescription())
                .BillingInformation(billingInformation)
                .ShippingInformation(shippAddressInformation)
                .contactId(orderContract.getContactId())
                .contractNumber(orderContract.getContractNumber())
                .orderNumber(orderContract.getOrderContractNumber())
                .build();
    }

    public OrderContract convertOrderContractDtoToEntity(OrderContractDTO orderContract){

        return OrderContract.builder()
                .orderId(orderContract.getOrderId())
                .accountId(orderContract.getAccountId())
                .orderStartDate(orderContract.getOrderStartDate())
                .orderStatus(orderStatusRepository.findById(orderContract.getOrderStatus()).orElse(null))
                .companyId(orderContract.getCompanyId())
                .description(orderContract.getDescription())
                .contactId(orderContract.getContactId())
                .contractNumber(orderContract.getContractNumber())
                .build();
    }
    public PageResponse<?> convertOrderContractToPageFileReponse(Page<OrderContract> contracts, Pageable pageable) {
        List<OrderContractDTO> response = contracts.stream().map(this::convertOrderContractToDto).toList();
        return PageResponse.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .total(contracts.getTotalPages())
                .items(response)
                .build();
    }
    public OrderContract UpdateOrderContractFromContractDTO(OrderContract orderContract, OrderContractDTO orderContractDTO){
        if(orderContractDTO.getAccountId() != null)
        orderContract.setAccountId(orderContractDTO.getAccountId());
        if(orderContractDTO.getOrderStartDate() != null)
        orderContract.setOrderStartDate(orderContractDTO.getOrderStartDate());
        if(orderContractDTO.getOrderStatus() != null)
        orderContract.setOrderStatus(orderStatusRepository.findById(orderContractDTO.getOrderStatus()).orElse(null));
        if(orderContractDTO.getCompanyId() != null)
        orderContract.setCompanyId(orderContractDTO.getCompanyId());
        if(orderContractDTO.getDescription() != null)
        orderContract.setDescription(orderContractDTO.getDescription());
        if(orderContractDTO.getContactId() != null)
        orderContract.setContactId(orderContractDTO.getContactId());
        if(orderContractDTO.getContractNumber() != null)
        orderContract.setContractNumber(orderContractDTO.getContractNumber());
        return orderContract;
    }
    public List<AccountContractDTO> ConvertAccountToAccountContractDTOs(List<Account> accounts) {
        return accounts.stream()
                .map(this::AccountToAccountContractDTO)
                .collect(Collectors.toList());
    }
    public AccountContractDTO AccountToAccountContractDTO(Account account) {
        return AccountContractDTO.builder()
                .accountId(account.getAccountId())
                .accountName(account.getAccountName())
                .build();
    }
    public List<ProductPriceBookDTO> ConvertListProductPriceBookDTOs(List<ProductPriceBook> productPriceBook) {
        return productPriceBook.stream()
                .map(this::ConvertProductPriceBook)
                .collect(Collectors.toMap(
                        ProductPriceBookDTO::getPriceBookId, // keyMapper: sử dụng priceBookId làm khóa
                        dto -> dto, // valueMapper: giá trị là chính ProductPriceBookDTO
                        (existing, replacement) -> existing // nếu trùng, giữ lại giá trị hiện tại
                ))
                .values()
                .stream()
                .collect(Collectors.toList());
    }
    public ProductPriceBookDTO ConvertProductPriceBook(ProductPriceBook productPriceBook){
        PriceBook priceBook = priceBookRepository.findById(productPriceBook.getPriceBookId()).orElse(null);
        return ProductPriceBookDTO.builder()
                .priceBookId(productPriceBook.getPriceBookId())
                .priceBookName(priceBook.getPriceBookName())
                .build();
    }

    public List<ListPriceBookeProductReponse> ConvertListPriceBookProductOrderReponse(List<ProductPriceBook> productPriceBook) {
        return productPriceBook.stream()
                .map(this::ConvertPriceBookeProductOrderReponse)
                .collect(Collectors.toList());
    }
    public ListPriceBookeProductReponse ConvertPriceBookeProductOrderReponse
            (ProductPriceBook productPriceBook){
        String productFamily;
        String productPriceBookCurrencyName;
        Product product = productRepository.findById(productPriceBook.getProductId()).orElse(null);
        if(product.getProductFamily() != null) {
            productFamily = product.getProductFamily().getProductFamilyName();
            if (product.getProductFamily().getProductFamilyName() == null) {
                productFamily = null;
            }
             productPriceBookCurrencyName = product.getProductFamily().getProductFamilyName();
            if (product.getProductFamily().getProductFamilyName() == null) {
                productPriceBookCurrencyName = null;
            }
        }
        else{
            productFamily = null;
            productPriceBookCurrencyName = null;
        }
        return ListPriceBookeProductReponse.builder()
                .productId(productPriceBook.getProductId())
                .listPrice(productPriceBook.getListPrice())
                .productName(product.getProductName())
                .productCode(product.getProductCode())
                .productFamily(productFamily)
                .productDescription(product.getProductDescription())
                .productPriceBookCurrencyName(productPriceBookCurrencyName)
                .build();

    }
//
//    public List<QuoteOpportunityProduct> ConvertQuoteProduct(List<QuoteProductDTO> quoteProductDTO) {
//        return quoteProductDTO.stream()
//                .map(this::convertDTOToQuoteProduct)
//                .collect(Collectors.toList());
//    }
//
//    public QuoteOpportunityProduct convertDTOToQuoteProduct(QuoteProductDTO quoteProductDTO){
//            double  unitPrice = quoteProductDTO.getUnitPrice();
//            int quantity = quoteProductDTO.getQuantity();
//            double total = unitPrice * quantity;
//            return QuoteOpportunityProduct.builder()
//                    .quoteId(quoteProductDTO.getQuoteId())
//                    .productName(quoteProductDTO.getProductName())
//                    .quantity(quantity)
//                    .unitPrice(unitPrice)
//                    .listPrice(quoteProductDTO.getListPrice())
//                    .totalPrice(total)
//                    .description(quoteProductDTO.getDescription())
//                    .productId(quoteProductDTO.getProductId())
//                    .build();
//
//    }

    public List<OrderContractProduct> ConvertLOrderContractProduct(List<OrderContractProductDTO> orderContractProductDTO) {
        return orderContractProductDTO.stream()
                .map(this::convertDTOToOrderContractProduct)
                .collect(Collectors.toList());
    }

    public OrderContractProduct convertDTOToOrderContractProduct(OrderContractProductDTO orderContractProductDTO){
        double  unitPrice = orderContractProductDTO.getUnitPrice();
        int quantity = orderContractProductDTO.getQuantity();
        double total = unitPrice * quantity;
        return OrderContractProduct.builder()
                .orderId(orderContractProductDTO.getOrderId())
                .productName(orderContractProductDTO.getProductName())
                .quantity(quantity)
                .unitPrice(unitPrice)
                .listPrice(orderContractProductDTO.getListPrice())
                .totalPrice(total)
                .description(orderContractProductDTO.getDescription())
                .productId(orderContractProductDTO.getProductId())
                .isDelete(0)
                .build();

    }

    public OrderContractProduct UpdateOrderContractProductFromDTO
            (OrderContractProduct orderContractProduct, OrderContractProductDTO orderContractProductDTO){


        if(orderContractProductDTO.getProductName() != null)
        orderContractProduct.setProductName(orderContractProductDTO.getProductName());

        if(orderContractProductDTO.getUnitPrice() != null)
        orderContractProduct.setUnitPrice(orderContractProductDTO.getUnitPrice());

        orderContractProduct.setQuantity(orderContractProductDTO.getQuantity());

        orderContractProduct.setTotalPrice(orderContractProductDTO.getUnitPrice() * orderContractProductDTO.getQuantity());

        if(orderContractProductDTO.getDescription() != null)
        orderContractProduct.setDescription(orderContractProductDTO.getDescription());
        return orderContractProduct;
    }
//    public QuoteOpportunity convertQuoteOpportunityDtoToEntity(QuoteOpportunityDTO quoteOpportunityDTO){
//
//        return QuoteOpportunity.builder()
//                .id(quoteOpportunityDTO.getId())
//                .opportunityName(quoteOpportunityDTO.getOpportunityName())
//                .quoteName(quoteOpportunityDTO.getQuoteName())
//                .expirationDate(quoteOpportunityDTO.getExpirationDate())
//                .statusQuoteOpportunityId(quoteOpportunityDTO.getStatusQuoteOpportunityId())
//                .description(quoteOpportunityDTO.getDescription())
//                .opportunityId(quoteOpportunityDTO.getOpportunityId())
//                .accountName(quoteOpportunityDTO.getAccountName())
//                .discount(quoteOpportunityDTO.getDiscount())
//                .tax(quoteOpportunityDTO.getTax())
//                .shippingHandling(quoteOpportunityDTO.getShippingHandling())
//                .grandTotal(quoteOpportunityDTO.getGrandTotal())
//                .contactId(quoteOpportunityDTO.getContactId())
//                .email(quoteOpportunityDTO.getEmail())
//                .phone(quoteOpportunityDTO.getPhone())
//                .fax(quoteOpportunityDTO.getFax())
//                .billingName(quoteOpportunityDTO.getBillingName())
//                .shippingName(quoteOpportunityDTO.getShippingName())
//                .build();
//    }
//    public QuoteOpportunityReponse convertQuoteOpportunityEntityToDto(QuoteOpportunity quoteOpportunity){
//
//        return QuoteOpportunityReponse.builder()
//                .id(quoteOpportunity.getId())
//                .opportunityName(quoteOpportunity.getOpportunityName())
//                .quoteName(quoteOpportunity.getQuoteName())
//                .expirationDate(quoteOpportunity.getExpirationDate())
//                .statusQuoteOpportunityId(quoteOpportunity.getStatusQuoteOpportunityId())
//                .description(quoteOpportunity.getDescription())
//                .opportunityId(quoteOpportunity.getOpportunityId())
//                .accountName(quoteOpportunity.getAccountName())
//                .discount(quoteOpportunity.getDiscount())
//                .tax(quoteOpportunity.getTax())
//                .shippingHandling(quoteOpportunity.getShippingHandling())
//                .grandTotal(quoteOpportunity.getGrandTotal())
//                .contactId(quoteOpportunity.getContactId())
//                .email(quoteOpportunity.getEmail())
//                .phone(quoteOpportunity.getPhone())
//                .fax(quoteOpportunity.getFax())
//                .billingName(quoteOpportunity.getBillingName())
//                .shippingName(quoteOpportunity.getShippingName())
//                .build();
//    }
//    public QuoteReport  getQuoteReport(Long quoteId) {
//        // Fetch QuoteOpportunity entity
//        QuoteOpportunity quoteOpportunity = quoteOpportunityRepository.findById(quoteId)
//                .orElseThrow(() -> new RuntimeException("QuoteOpportunity not found"));
//
//        // Fetch associated QuoteOpportunityProducts
//        List<QuoteOpportunityProduct> products = quoteOpportunityProductRepository.findByQuoteId(quoteId);
//
//        // Create list of QuoteProductResponse
//        List<QuoteProductResponse> productResponses = products.stream()
//                .map(product -> QuoteProductResponse.builder()
//                        .productName(product.getProductName())
//                        .quantity(product.getQuantity())
//                        .unitPrice(product.getUnitPrice())
//                        .totalPrice2(product.getTotalPrice())
//                        .listPrice(product.getListPrice())
//                        .build())
//                .collect(Collectors.toList());
//
//        // Extract AddressInformation
//        AddressInformation billingAddress = quoteOpportunity.getBillingAddressInformation();
//        AddressInformation shippingAddress = quoteOpportunity.getShippingInformationId();
//
//        // Map to QuoteReport
//        return QuoteReport.builder()
//                .prepareBy("system")
//                .email(quoteOpportunity.getEmail())
//                .shipToName(shippingAddress != null ? shippingAddress.getStreet() : null)
//                .shipToNameStreet(shippingAddress != null ? shippingAddress.getStreet() : null)
//                .shipToNameCity(shippingAddress != null ? shippingAddress.getCity() : null)
//                .shipToNamePostalCode(shippingAddress != null ? shippingAddress.getPostalCode() : null)
//                .shipToNameCountry(shippingAddress != null ? shippingAddress.getCountry() : null)
//                .billToName(billingAddress != null ? billingAddress.getStreet() : null)
//                .billToNameStreet(billingAddress != null ? billingAddress.getStreet() : null)
//                .billToNameCity(billingAddress != null ? billingAddress.getCity() : null)
//                .billToNamePostalCode(billingAddress != null ? billingAddress.getPostalCode() : null)
//                .billToNameCountry(billingAddress != null ? billingAddress.getCountry() : null)
//                .createDate(quoteOpportunity.getExpirationDate())
//                .quoteNumber(quoteOpportunity.getQuoteNumber())
//                .subTotal(quoteOpportunity.getDiscount())  // Adjust as necessary
//                .discount(quoteOpportunity.getDiscount())
//                .totalPrice(quoteOpportunity.getGrandTotal())
//                .grandTotal(quoteOpportunity.getGrandTotal())
//                .quoteProductResponses(productResponses)
//                .build();
//    }
}
