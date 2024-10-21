//package fpt.capstone.iReport.controller;
//
//import fpt.capstone.iReport.dto.ProcessingQuotePriceBook;
//import fpt.capstone.iReport.dto.request.ContractDTO;
//import fpt.capstone.iReport.dto.request.OrderContractProductDTO;
//import fpt.capstone.iReport.dto.request.QuoteOpportunityDTO;
//import fpt.capstone.iReport.dto.request.QuoteProductDTO;
//import fpt.capstone.iReport.dto.response.QuoteOpportunityReponse;
//import fpt.capstone.iReport.dto.response.ResponseData;
//import fpt.capstone.iReport.dto.response.ResponseError;
//import fpt.capstone.iReport.model.ProcessingOrderPriceBook;
//import fpt.capstone.iReport.service.ContractService;
//import fpt.capstone.iReport.service.QuoteService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.URLEncoder;
//import java.util.List;
//
//@Slf4j
//@RestController
//@AllArgsConstructor
//@RequestMapping("/api/quote")
//public class QuoteController {
//    @Autowired
//    private final QuoteService quoteService;
//    @PostMapping("/create-quote-opportunity")
//    public ResponseData<?> createQuoteOpportunity(
//            @RequestBody QuoteOpportunityDTO quoteOpportunityDTO
//    ) {
//        try {
//            Long EmailId = quoteService.createQuoteOpportunity(quoteOpportunityDTO);
//            return new ResponseData<>(HttpStatus.OK.value(), "Create quoteOpportunity success", EmailId, 1);
//        } catch (Exception e) {
//            return new ResponseError(0, HttpStatus.BAD_REQUEST.value(), e.getMessage());
//        }
//    }
//    @GetMapping("/get-opportunity-info-for-quote/{opportunityId}")
//    public ResponseData<?> getOpportunityInfoQuoteDTO(
//            @PathVariable(name = "opportunityId") Long opportunityId
//    ) {
//        try {
//            return new ResponseData<>(1, HttpStatus.OK.value(),
//                    quoteService.getOpportunityInfoQuoteDTO(opportunityId));
//        } catch (Exception e) {
//            return new ResponseError(0,
//                    HttpStatus.BAD_REQUEST.value(), "Get list mail failed");
//        }
//    }
//
//    @GetMapping("/detail/{id}")
//    public ResponseData<?> detailQuoteOpportunity(
//            @PathVariable(name = "id") Long id
//    ) {
//        try {
//            QuoteOpportunityReponse quoteOpportunityReponse =
//                    quoteService.getDetailQuoteOpportunity(id);
//            if(quoteOpportunityReponse != null)
//                return new ResponseData<>(1, HttpStatus
//                        .OK.value(), quoteOpportunityReponse);
//            return new ResponseData<>(1, HttpStatus
//                    .NOT_FOUND.value(), "Not found contract teamplate");
//        } catch (Exception e) {
//            return new ResponseError(0,
//                    HttpStatus.BAD_REQUEST.value(), e.getMessage());
//        }
//    }
//    @GetMapping("/get-list-product-add-quote")
//    public ResponseData<?> getListProductAddToQuote(
//            @RequestParam(value = "quoteId") Long quoteId
//    ) {
//        try {
//            return new ResponseData<>(1, HttpStatus.OK.value(),
//                    quoteService.getListProductAddToQuote(quoteId));
//        } catch (Exception e) {
//            return new ResponseError(0,
//                    HttpStatus.BAD_REQUEST.value(), "Get list get-list-product-add-order failed");
//        }
//    }
//    @PostMapping("/add-price-book-quote-to-quote")
//    public ResponseData<?> addPriceBookProductToQuote(
//            @RequestParam(value = "PriceBookId") Long PriceBookId,
//            @RequestParam(value = "quoteId") Long quoteId
//    ) {
//        try {
//            Long orderContract = quoteService.AddQuotePriceBookProduct(PriceBookId,quoteId);
//            return new ResponseData<>(HttpStatus.OK.value(), "Create price book product success", orderContract, 1);
//        } catch (Exception e) {
//            return new ResponseError(0, HttpStatus.BAD_REQUEST.value(), e.getMessage());
//        }
//    }
//
//    @PostMapping("/add-list-product-quote")
//    public ResponseData<?> addListProductToOrder(
//            @RequestBody List<QuoteProductDTO> QuoteProductDTOS
//    ) {
//        try {
//            Long orderContract = quoteService.AddListProductToQuote(QuoteProductDTOS);
//            return new ResponseData<>(HttpStatus.OK.value(), "Create price book product success", orderContract, 1);
//        } catch (Exception e) {
//            return new ResponseError(0, HttpStatus.BAD_REQUEST.value(), e.getMessage());
//        }
//    }
//    @GetMapping("/get-list-product-price-book")
//    public ResponseData<?> getListProductPriceBook(
//    ) {
//        try {
//            return new ResponseData<>(1, HttpStatus.OK.value(),
//                    quoteService.getListProductPriceBook());
//        } catch (Exception e) {
//            return new ResponseError(0,
//                    HttpStatus.BAD_REQUEST.value(), "Get list product-price-book failed");
//        }
//    }
//
//    @GetMapping("/get-list-quote-product/{quoteId}")
//    public ResponseData<?> getListQuoteProduct(
//            @PathVariable(name = "quoteId") Long quoteId
//    ) {
//        try {
//            return new ResponseData<>(1, HttpStatus.OK.value(),
//                    quoteService.getListQuoteProduct(quoteId));
//        } catch (Exception e) {
//            return new ResponseError(0,
//                    HttpStatus.BAD_REQUEST.value(), "Get list product-price-book failed");
//        }
//    }
//
//    @GetMapping("/get-list-quote-opportunity")
//    public ResponseData<?> getListOrderContract(
//            @RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
//            @RequestParam(value = "perPage", defaultValue = "10") int perPage
//    ) {
//        try {
//            return new ResponseData<>(1, HttpStatus.OK.value(),
//                    quoteService.getListQuote(currentPage, perPage));
//        } catch (Exception e) {
//            return new ResponseError(0,
//                    HttpStatus.BAD_REQUEST.value(), "Get list order failed");
//        }
//    }
//
//    @GetMapping("/get-report-quote-opportunity/{quoteId}")
//    public ResponseEntity<byte[]> downloadFileFromDrive(@PathVariable Long quoteId) {
//        try {
//            byte[] bytes = quoteService.generateReport(quoteId);
//            String fileName = "Quote_export.pdf";
//            String fileNameURL = URLEncoder.encode(fileName, "UTF-8");
//            ByteArrayResource resource = new ByteArrayResource(bytes);
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Disposition", "attachment; filename*=UTF-8''" + fileNameURL);
//            headers.add("Content-Type", "application/pdf");
//                return ResponseEntity.ok()
//                        .headers(headers)
//                        .body(bytes);
//        }catch (Exception e){
//            throw new RuntimeException("Could not generate report!", e);
//        }
//    }
//    @GetMapping("/processing-quote-price-book-detail/{quoteId}")
//    public ResponseData<?> ProcessingQuotePriceBook(
//            @PathVariable(name = "quoteId") Long quoteId
//    ) {
//        try {
//            ProcessingQuotePriceBook processingOrderPriceBook =
//                    quoteService.getProcessingQuotePriceBook(quoteId);
//            if(processingOrderPriceBook != null)
//                return new ResponseData<>(1, HttpStatus
//                        .OK.value(), processingOrderPriceBook);
//            return new ResponseData<>(0, HttpStatus
//                    .NOT_FOUND.value(), "Not found order contract");
//        } catch (Exception e) {
//            return new ResponseError(0,
//                    HttpStatus.BAD_REQUEST.value(), e.getMessage());
//        }
//    }
//
//    @GetMapping("/get-list-quote-opportunity/{oppId}")
//    public ResponseData<?> getListQuoteByOppId(
//            @RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
//            @RequestParam(value = "perPage", defaultValue = "10") int perPage,
//            @PathVariable(name = "oppId") Long oppId
//    ) {
//        try {
//            return new ResponseData<>(1, HttpStatus.OK.value(),
//                    quoteService.getListQuoteOpportunityByOpportunityId(oppId,currentPage, perPage));
//        } catch (Exception e) {
//            return new ResponseError(0,
//                    HttpStatus.BAD_REQUEST.value(), "Get list order failed");
//        }
//    }
//
//    @PatchMapping("/update-order-product-contract-/{id}")
//    public ResponseData<?> updateOrderContactProduct(
//            @PathVariable(name = "id") Long id,
//            @RequestBody QuoteOpportunityDTO quoteOpportunityDTO
//    ) {
//        try {
//            boolean Contract = quoteService.updateQuoteOpportunity(quoteOpportunityDTO, id);
//            return new ResponseData<>(HttpStatus.OK.value(), "Update Contract success", Contract, 1);
//        } catch (Exception e) {
//            return new ResponseError(0, HttpStatus.BAD_REQUEST.value(), e.getMessage());
//        }
//    }
//
//    @PatchMapping("/update-quote-product/{id}")
//    public ResponseData<?> updateQuoteProduct(
//            @PathVariable(name = "id") Long id,
//            @RequestBody QuoteProductDTO quoteProductDTO
//    ) {
//        try {
//            boolean Contract = quoteService.updateQuoteOpportunityProduct(quoteProductDTO, id);
//            return new ResponseData<>(HttpStatus.OK.value(), "Update Contract success", Contract, 1);
//        } catch (Exception e) {
//            return new ResponseError(0, HttpStatus.BAD_REQUEST.value(), e.getMessage());
//        }
//    }
//
//    @DeleteMapping("/delete-quote-opportunity/{id}")
//    public ResponseData<?> deleteQuote(
//            @PathVariable(name = "id") Long id
//    ) {
//        try {
//            quoteService.deleteQuoteOpportunity(id);
//            return new ResponseData<>(HttpStatus
//                    .OK.value(), "Delete Quote success", 1);
//        } catch (Exception e) {
//            return new ResponseError(0,
//                    HttpStatus.BAD_REQUEST.value(), e.getMessage());
//        }
//    }
//
//    @DeleteMapping("/delete-quote-product/{id}")
//    public ResponseData<?> deleteQuoteProduct(
//            @PathVariable(name = "id") Long id
//    ) {
//        try {
//            quoteService.deleteQuoteOpportunityProduct(id);
//            return new ResponseData<>(HttpStatus
//                    .OK.value(), "Delete Quote product success", 1);
//        } catch (Exception e) {
//            return new ResponseError(0,
//                    HttpStatus.BAD_REQUEST.value(), e.getMessage());
//        }
//    }
//
//}
