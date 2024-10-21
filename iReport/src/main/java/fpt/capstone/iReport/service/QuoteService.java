//package fpt.capstone.iReport.service;
//
//import fpt.capstone.iReport.dto.ProcessingQuotePriceBook;
//import fpt.capstone.iReport.dto.request.*;
//import fpt.capstone.iReport.dto.response.ListPriceBookeProductReponse;
//import fpt.capstone.iReport.dto.response.PageResponse;
//import fpt.capstone.iReport.dto.response.QuoteOpportunityReponse;
//import fpt.capstone.iReport.model.ProcessingOrderPriceBook;
//import fpt.capstone.iReport.model.Product;
//import fpt.capstone.iReport.model.QuoteOpportunity;
//import fpt.capstone.iReport.model.QuoteOpportunityProduct;
//
//import java.util.List;
//
//public interface QuoteService {
//    OpportunityInfoQuoteDTO getOpportunityInfoQuoteDTO(Long oppportunityId);
//    Long createQuoteOpportunity(QuoteOpportunityDTO quoteOpportunityDTO);
//    QuoteOpportunityReponse getDetailQuoteOpportunity(Long quoteOpportunityId);
//    PageResponse<?> getListQuote(int pageNo, int pageSize);
//    Long AddQuotePriceBookProduct(Long PriceBookId, Long quoteId);
//    List<ListPriceBookeProductReponse> getListProductAddToQuote(Long quoteId);
//    Long AddListProductToQuote(List<QuoteProductDTO> QuoteProductDTOS);
//    List<ProductPriceBookDTO> getListProductPriceBook();
//    List<QuoteOpportunityProduct> getListQuoteProduct(Long quoteId);
//    ProcessingQuotePriceBook getProcessingQuotePriceBook(Long quoteId);
//    byte[] generateReport(Long quoteId);
//    List<QuoteOpportunity> getListQuoteOpportunityByOpportunityId(Long opportunityId, int pageNo, int pageSize);
//    boolean deleteQuoteOpportunity(Long id);
//    boolean updateQuoteOpportunity(QuoteOpportunityDTO quoteOpportunityDTO, Long id);
//    boolean updateQuoteOpportunityProduct(QuoteProductDTO quoteProductDTO, Long id);
//    boolean deleteQuoteOpportunityProduct(Long id);
//
//}
