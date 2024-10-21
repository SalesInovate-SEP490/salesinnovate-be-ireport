package fpt.capstone.iReport.controller;

import fpt.capstone.iReport.dto.request.ContractDTO;
import fpt.capstone.iReport.dto.request.OrderContractDTO;
import fpt.capstone.iReport.dto.request.OrderContractProductDTO;
import fpt.capstone.iReport.dto.response.FileDetailResponse;
import fpt.capstone.iReport.dto.response.ResponseData;
import fpt.capstone.iReport.dto.response.ResponseError;
import fpt.capstone.iReport.model.ContractFile;
import fpt.capstone.iReport.model.ProcessingOrderPriceBook;
import fpt.capstone.iReport.service.ContractService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/contract")
public class ContractController {
    @Autowired
    private final ContractService contractService;
    @PostMapping("/create-contract-template")
    public ResponseData<?> createContract(
            @RequestBody ContractDTO contractDTO
    ) {
        try {
            Long contract = contractService.createContract(contractDTO);
            return new ResponseData<>(HttpStatus.OK.value(), "Create contract success", contract, 1);
        } catch (Exception e) {
            return new ResponseError(0, HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PatchMapping("/update-contract-template/{id}")
    public ResponseData<?> updateContractDTO(
            @PathVariable(name = "id") Long id,
            @RequestBody ContractDTO contractDTO
    ) {
        try {
            Long Contract = contractService.updateContract(contractDTO, id);
            return new ResponseData<>(HttpStatus.OK.value(), "Update Contract success", Contract, 1);
        } catch (Exception e) {
            return new ResponseError(0, HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseData<?> deleteContract(
            @PathVariable(name = "id") Long id
    ) {
        try {
            contractService.deleteContract(id);
            return new ResponseData<>(HttpStatus
                    .OK.value(), "Delete contract  success", 1);
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @GetMapping("/get-list-contract")
    public ResponseData<?> getListContract(
            @RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
            @RequestParam(value = "perPage", defaultValue = "10") int perPage
    ) {
        try {
            return new ResponseData<>(1, HttpStatus.OK.value(),
                    contractService.getListContract(currentPage, perPage));
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), "Get list contract failed");
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseData<?> detailContract(
            @PathVariable(name = "id") Long id
    ) {
        try {
            ContractDTO contractDTO =
                    contractService.getDetailContract(id);
            if(contractDTO != null)
                return new ResponseData<>(1, HttpStatus
                        .OK.value(), contractDTO);
            return new ResponseData<>(1, HttpStatus
                    .NOT_FOUND.value(), "Not found contract teamplate");
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @PostMapping("/create-order-contract-template")
    public ResponseData<?> createOrderContractDTO(
            @RequestBody OrderContractDTO orderContractDTO
    ) {
        try {
            Long orderContract = contractService.createOrderContract(orderContractDTO);
            return new ResponseData<>(HttpStatus.OK.value(), "Create order ContractDTO success", orderContract, 1);
        } catch (Exception e) {
            return new ResponseError(0, HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PatchMapping("/update-order-contract-template/{id}")
    public ResponseData<?> updateOrderContractDTO(
            @PathVariable(name = "id") Long id,
            @RequestBody OrderContractDTO orderContractDTO
    ) {
        try {
            Long orderContractId = contractService.updateOrderContract(orderContractDTO, id);
            return new ResponseData<>(HttpStatus.OK.value(), "update Order Contract success", orderContractId, 1);
        } catch (Exception e) {
            return new ResponseError(0, HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }

    }

    @DeleteMapping("/delete-order-contract/{id}")
    public ResponseData<?> deleteOrderContractDTO(
            @PathVariable(name = "id") Long id
    ) {
        try {
            contractService.deleteOrderContract(id);
            return new ResponseData<>(HttpStatus
                    .OK.value(), "Delete order Contract success", 1);
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @GetMapping("/get-list-order-contract")
    public ResponseData<?> getListOrderContract(
            @RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
            @RequestParam(value = "perPage", defaultValue = "10") int perPage
    ) {
        try {
            return new ResponseData<>(1, HttpStatus.OK.value(),
                    contractService.getListOrderContract(currentPage, perPage));
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), "Get list order failed");
        }
    }

    @GetMapping("/order-contract-detail/{orderId}")
    public ResponseData<?> detailOrderContract(
            @PathVariable(name = "orderId") Long orderId
    ) {
        try {
            OrderContractDTO orderContractDTO =
                    contractService.getDetailOrderContract(orderId);
            if(orderContractDTO != null)
                return new ResponseData<>(1, HttpStatus
                        .OK.value(), orderContractDTO);
            return new ResponseData<>(1, HttpStatus
                    .NOT_FOUND.value(), "Not found order contract");
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @GetMapping("/get-list-order-status")
    public ResponseData<?> getListOrderStatus(
    ) {
        try {
            return new ResponseData<>(1, HttpStatus.OK.value(),
                    contractService.getListOrderStatus());
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), "Get list order Status failed");
        }
    }
    @GetMapping("/get-list-contract-number")
    public ResponseData<?> getListContractNumber() {
        try {
            return new ResponseData<>(1, HttpStatus.OK.value(),
                    contractService.getContractNumber());
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), "Get list ContractNumber failed");
        }
    }
    @GetMapping("/get-list-account-name")
    public ResponseData<?> getListAccountName() {
        try {
            return new ResponseData<>(1, HttpStatus.OK.value(),
                    contractService.getAccountName());
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), "Get list Account Name failed");
        }
    }
    @GetMapping("/get-list-contract-status")
    public ResponseData<?> getListContractStatus(
    ) {
        try {
            return new ResponseData<>(1, HttpStatus.OK.value(),
                    contractService.getListContractStatus());
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), "Get list order Status failed");
        }
    }
    @GetMapping("/get-list-product-price-book")
    public ResponseData<?> getListProductPriceBook(
    ) {
        try {
            return new ResponseData<>(1, HttpStatus.OK.value(),
                    contractService.getListProductPriceBook());
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), "Get list product-price-book failed");
        }
    }

    @PostMapping("/create-price-book-product")
    public ResponseData<?> createPriceBookProduct(
            @RequestParam(value = "PriceBookId") Long PriceBookId,
            @RequestParam(value = "orderId") Long orderId
    ) {
        try {
            Long orderContract = contractService.AddPriceBookProduct(PriceBookId,orderId);
            return new ResponseData<>(HttpStatus.OK.value(), "Create price book product success", orderContract, 1);
        } catch (Exception e) {
            return new ResponseError(0, HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PostMapping("/create-list-product-oder")
    public ResponseData<?> createListProductToOrder(
            @RequestBody List<OrderContractProductDTO> orderContractProductDTOS
    ) {
        try {
            Long orderContract = contractService.AddListProductToOrder(orderContractProductDTOS);
            return new ResponseData<>(HttpStatus.OK.value(), "Create price book product success", orderContract, 1);
        } catch (Exception e) {
            return new ResponseError(0, HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @GetMapping("/get-list-product-add-order")
    public ResponseData<?> getListProductAddToOrder(
            @RequestParam(value = "OrderId") Long OrderId
    ) {
        try {
            return new ResponseData<>(1, HttpStatus.OK.value(),
                    contractService.getListProductAddToOrder(OrderId));
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), "Get list get-list-product-add-order failed");
        }
    }

    @GetMapping("/get-list-order-contract-product")
    public ResponseData<?> getListOrderContractProduct(
            @RequestParam(value = "OrderId") Long OrderId
    ) {
        try {
            return new ResponseData<>(1, HttpStatus.OK.value(),
                    contractService.getListOrderContractProduct(OrderId));
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), "Get list get-list-order-contract-product failed");
        }
    }

    @GetMapping("/get-detail-order-contract-product")
    public ResponseData<?> getDetailOrderContractProduct(
            @RequestParam(value = "orderContractProductId") Long orderContractProductId
    ) {
        try {
            return new ResponseData<>(1, HttpStatus.OK.value(),
                    contractService.getDetailOrderContractProduct(orderContractProductId));
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), "Get list get-detail-order-contract-product failed");
        }
    }

    @GetMapping("/get-list-order-contract-by/{contractNumber}")
    public ResponseData<?> getListOrderContractByContractNumber(
            @PathVariable(value = "contractNumber") String contractNumber
    ) {
        try {
            return new ResponseData<>(1, HttpStatus.OK.value(),
                    contractService.getListOrderContractByContractNumber(contractNumber));
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), "Get list get-list-order-contract-product failed");
        }
    }

    @DeleteMapping("/delete-order-product/{id}")
    public ResponseData<?> deleteOrderContractProduct(
            @PathVariable(name = "id") Long id
    ) {
        try {
            contractService.deleteOrderContractProduct(id);
            return new ResponseData<>(HttpStatus
                    .OK.value(), "Delete Delete Order COntract product success", 1);
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @GetMapping("/processing-order-contract-detail/{orderId}")
    public ResponseData<?> ProcessingOrderContract(
            @PathVariable(name = "orderId") Long orderId
    ) {
        try {
            ProcessingOrderPriceBook processingOrderPriceBook =
                    contractService.getDetailProcessingOrderPriceBook(orderId);
            if(processingOrderPriceBook != null)
                return new ResponseData<>(1, HttpStatus
                        .OK.value(), processingOrderPriceBook);
            return new ResponseData<>(0, HttpStatus
                    .NOT_FOUND.value(), "Not found order contract");
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @PatchMapping("/update-order-contract-/{id}")
    public ResponseData<?> updateOrderContract(
            @PathVariable(name = "id") Long id,
            @RequestBody OrderContractDTO contractDTO
    ) {
        try {
            Boolean contract = contractService.updateOrderContact(contractDTO, id);
            return new ResponseData<>(HttpStatus.OK.value(), "Update Contract success", contract, 1);
        } catch (Exception e) {
            return new ResponseError(0, HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }

    }

    @PatchMapping("/update-order-product-contract-/{id}")
    public ResponseData<?> updateOrderContactProduct(
            @PathVariable(name = "id") Long id,
            @RequestBody OrderContractProductDTO contractDTO
    ) {
        try {
            boolean Contract = contractService.updateOrderContactProduct(contractDTO, id);
            return new ResponseData<>(HttpStatus.OK.value(), "Update Contract success", Contract, 1);
        } catch (Exception e) {
            return new ResponseError(0, HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PostMapping("/upload-contract-file")
    public ResponseData<?> uploadContractFile(
            @RequestParam(value  = "ContractId") Long ContractId,
            @RequestParam(value  = "file", required = false) MultipartFile file)
    {
        try{
            String fileName = file.getOriginalFilename();
            File templFile = File.createTempFile(file.getOriginalFilename(), null);
            file.transferTo(templFile);
            ContractFile fileResponse = contractService.uploadFile(templFile,fileName,ContractId);
            return new ResponseData<>(HttpStatus.OK.value(),"Upload file success", fileResponse,1);
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), "Upload file failed error: " +e.getMessage());
        }
    }

    @GetMapping("/down-contract-files/{fileId}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileId) {
        FileDetailResponse fileDetailResponse = contractService.getFileFromDrive(fileId);
        if (fileDetailResponse != null) {
            ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();
            if (fileDetailResponse.getContentType() != null) {
                responseBuilder.header(HttpHeaders.CONTENT_TYPE, fileDetailResponse.getContentType());
            }
            return responseBuilder.body(fileDetailResponse.getResource());

        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/get-contract-file/{contractId}")
    public ResponseData<?> getContractFile(
            @PathVariable Long contractId) {
        try {
            return new ResponseData<>(1, HttpStatus.OK.value(),
                    contractService.getFileDetailFromContract(contractId));
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), "Get contract file failed");
        }
    }
    @DeleteMapping("/delete-file/{id}")
    public ResponseData<?> deleteFile(
            @PathVariable(name = "id") Long id
    ) {
        try {
            boolean isDelete = contractService.deleteFile(id);
            if(isDelete == true){
                return new ResponseData<>(HttpStatus
                        .OK.value(), "Delete File success", 1);
            }
            return new ResponseData<>(HttpStatus
                    .NOT_FOUND.value(), "Not exist File", 0);
        } catch (Exception e) {
            return new ResponseError(0,
                    HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
