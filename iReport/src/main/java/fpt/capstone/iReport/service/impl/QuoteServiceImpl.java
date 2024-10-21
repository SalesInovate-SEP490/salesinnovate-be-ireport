//package fpt.capstone.iReport.service.impl;
//
//import fpt.capstone.iReport.dto.Convert;
//import fpt.capstone.iReport.dto.ProcessingQuotePriceBook;
//import fpt.capstone.iReport.dto.request.*;
//import fpt.capstone.iReport.dto.response.*;
//import fpt.capstone.iReport.model.*;
//import fpt.capstone.iReport.model.accounts.Account;
//import fpt.capstone.iReport.model.opportunities.Opportunity;
//import fpt.capstone.iReport.repository.*;
//import fpt.capstone.iReport.repository.account.AccountRepository;
//import fpt.capstone.iReport.repository.oppotunity.OpportunityRepository;
//import fpt.capstone.iReport.service.QuoteService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import net.sf.jasperreports.engine.*;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import net.sf.jasperreports.engine.export.JRPdfExporter;
//import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.*;
//
//@Slf4j
//@Service
//@AllArgsConstructor
//public class QuoteServiceImpl implements QuoteService {
//
//    private final Convert convert;
//    private final ContractRepository contractRepository;
//    private final AddressInformationRepository addressInformationRepository;
//    private final OrderStatusRepository orderStatusRepository;
//    private final OrderContractRepository orderContractRepository;
//    private static final int MINIMUM_LENGTH = 10;
//    private final AccountRepository accountRepository;
//    private final ContractStatusRepository contractStatusRepository;
//    private final ProductPriceBookRepository productPriceBookRepository;
//    private final ProcessingOrderPriceBookRepository processingOrderPriceBookRepository;
//    private final PriceBookRepository priceBookRepository;
//    private final OrderContractProductRepository orderContractProductRepository;
//    private final OpportunityRepository opportunityRepository;
//    private final QuoteOpportunityRepository quoteOpportunityRepository;
//    private final ProcessingQuotePriceBookRepository processingQuotePriceBookRepository;
//    private final QuoteOpportunityProductRepository quoteOpportunityProductRepository;
//    private final ProductRepository productRepository;
//    @Override
//    public OpportunityInfoQuoteDTO getOpportunityInfoQuoteDTO(Long oppportunityId) {
//        try {
//            OpportunityInfoQuoteDTO opportunityInfoQuoteDTO = new OpportunityInfoQuoteDTO() ;
//            Optional<Opportunity> opportunityOptional = opportunityRepository.findById(oppportunityId);
//            if(opportunityOptional != null){
//                Opportunity opportunityResult = opportunityOptional.get();
//                opportunityInfoQuoteDTO.setOpportunityId(opportunityResult.getOpportunityId());
//                opportunityInfoQuoteDTO.setOpportunityName
//                        (opportunityResult.getOpportunityName() != null ? opportunityResult.getOpportunityName() : null);
//                opportunityInfoQuoteDTO.setUserId
//                        (opportunityResult.getUserId() != null ? opportunityResult.getUserId() : null);
//                Account account = new Account();
//                if(opportunityResult.getAccountId() != null){
//                    Account accountDetail = accountRepository.findById(opportunityResult.getAccountId()).orElse(null);
//                    opportunityInfoQuoteDTO.setAccountId
//                            (accountDetail.getAccountId() != null ? accountDetail.getAccountId() : null);
//                    opportunityInfoQuoteDTO.setAccountName
//                            (accountDetail.getAccountName() != null ? accountDetail.getAccountName() : null);
//                    opportunityInfoQuoteDTO.setBillingAddress
//                            (accountDetail.getBillingInformation() != null ? accountDetail.getBillingInformation() : null);
//                    opportunityInfoQuoteDTO.setShippingAddress
//                            (accountDetail.getShippingInformation() != null ? accountDetail.getShippingInformation() : null);
//                }
//            }
//            return opportunityInfoQuoteDTO;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public Long createQuoteOpportunity(QuoteOpportunityDTO quoteOpportunityDTO) {
//        QuoteOpportunity quoteOpportunity = convert.convertQuoteOpportunityDtoToEntity(quoteOpportunityDTO);
//        if (quoteOpportunity != null) {
//
//            if (quoteOpportunityDTO.getBillingAddressInformation() != null) {
//                AddressInformation billingAddressInformation =
//                        convert.DTOToAddressInformation(quoteOpportunityDTO.getBillingAddressInformation());
//                billingAddressInformation = addressInformationRepository.save(billingAddressInformation);
//                quoteOpportunity.setBillingAddressInformation(billingAddressInformation);
//                quoteOpportunityDTO.getBillingAddressInformation().setAddressInformationId(billingAddressInformation.getAddressInformationId());
//            }
//
//            if (quoteOpportunityDTO.getShippingInformationId() != null ) {
//                AddressInformation shippingAddressInformation =
//                        convert.DTOToAddressInformation(quoteOpportunityDTO.getShippingInformationId());
//                shippingAddressInformation = addressInformationRepository.save(shippingAddressInformation);
//                quoteOpportunity.setShippingInformationId(shippingAddressInformation);
//                quoteOpportunityDTO.getShippingInformationId().setAddressInformationId(shippingAddressInformation.getAddressInformationId());
//            }
//            quoteOpportunity.setIsDelete(0);
//            log.info("The QuoteOpportunity entered is valid");
//
//            QuoteOpportunity quoteOpportunityNumber = quoteOpportunityRepository.save(quoteOpportunity);
//            String quoteNumber = generateContractNumber(quoteOpportunityNumber.getStatusQuoteOpportunityId()) ;
//            quoteOpportunityNumber.setQuoteNumber(quoteNumber);
//            quoteOpportunityRepository.save(quoteOpportunityNumber);
//            return quoteOpportunity.getStatusQuoteOpportunityId();
//        }
//        log.info("The order contract entered is null ");
//        return null;
//    }
//
//    @Override
//    public QuoteOpportunityReponse getDetailQuoteOpportunity(Long quoteOpportunityId) {
//        QuoteOpportunity quoteOpportunity = quoteOpportunityRepository.findById(quoteOpportunityId).orElse(null);
//        if (quoteOpportunity != null && quoteOpportunity.getIsDelete() == 0) {
//            log.info("Exist QuoteOpportunity in database");
//            QuoteOpportunityReponse quoteOpportunityReponse = convert.convertQuoteOpportunityEntityToDto(quoteOpportunity);
//            return quoteOpportunityReponse;
//        }
//        log.info("Not Exist QuoteOpportunity in database");
//        return null;
//    }
//
//    @Override
//    public PageResponse<?> getListQuote(int pageNo, int pageSize) {
//        try {
//            int page = 0;
//            if (pageNo > 0) {
//                page = pageNo - 1;
//            }
//            Pageable pageable = PageRequest.of(page, pageSize);
//
//            Page<QuoteOpportunity> orderContracts = quoteOpportunityRepository.findAllByIsDelete(0, pageable);
//
//            // Map to OrderContractResponse
//            Page<QuoteResponse> responsePage = orderContracts.map(this::mapToQuoteResponse);
//
//            return PageResponse.builder()
//                    .page(pageable.getPageNumber())
//                    .size(pageable.getPageSize())
//                    .total(responsePage.getTotalPages())
//                    .totalElements(responsePage.getTotalElements())
//                    .items(responsePage)
//                    .build();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
////    @Override
////    public Long AddListProductToQuote(List<QuoteOpportunityProductDTO> quoteOpportunityProductDTOS) {
////        try{
////            quoteOpportunityProductRepository.saveAll(convert.ConvertLOrderContractProduct(quoteOpportunityProductDTOS));
////            return orderContractProductDTOS.get(0).getOrderId();
////        }catch (Exception e){
////            new RuntimeException("There are not exist Order status");
////            return null;
////        }
////    }
//
//    @Override
//    public Long AddQuotePriceBookProduct(Long PriceBookId, Long quoteId) {
//        try{
//            ProcessingQuotePriceBook processingOrderPriceBook =
//                    ProcessingQuotePriceBook.builder().priceBookId(PriceBookId)
//                            .priceBookName(priceBookRepository.findById(PriceBookId).get().getPriceBookName())
//                            .quoteId(quoteId)
//                            .build();
//            ProcessingQuotePriceBook processingOrderPriceBookSave =
//                    processingQuotePriceBookRepository.save(processingOrderPriceBook);
//            return processingOrderPriceBookSave.getQuoteId();
//        }catch (Exception e){
//            new RuntimeException("There are not exist Order status");
//            return null;
//        }
//    }
//
//    @Override
//    public List<ListPriceBookeProductReponse> getListProductAddToQuote(Long quoteId) {
//        try{
//            ProcessingQuotePriceBook processingOrderPriceBook =
//                    processingQuotePriceBookRepository.GetProcessingQuotePriceBookByOrderId(quoteId);
//            List<ProductPriceBook> productPriceBookList =
//                    productPriceBookRepository.getProductPriceBookByPriceBookId(processingOrderPriceBook.getPriceBookId());
//
//            return convert.ConvertListPriceBookProductOrderReponse(productPriceBookList);
//        }catch (Exception e){
//            new RuntimeException("There are not exist Order status");
//            return null;
//        }
//    }
//
//    @Override
//    public Long AddListProductToQuote(List<QuoteProductDTO> QuoteProductDTOS) {
//        try{
//            quoteOpportunityProductRepository.saveAll(convert.ConvertQuoteProduct(QuoteProductDTOS));
//            return QuoteProductDTOS.get(0).getQuoteId();
//        }catch (Exception e){
//            new RuntimeException("There are not exist Order status");
//            return null;
//        }
//    }
//
//    @Override
//    public List<ProductPriceBookDTO> getListProductPriceBook() {
//        try{
//            List<ProductPriceBook> productPriceBookList = productPriceBookRepository.findAll();
//            return convert.ConvertListProductPriceBookDTOs(productPriceBookList);
//        }catch (Exception e){
//            throw new RuntimeException("There are not exist Order status");
//        }
//    }
//
//    @Override
//    public List<QuoteOpportunityProduct> getListQuoteProduct(Long quoteId) {
//        try{
//            List<QuoteOpportunityProduct> quoteOpportunityProducts = quoteOpportunityProductRepository.findByQuoteId(quoteId);
//            return quoteOpportunityProducts;
//        }catch (Exception e){
//            throw new RuntimeException("There are not exist Order status");
//        }
//    }
//
//
//    @Override
//    public ProcessingQuotePriceBook getProcessingQuotePriceBook(Long quoteId) {
//        try {
//            ProcessingQuotePriceBook processingOrderPriceBook =
//                    processingQuotePriceBookRepository.GetProcessingQuotePriceBookByOrderId(quoteId);
//            if (processingOrderPriceBook != null) {
//                log.info("Exist order Contract template in database");
//                return processingOrderPriceBook;
//            }
//            log.info("Not Exist order Contract template in database");
//            return null;
//        }
//        catch (Exception e){
//            throw  new RuntimeException("Can not get ProcessingOrderPriceBook");
//        }
//    }
//    @Override
//    public byte[] generateReport(Long quoteId) {
//        try {
//            // Bước 1: Lấy dữ liệu báo giá
//            QuoteReport quoteReport = convert.getQuoteReport(quoteId);
//            if (quoteReport == null) {
//                throw new RuntimeException("No data found for quoteId: " + quoteId);
//            }
//
//            List<QuoteProductResponse> quoteProductResponses = quoteReport.getQuoteProductResponses();
//            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(quoteProductResponses);
//
//            // Biên dịch báo cáo
//            JasperReport jasperReport = JasperCompileManager.compileReport(new ClassPathResource("Invoice.jrxml").getInputStream());
//
//            // Khai báo các tham số
//            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("prepareBy", quoteReport.getPrepareBy());
//            parameters.put("email", quoteReport.getEmail());
//            parameters.put("shipToName", quoteReport.getShipToName());
//            parameters.put("shipToNameStreet", quoteReport.getShipToNameStreet());
//            parameters.put("shipToNameCity", quoteReport.getShipToNameCity());
//            parameters.put("shipToNamePostalCode", quoteReport.getShipToNamePostalCode());
//            parameters.put("shipToNameCountry", quoteReport.getShipToNameCountry());
//            parameters.put("billToName", quoteReport.getBillToName());
//            parameters.put("billToNameStreet", quoteReport.getBillToNameStreet());
//            parameters.put("billToNameCity", quoteReport.getBillToNameCity());
//            parameters.put("billToNamePostalCode", quoteReport.getBillToNamePostalCode());
//            parameters.put("billToNameCountry", quoteReport.getBillToNameCountry());
//            parameters.put("createDate", quoteReport.getCreateDate());
//            parameters.put("quoteNumber", quoteReport.getQuoteNumber());
//            parameters.put("subTotal", quoteReport.getSubTotal());
//            parameters.put("discount", quoteReport.getDiscount());
//            parameters.put("totalPrice", quoteReport.getTotalPrice());
//            parameters.put("grandTotal", quoteReport.getGrandTotal());
//
//            // Tạo đối tượng JasperPrint
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
//
//            // Tạo danh sách JasperPrint
//            List<JasperPrint> jasperPrintList = new ArrayList<>();
//            jasperPrintList.add(jasperPrint);
//
//            // Xuất báo cáo
//            return generateCombinedReport(jasperPrintList);
//
//        } catch (JRException | IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error generating report", e);
//        }
//    }
//    public byte[] generateCombinedReport(List<JasperPrint> jasperPrintList) {
//        try {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//            JRExporter exporter = new JRPdfExporter();
//            exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
//            exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, stream);
//
//            exporter.exportReport();
//            return stream.toByteArray();
//
//        } catch (JRException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error generating combined PDF report", e);
//        }
//    }
//
//    public String generateContractNumber(Long id) {
//        int length = Math.max(MINIMUM_LENGTH, String.valueOf(id).length());
//        return String.format("%0" + length + "d", id);
//    }
//
//    private QuoteResponse mapToQuoteResponse(QuoteOpportunity quoteOpportunity) {
//        QuoteResponse response = QuoteResponse.builder()
//                .id(quoteOpportunity.getId())
//                .quoteNumber(quoteOpportunity.getQuoteNumber())
//                .quoteName(quoteOpportunity.getQuoteName())
//                .expirationDate(quoteOpportunity.getExpirationDate())
//                .statusQuoteOpportunityId(quoteOpportunity.getStatusQuoteOpportunityId())
//                .description(quoteOpportunity.getDescription())
//                .accountName(quoteOpportunity.getAccountName())
//                .opportunityName(quoteOpportunity.getOpportunityName())
//                .build();
//
//        // Calculate Total by summing totalPrice of all related QuoteOpportunityProducts
//        List<QuoteOpportunityProduct> products = quoteOpportunityProductRepository.findByQuoteId(quoteOpportunity.getId());
//        Double total = products.stream().mapToDouble(QuoteOpportunityProduct::getTotalPrice).sum();
//        response.setTotal(total);
//
//        return response;
//    }
//    @Override
//    public List<QuoteOpportunity> getListQuoteOpportunityByOpportunityId(Long opportunityId, int pageNo, int pageSize) {
//        if (opportunityId == null) {
//            log.error("OpportunityId is null");
//            return List.of();
//        }
//        try {
//            Pageable pageable = PageRequest.of(pageNo, pageSize);
//            Page<QuoteOpportunity> quoteOpportunities = quoteOpportunityRepository.findByOpportunityIdAndIsDelete(opportunityId, 0,pageable);
//            return quoteOpportunities.getContent();
//        } catch (Exception e) {
//            log.error("Error retrieving QuoteOpportunity by OpportunityId: {}", opportunityId, e);
//            return List.of();
//        }
//    }
//
//    @Override
//    public boolean deleteQuoteOpportunity(Long id) {
//        Optional<QuoteOpportunity> quoteOpportunityOptional = quoteOpportunityRepository.findById(id);
//        if (quoteOpportunityOptional.isPresent()) {
//            QuoteOpportunity quoteOpportunity = quoteOpportunityOptional.get();
//            quoteOpportunity.setIsDelete(1);
//            quoteOpportunityRepository.save(quoteOpportunity);
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean updateQuoteOpportunity(QuoteOpportunityDTO quoteOpportunityDTO, Long id) {
//        Optional<QuoteOpportunity> quoteOpportunityOptional = quoteOpportunityRepository.findById(id);
//        if (quoteOpportunityOptional.isPresent()) {
//            QuoteOpportunity quoteOpportunity = quoteOpportunityOptional.get();
//
//            if (quoteOpportunityDTO.getQuoteName() != null) {
//                quoteOpportunity.setQuoteName(quoteOpportunityDTO.getQuoteName());
//            }
//            if (quoteOpportunityDTO.getExpirationDate() != null) {
//                quoteOpportunity.setExpirationDate(quoteOpportunityDTO.getExpirationDate());
//            }
//            if (quoteOpportunityDTO.getStatusQuoteOpportunityId() != null) {
//                quoteOpportunity.setStatusQuoteOpportunityId(quoteOpportunityDTO.getStatusQuoteOpportunityId());
//            }
//            if (quoteOpportunityDTO.getDescription() != null) {
//                quoteOpportunity.setDescription(quoteOpportunityDTO.getDescription());
//            }
//            if (quoteOpportunityDTO.getAccountName() != null) {
//                quoteOpportunity.setAccountName(quoteOpportunityDTO.getAccountName());
//            }
//            if (quoteOpportunityDTO.getOpportunityName() != null) {
//                quoteOpportunity.setOpportunityName(quoteOpportunityDTO.getOpportunityName());
//            }
//
//            quoteOpportunityRepository.save(quoteOpportunity);
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean updateQuoteOpportunityProduct(QuoteProductDTO quoteProductDTO, Long id) {
//        Optional<QuoteOpportunityProduct> quoteOpportunityProductOptional = quoteOpportunityProductRepository.findById(id);
//        if (quoteOpportunityProductOptional.isPresent()) {
//            QuoteOpportunityProduct quoteOpportunityProduct = quoteOpportunityProductOptional.get();
//
//            if (quoteProductDTO.getProductId() != null) {
//                quoteOpportunityProduct.setProductId(quoteProductDTO.getProductId());
//            }
//            if (quoteProductDTO.getProductName() != null) {
//                quoteOpportunityProduct.setProductName(quoteProductDTO.getProductName());
//            }
//            if (quoteProductDTO.getQuantity() != 0) {
//                quoteOpportunityProduct.setQuantity(quoteProductDTO.getQuantity());
//            }
//            if (quoteProductDTO.getUnitPrice() != null) {
//                quoteOpportunityProduct.setUnitPrice(quoteProductDTO.getUnitPrice());
//            }
//            if (quoteProductDTO.getTotalPrice() != null) {
//                quoteOpportunityProduct.setTotalPrice(quoteProductDTO.getTotalPrice());
//            }
//            if (quoteProductDTO.getListPrice() != null) {
//                quoteOpportunityProduct.setListPrice(quoteProductDTO.getListPrice());
//            }
//            if (quoteProductDTO.getCreateBy() != null) {
//                quoteOpportunityProduct.setCreateBy(quoteProductDTO.getCreateBy());
//            }
//            if (quoteProductDTO.getDescription() != null) {
//                quoteOpportunityProduct.setDescription(quoteProductDTO.getDescription());
//            }
//
//            quoteOpportunityProductRepository.save(quoteOpportunityProduct);
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean deleteQuoteOpportunityProduct(Long id) {
//        Optional<QuoteOpportunityProduct> quoteOpportunityProductOptional = quoteOpportunityProductRepository.findById(id);
//        if (quoteOpportunityProductOptional.isPresent()) {
//            QuoteOpportunityProduct quoteOpportunityProduct = quoteOpportunityProductOptional.get();
//            quoteOpportunityProductRepository.delete(quoteOpportunityProduct);
//            return true;
//        }
//        return false;
//    }
//
//}
