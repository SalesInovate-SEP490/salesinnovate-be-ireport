package fpt.capstone.iReport.service;

import fpt.capstone.iReport.dto.request.*;
import fpt.capstone.iReport.dto.response.FileDetailResponse;
import fpt.capstone.iReport.dto.response.PageResponse;
import fpt.capstone.iReport.dto.response.ListPriceBookeProductReponse;
import fpt.capstone.iReport.model.*;

import java.io.File;
import java.util.List;

public interface ContractService {
    Long createContract(ContractDTO contractDTO);
    Long updateContract(ContractDTO contractDTO, long id);
    boolean deleteContract(Long id);
    PageResponse<?> getListContract( int pageNo, int pageSize);
    ContractDTO getDetailContract(Long contractId);
    List<OrderStatus> getListOrderStatus();
    Long createOrderContract(OrderContractDTO orderContractDTO);
    Long updateOrderContract(OrderContractDTO orderContractDTO, long id);
    boolean deleteOrderContract(Long id);
    PageResponse<?> getListOrderContract( int pageNo, int pageSize);
    OrderContractDTO getDetailOrderContract(Long contractId);
    List<String> getContractNumber();
    List<AccountContractDTO> getAccountName();
    List<ContractStatus> getListContractStatus();
    List<ProductPriceBookDTO> getListProductPriceBook();
    Long AddPriceBookProduct(Long PriceBookId, Long orderId);
    List<ListPriceBookeProductReponse> getListProductAddToOrder(Long OrderId);
    Long AddListProductToOrder(List<OrderContractProductDTO> orderContractProductDTOS);
    List<OrderContractProduct> getListOrderContractProduct(Long orderId);
    OrderContractProduct getDetailOrderContractProduct(Long orderContractProductId);
    Long editOrderContractProduct(Long id , OrderContractProductDTO orderContractProductDTO);
    List<OrderContract> getListOrderContractByContractNumber(String contractNumber);
    ProcessingOrderPriceBook getDetailProcessingOrderPriceBook(Long id);
    boolean deleteOrderContractProduct(Long id);
    boolean updateOrderContact(OrderContractDTO orderContractDTO, Long id);
    boolean updateOrderContactProduct(OrderContractProductDTO orderContractProductDTO, Long id);

    ContractFile uploadFile(File file, String fileName, Long ContractId);
    FileDetailResponse getFileFromDrive(String fileId);
    ContractFile getFileDetailFromContract(Long ContractId);
    boolean deleteFile(Long fileShareId);
}
