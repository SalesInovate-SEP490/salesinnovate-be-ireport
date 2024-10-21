package fpt.capstone.iReport.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import fpt.capstone.iReport.dto.Convert;
import fpt.capstone.iReport.dto.request.*;
import fpt.capstone.iReport.dto.response.FileDetailResponse;
import fpt.capstone.iReport.dto.response.OrderContractResponse;
import fpt.capstone.iReport.dto.response.PageResponse;
import fpt.capstone.iReport.dto.response.ListPriceBookeProductReponse;
import fpt.capstone.iReport.model.*;
import fpt.capstone.iReport.model.accounts.Account;
import fpt.capstone.iReport.repository.*;
import fpt.capstone.iReport.repository.account.AccountRepository;
import fpt.capstone.iReport.service.ContractService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ContractServiceImpl implements ContractService {
    private final Convert convert;
    private final ContractRepository contractRepository;
    private final AddressInformationRepository addressInformationRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderContractRepository orderContractRepository;
    private static final int MINIMUM_LENGTH = 10;
    private final AccountRepository accountRepository;
    private final ContractStatusRepository contractStatusRepository;
    private final ProductPriceBookRepository productPriceBookRepository;
    private final ProcessingOrderPriceBookRepository processingOrderPriceBookRepository;
    private final PriceBookRepository priceBookRepository;
    private final OrderContractProductRepository orderContractProductRepository;
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_KEY_PATH = getPathToGoodleCredentials();
    private final ContractFileRepository contractFileRepository;
    @Override
    public Long createContract(ContractDTO contractDTO) {
        Contract contract = convert.convertContractDtoToEntity(contractDTO);
        if (contract != null) {

            if (contractDTO.getBillingAddressId() != null) {
                AddressInformation billingAddressInformation = convert.DTOToAddressInformation(contractDTO.getBillingAddressId());
                billingAddressInformation = addressInformationRepository.save(billingAddressInformation);
                contract.setBillingInformation(billingAddressInformation);
                contractDTO.getBillingAddressId().setAddressInformationId(billingAddressInformation.getAddressInformationId());
            }

            if (contractDTO.getShippingAddressId() != null) {
                AddressInformation shippingAddressInformation = convert.DTOToAddressInformation(contractDTO.getShippingAddressId());
                shippingAddressInformation = addressInformationRepository.save(shippingAddressInformation);
                contract.setShippingInformation(shippingAddressInformation);
                contractDTO.getShippingAddressId().setAddressInformationId(shippingAddressInformation.getAddressInformationId());
            }
            log.info("The contract entered is valid");
            contract.setIsDelete(0);
            Contract saveContract = contractRepository.save(contract);
            String contractNumber = generateContractNumber(saveContract.getContractId());
            saveContract.setContractNumber(contractNumber);
            contractRepository.save(saveContract);
            return contract.getContractId();
        }
        log.info("The contract entered is null ");
        return null;
    }

    @Override
    public Long updateContract(ContractDTO contractDTO, long id) {
        Optional<Contract> contractOptional = contractRepository.
                findById(id);
        if (contractOptional.isPresent()) {

            log.info("Exist contract template in database");
            Contract contractExisted = contractOptional.get();
            Contract contract =
                    convert.UpdateContractFromContactDTO(contractExisted, contractDTO);

            if (contractDTO.getBillingAddressId() != null) {
                if (contractExisted.getBillingInformation() != null) {
                    Long billingAddressId = contractExisted.getBillingInformation().getAddressInformationId();
                    AddressInformation billingAddressInformation =
                            addressInformationRepository.findById(billingAddressId).orElse(null);
                    if (billingAddressInformation != null) {
                        addressInformationRepository
                                .save(convert.UpdateDTOToAddressInformation(billingAddressInformation, contractDTO.getBillingAddressId()));
                        contract.setBillingInformation(billingAddressInformation);
                    }
                } else {
                    AddressInformation billingAddressInformation = convert.DTOToAddressInformation(contractDTO.getBillingAddressId());
                    billingAddressInformation = addressInformationRepository.save(billingAddressInformation);
                    contract.setBillingInformation(billingAddressInformation);
                    contractDTO.getBillingAddressId().setAddressInformationId(billingAddressInformation.getAddressInformationId());
                }
            }

            if (contractDTO.getShippingAddressId() != null) {
                if (contractExisted.getShippingInformation() != null) {
                    Long shippingAddressId = contractExisted.getShippingInformation().getAddressInformationId();
                    AddressInformation shippingAddressInformation =
                            addressInformationRepository.findById(shippingAddressId).orElse(null);
                    if (shippingAddressInformation != null) {

                    }
                    addressInformationRepository
                            .save(convert.UpdateDTOToAddressInformation(shippingAddressInformation, contractDTO.getShippingAddressId()));
                    contract.setShippingInformation(shippingAddressInformation);
                } else {
                    AddressInformation shippingAddressInformation = convert.DTOToAddressInformation(contractDTO.getShippingAddressId());
                    shippingAddressInformation = addressInformationRepository.save(shippingAddressInformation);
                    contract.setShippingInformation(shippingAddressInformation);
                    contractDTO.getShippingAddressId().setAddressInformationId(shippingAddressInformation.getAddressInformationId());
                }
            }

            contractRepository.save(contract);
            return contract.getContactId();
        }
        log.info("Can nto create contract: " + contractDTO.getContractId());
        throw new RuntimeException("Can nto create contract");
    }

    @Override
    public boolean deleteContract(Long id) {
        Optional<Contract> contract = contractRepository.
                findById(id);
        if (contract.isPresent()) {
            log.info("Exist contract in database");
            Contract contractExisted = contract.get();
            contractExisted.setIsDelete(1);
            contractExisted.setDeleteDate(getCurrentLocalDate());
            contractRepository.save(contractExisted);
            return true;
        }
        log.info("There are not exists a contract containing that ID: " + id);
        return false;
    }

    @Override
    public PageResponse<?> getListContract(int pageNo, int pageSize) {
        try {
            int page = 0;
            if (pageNo > 0) {
                page = pageNo - 1;
            }
            Pageable pageable = PageRequest.of(page, pageSize);

            Page<Contract> contracts =
                    contractRepository.findAllByIsDelete(0, pageable);

            return convert.convertContractToPageFileReponse(contracts, pageable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ContractDTO getDetailContract(Long contractId) {
        Contract contract = contractRepository.findById(contractId).orElse(null);
        if (contract != null) {
            log.info("Exist email template in database");
            ContractDTO contractDTO = convert.convertContractDtoToEntity(contract);
            return contractDTO;
        }
        log.info("Not Exist email template in database");
        return null;
    }

    @Override
    public List<OrderStatus> getListOrderStatus() {
        try {
            return orderStatusRepository.findAll();
        } catch (Exception e) {
            new RuntimeException("There are not exist Order status");
            return null;
        }
    }

    @Override
    public Long createOrderContract(OrderContractDTO orderContractDTO) {
        OrderContract orderContract = convert.convertOrderContractDtoToEntity(orderContractDTO);
        if (orderContract != null) {

            if (orderContractDTO.getBillingInformation() != null) {
                AddressInformation billingAddressInformation =
                        convert.DTOToAddressInformation(orderContractDTO.getBillingInformation());
                billingAddressInformation = addressInformationRepository.save(billingAddressInformation);
                orderContract.setBillingInformation(billingAddressInformation);
                orderContractDTO.getBillingInformation().setAddressInformationId(billingAddressInformation.getAddressInformationId());
            }

            if (orderContractDTO.getShippingInformation() != null) {
                AddressInformation shippingAddressInformation =
                        convert.DTOToAddressInformation(orderContractDTO.getShippingInformation());
                shippingAddressInformation = addressInformationRepository.save(shippingAddressInformation);
                orderContract.setShippingInformation(shippingAddressInformation);
                orderContractDTO.getShippingInformation().setAddressInformationId(shippingAddressInformation.getAddressInformationId());
            }
            orderContract.setIsDelete(0);
            log.info("The order contract entered is valid");
            OrderContract orderContractSave = orderContractRepository.save(orderContract);

            String contractNumber = generateContractNumber(orderContractSave.getOrderId());
            orderContractSave.setOrderContractNumber(contractNumber);
            orderContractRepository.save(orderContractSave);
            return orderContract.getOrderId();
        }
        log.info("The order contract entered is null ");
        return null;
    }

    @Override
    public Long updateOrderContract(OrderContractDTO orderContractDTO, long id) {
        Optional<OrderContract> orderContractOptional = orderContractRepository.
                findById(id);
        if (orderContractOptional.isPresent()) {

            log.info("Exist order contract template in database");
            OrderContract oderContractExisted = orderContractOptional.get();
            OrderContract orderContract =
                    convert.UpdateOrderContractFromContractDTO(oderContractExisted, orderContractDTO);

            if (orderContractDTO.getBillingInformation() != null) {
                if (oderContractExisted.getBillingInformation() != null) {
                    Long billingAddressId = oderContractExisted.getBillingInformation().getAddressInformationId();
                    AddressInformation billingAddressInformation =
                            addressInformationRepository.findById(billingAddressId).orElse(null);
                    if (billingAddressInformation != null) {
                        addressInformationRepository
                                .save(convert.UpdateDTOToAddressInformation(billingAddressInformation, orderContractDTO.getBillingInformation()));
                        orderContract.setBillingInformation(billingAddressInformation);
                    }
                } else {
                    AddressInformation billingAddressInformation = convert.DTOToAddressInformation(orderContractDTO.getBillingInformation());
                    billingAddressInformation = addressInformationRepository.save(billingAddressInformation);
                    orderContract.setBillingInformation(billingAddressInformation);
                    orderContractDTO.getBillingInformation().setAddressInformationId(billingAddressInformation.getAddressInformationId());
                }
            }

            if (orderContractDTO.getShippingInformation() != null) {
                if (oderContractExisted.getShippingInformation() != null) {
                    Long shippingAddressId = oderContractExisted.getShippingInformation().getAddressInformationId();
                    AddressInformation shippingAddressInformation =
                            addressInformationRepository.findById(shippingAddressId).orElse(null);
                    if (shippingAddressInformation != null)
                        addressInformationRepository
                                .save(convert.UpdateDTOToAddressInformation(shippingAddressInformation, orderContractDTO.getShippingInformation()));
                    orderContract.setShippingInformation(shippingAddressInformation);
                } else {
                    AddressInformation shippingAddressInformation =
                            convert.DTOToAddressInformation(orderContractDTO.getShippingInformation());
                    shippingAddressInformation = addressInformationRepository.save(shippingAddressInformation);
                    orderContract.setShippingInformation(shippingAddressInformation);
                    orderContractDTO.getShippingInformation().setAddressInformationId(shippingAddressInformation.getAddressInformationId());
                }
            }

            orderContractRepository.save(orderContract);
            return orderContract.getContactId();
        }
        log.info("There are not exists a order containing that ID: " + orderContractDTO.getOrderId());
        return null;
    }

    @Override
    public boolean deleteOrderContract(Long id) {
        Optional<OrderContract> orderContract = orderContractRepository.
                findById(id);
        if (orderContract.isPresent()) {
            log.info("Exist order Contract in database");
            OrderContract orderContractExisted = orderContract.get();
            orderContractExisted.setIsDelete(1);
            orderContractExisted.setDeleteDate(getCurrentLocalDate());
            orderContractRepository.save(orderContractExisted);
            return true;
        }
        log.info("There are not exists a contract containing that ID: " + id);
        return false;
    }

    @Override
    public PageResponse<?> getListOrderContract(int pageNo, int pageSize) {
        try {
            int page = 0;
            if (pageNo > 0) {
                page = pageNo - 1;
            }
            Pageable pageable = PageRequest.of(page, pageSize);

            Page<OrderContract> orderContracts = orderContractRepository.findAllByIsDelete(0, pageable);

            // Map to OrderContractResponse
            Page<OrderContractResponse> responsePage = orderContracts.map(this::mapToOrderContractResponse);

            return PageResponse.builder()
                    .page(pageable.getPageNumber())
                    .size(pageable.getPageSize())
                    .total(responsePage.getTotalPages())
                    .items(responsePage)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrderContractDTO getDetailOrderContract(Long orderId) {
        OrderContract orderContract = orderContractRepository.findByContractNumber(orderId);
        if (orderContract != null) {
            log.info("Exist order Contract template in database");
            OrderContractDTO orderContractDTO = convert.convertOrderContractToDto(orderContract);
            return orderContractDTO;
        }
        log.info("Not Exist order Contract template in database");
        return null;
    }

    @Override
    public List<String> getContractNumber() {
        List<Contract> contracts = contractRepository.findAll();
        return contracts.stream()
                .map(Contract::getContractNumber)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountContractDTO> getAccountName() {
        List<Account> accounts = accountRepository.findAll();
        return convert.ConvertAccountToAccountContractDTOs(accounts);
    }

    @Override
    public List<ContractStatus> getListContractStatus() {
        try {
            return contractStatusRepository.findAll();
        } catch (Exception e) {
            new RuntimeException("There are not exist Order status");
            return null;
        }
    }

    @Override
    public List<ProductPriceBookDTO> getListProductPriceBook() {
        try {
            List<ProductPriceBook> productPriceBookList = productPriceBookRepository.findAll();
            return convert.ConvertListProductPriceBookDTOs(productPriceBookList);
        } catch (Exception e) {
            new RuntimeException("There are not exist Order status");
            return null;
        }
    }

    @Override
    public Long AddPriceBookProduct(Long PriceBookId, Long orderId) {
        try {
            PriceBook priceBook = priceBookRepository.findById(PriceBookId).orElse(null);
            ProcessingOrderPriceBook processingOrderPriceBook =
                    ProcessingOrderPriceBook.builder().priceBookId(PriceBookId)
                            .priceBookName(priceBookRepository.findById(PriceBookId).get().getPriceBookName())
                            .orderId(orderId)
                            .build();
            ProcessingOrderPriceBook processingOrderPriceBookSave =
                    processingOrderPriceBookRepository.save(processingOrderPriceBook);
            return processingOrderPriceBookSave.getOrderId();
        } catch (Exception e) {
            new RuntimeException("There are not exist Order status");
            return null;
        }
    }

    @Override
    public List<ListPriceBookeProductReponse> getListProductAddToOrder(Long OrderId) {
        try {
            ProcessingOrderPriceBook processingOrderPriceBooks =
                    processingOrderPriceBookRepository.GetProcessingOrderPriceBookByOrderId(OrderId);
            List<ProductPriceBook> productPriceBookList =
                    productPriceBookRepository.getProductPriceBookByPriceBookId(processingOrderPriceBooks.getPriceBookId());

            return convert.ConvertListPriceBookProductOrderReponse(productPriceBookList);
        } catch (Exception e) {
            new RuntimeException("There are not exist Order status");
            return null;
        }
    }

    @Override
    public Long AddListProductToOrder(List<OrderContractProductDTO> orderContractProductDTOS) {
        try {
            orderContractProductRepository.saveAll(convert.ConvertLOrderContractProduct(orderContractProductDTOS));
            return orderContractProductDTOS.get(0).getOrderId();
        } catch (Exception e) {
            new RuntimeException("There are not exist Order status");
            return null;
        }
    }

    @Override
    public List<OrderContractProduct> getListOrderContractProduct(Long orderId) {
        try {
            List<OrderContractProduct> productPriceBookList = orderContractProductRepository.findAllByIsDeleteAndOrderId(0, orderId);
            return productPriceBookList;
        } catch (Exception e) {
            new RuntimeException("There are not exist Order status");
            return null;
        }
    }

    @Override
    public OrderContractProduct getDetailOrderContractProduct(Long orderContractProductId) {
        try {
            OrderContractProduct productPriceBookList = orderContractProductRepository.findById(orderContractProductId).orElse(null);
            return productPriceBookList;
        } catch (Exception e) {
            new RuntimeException("There are not exist Order status");
            return null;
        }
    }

    @Override
    public Long editOrderContractProduct(Long id, OrderContractProductDTO orderContractProductDTO) {

        Optional<OrderContractProduct> orderContractProductOptional = orderContractProductRepository.
                findById(id);
        if (orderContractProductOptional.isPresent()) {

            log.info("Exist order order Contract Product Optional template in database");
            OrderContractProduct orderContractProductExisted = orderContractProductOptional.get();
            OrderContractProduct orderContractProduct =
                    convert.UpdateOrderContractProductFromDTO(orderContractProductExisted, orderContractProductDTO);

            orderContractProductRepository.save(orderContractProduct);
            return orderContractProduct.getOrderContractProductId();

        }
        return null;
    }

    @Override
    public List<OrderContract> getListOrderContractByContractNumber(String contractNumber) {
        try {
            List<OrderContract> orderContract =
                    orderContractRepository.findAllByIsDeleteAndContractNumber(0, contractNumber);
            return orderContract;
        } catch (Exception e) {
            new RuntimeException("There are not exist Order status");
            return null;
        }
    }

    @Override
    public ProcessingOrderPriceBook getDetailProcessingOrderPriceBook(Long id) {
        ProcessingOrderPriceBook processingOrderPriceBook =
                processingOrderPriceBookRepository.GetProcessingOrderPriceBookByOrderId(id);
        if (processingOrderPriceBook != null) {
            log.info("Exist order Contract template in database");
            return processingOrderPriceBook;
        }
        log.info("Not Exist order Contract template in database");
        return null;
    }

    @Override
    public boolean deleteOrderContractProduct(Long id) {
        Optional<OrderContractProduct> orderContractProduct = orderContractProductRepository.
                findById(id);
        if (orderContractProduct.isPresent()) {
            log.info("Exist contract in database");
            OrderContractProduct contractExisted = orderContractProduct.get();
            contractExisted.setIsDelete(1);
            orderContractProductRepository.save(contractExisted);
            return true;
        }
        log.info("There are not exists a contract containing that ID: " + id);
        return false;
    }

    public Date getCurrentLocalDate() {
        return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public String generateContractNumber(Long id) {
        int length = Math.max(MINIMUM_LENGTH, String.valueOf(id).length());
        return String.format("%0" + length + "d", id);
    }

    private OrderContractResponse mapToOrderContractResponse(OrderContract orderContract) {
        OrderContractResponse response = new OrderContractResponse();
        response.setOrderId(orderContract.getOrderId());
        response.setAccountName(orderContract.getAccountId().toString());
        response.setOrderStartDate(orderContract.getOrderStartDate());
        response.setOrderStatus(orderContract.getOrderStatus());
        response.setCompanyId(orderContract.getCompanyId());
        response.setDescription(orderContract.getDescription());
        response.setContactId(orderContract.getContactId());
        response.setContractNumber(orderContract.getContractNumber());
        response.setOrderContractNumber(orderContract.getOrderContractNumber());

        // Calculate orderAmount by summing totalPrice of all related OrderContractProducts
        List<OrderContractProduct> products = orderContractProductRepository.findByOrderId(orderContract.getOrderId());
        Double orderAmount = products.stream().mapToDouble(OrderContractProduct::getTotalPrice).sum();
        response.setOrderAmount(orderAmount);

        return response;
    }

    @Override
    public boolean updateOrderContact(OrderContractDTO orderContractDTO, Long id) {
        try {
            Optional<OrderContract> orderContractOptional = orderContractRepository.findById(id);
            if (orderContractOptional.isPresent()) {
                OrderContract orderContract = getOrderContractUpdate(orderContractDTO, orderContractOptional);

                orderContractRepository.save(orderContract);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("There are not exist Order status");
        }
    }


    @Override
    public boolean updateOrderContactProduct(OrderContractProductDTO orderContractProductDTO, Long id) {
        try {
            Optional<OrderContractProduct> orderContractProductOptional = orderContractProductRepository.findById(id);
            if (orderContractProductOptional.isPresent()) {
                OrderContractProduct orderContractProduct = getOrderContractProductUpdate(orderContractProductDTO, orderContractProductOptional);
                orderContractProductRepository.save(orderContractProduct);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("There are not exist Order status");
        }
    }

    @Override
    public ContractFile uploadFile(File file,String fileName,Long ContractId) {

        try {
            ContractFile contractFile = contractFileRepository.findByContractId(ContractId);
            if(contractFile != null){
                throw new RuntimeException("File already exists");
            }
            com.google.api.services.drive.model.File uploadFile = saveFileTODrive(file, fileName);

            ContractFile fileManager = new ContractFile();
            fileManager.setCreateDate(
                    Date.from(LocalDate.now()
                            .atStartOfDay(ZoneId.systemDefault()).toInstant()));
            fileManager.setContractId(ContractId);
            fileManager.setFileName(fileName);
            fileManager.setFileId(uploadFile.getId());
            contractFileRepository.save(fileManager);

            file.delete();
            return fileManager;
        }catch (Exception e){
            throw new RuntimeException("Error when upload file to Drive: " + e.getMessage());
        }
    }

    @Override
    public FileDetailResponse getFileFromDrive(String fileId) {
        try {
            ContractFile fileManager = contractFileRepository.findByFileId(fileId);

            Drive drive = createDriveService();
            com.google.api.services.drive.model.File file =
                    drive.files().get(fileManager.getFileId()).execute();
            String fileName = file.getName();
            java.io.File downloadedFile = new java.io.File(fileName);
            OutputStream outputStream = new FileOutputStream(downloadedFile);
            drive.files().get(fileManager.getFileId()).executeMediaAndDownloadTo(outputStream);
            outputStream.flush();
            outputStream.close();
            String contentType = fileManager.determineContentType();

            return FileDetailResponse.builder()
                    .resource(new FileSystemResource(downloadedFile))
                    .contentType(contentType)
                    .build();

        } catch (Exception e) {
            log.error("Error downloading file from Drive: " + e.getMessage());
            return null;
        }
    }
    @Override
    public ContractFile getFileDetailFromContract(Long ContractId){
        try {
            return contractFileRepository.findByContractId(ContractId);
        }catch (Exception e){
            throw  new RuntimeException("Error when get contract file detail " + e.getMessage());
        }
    }

    @Override
    public boolean deleteFile(Long contractFileId) {
        try {
            contractFileRepository.deleteById(contractFileId);
            return true;
        }catch (Exception e){
            throw  new RuntimeException("Error when delete file share " + e.getMessage());
        }
    }

    private com.google.api.services.drive.model.File saveFileTODrive(File file, String fileName) throws GeneralSecurityException, IOException {
        String folderId = "1gY4F2gBf3nBqvNemmTK0tovSRSerXDQO";
        Drive drive = createDriveService();
        com.google.api.services.drive.model.File fileMetaData =
                new com.google.api.services.drive.model.File();
        fileMetaData.setName(fileName);
        fileMetaData.setParents(Collections.singletonList(folderId));
        FileContent mediaContent = new FileContent("pdf/xlsx/xls/docx/doc/zip/jpg/JPEG/png|gif", file);
        com.google.api.services.drive.model.File uploadFile =
                drive.files().create(fileMetaData,mediaContent).setFields("id").execute();
        String fileUrl = "https://drive.google.com/uc?export=view&id="+uploadFile.getId();
        log.info("File URL: "+fileUrl);
        return uploadFile;
    }

    public static String getPathToGoodleCredentials(){
        String currentDirectory = System.getProperty("user.dir");
        Path fiPath = Paths.get(currentDirectory,"cred.json");
        return fiPath.toString();

    }
    public Drive createDriveService() throws GeneralSecurityException, IOException{
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .build();
    }

    private static OrderContractProduct getOrderContractProductUpdate(OrderContractProductDTO orderContractProductDTO, Optional<OrderContractProduct> orderContractProductOptional) {
        OrderContractProduct orderContractProduct = orderContractProductOptional.get();

        if (orderContractProductDTO.getProductId() != null) {
            orderContractProduct.setProductId(orderContractProductDTO.getProductId());
        }
        if (orderContractProductDTO.getProductName() != null) {
            orderContractProduct.setProductName(orderContractProductDTO.getProductName());
        }
        if (orderContractProductDTO.getQuantity() != 0) {
            orderContractProduct.setQuantity(orderContractProductDTO.getQuantity());
        }
        if (orderContractProductDTO.getUnitPrice() != null) {
            orderContractProduct.setUnitPrice(orderContractProductDTO.getUnitPrice());
        }
        if (orderContractProductDTO.getTotalPrice() != null) {
            orderContractProduct.setTotalPrice(orderContractProductDTO.getTotalPrice());
        }
        if (orderContractProductDTO.getListPrice() != null) {
            orderContractProduct.setListPrice(orderContractProductDTO.getListPrice());
        }
        if (orderContractProductDTO.getCreateBy() != null) {
            orderContractProduct.setCreateBy(orderContractProductDTO.getCreateBy());
        }
        if (orderContractProductDTO.getDescription() != null) {
            orderContractProduct.setDescription(orderContractProductDTO.getDescription());
        }
        return orderContractProduct;
    }

    private OrderContract getOrderContractUpdate(OrderContractDTO orderContractDTO, Optional<OrderContract> orderContractOptional) {
        OrderContract orderContract = orderContractOptional.get();

        if (orderContractDTO.getAccountId() != null) {
            orderContract.setAccountId(orderContractDTO.getAccountId());
        }
        if (orderContractDTO.getOrderStartDate() != null) {
            orderContract.setOrderStartDate(orderContractDTO.getOrderStartDate());
        }
        if (orderContractDTO.getOrderStatus() != null) {
            orderContract.setOrderStatus(orderStatusRepository.findById(orderContractDTO.getOrderStatus()).orElse(null));
        }
        if (orderContractDTO.getCompanyId() != null) {
            orderContract.setCompanyId(orderContractDTO.getCompanyId());
        }
        if (orderContractDTO.getDescription() != null) {
            orderContract.setDescription(orderContractDTO.getDescription());
        }
        if (orderContractDTO.getBillingInformation() != null) {
            if (orderContractDTO.getBillingInformation() != null) {
                AddressInformation billingAddressInformation = convert.DTOToAddressInformation(orderContractDTO.getBillingInformation());
                billingAddressInformation = addressInformationRepository.save(billingAddressInformation);
                orderContract.setBillingInformation(billingAddressInformation);
            }
        } else {
            AddressInformation billingAddressInformation = convert.DTOToAddressInformation(orderContractDTO.getBillingInformation());
            billingAddressInformation = addressInformationRepository.save(billingAddressInformation);
            orderContract.setBillingInformation(billingAddressInformation);
        }
        if (orderContractDTO.getBillingInformation() != null) {
            if (orderContractDTO.getShippingInformation() != null) {
                AddressInformation shippingAddressInformation = convert.DTOToAddressInformation(orderContractDTO.getShippingInformation());
                shippingAddressInformation = addressInformationRepository.save(shippingAddressInformation);
                orderContract.setShippingInformation(shippingAddressInformation);
            }
        } else {
            AddressInformation billingAddressInformation = convert.DTOToAddressInformation(orderContractDTO.getBillingInformation());
            billingAddressInformation = addressInformationRepository.save(billingAddressInformation);
            orderContract.setBillingInformation(billingAddressInformation);
        }
        if (orderContractDTO.getContactId() != null) {
            orderContract.setContactId(orderContractDTO.getContactId());
        }
        if (orderContractDTO.getContractNumber() != null) {
            orderContract.setContractNumber(orderContractDTO.getContractNumber());
        }
        return orderContract;
    }
}
