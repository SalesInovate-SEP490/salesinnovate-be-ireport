package fpt.capstone.iReport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fpt.capstone.iReport.controller.ContractController;
import fpt.capstone.iReport.dto.request.AddressInformationDTO;
import fpt.capstone.iReport.dto.request.ContractDTO;
import fpt.capstone.iReport.dto.response.ResponseData;
import fpt.capstone.iReport.dto.response.ResponseError;
import fpt.capstone.iReport.service.ContractService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Date;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class IReportApplicationTests {
    ObjectMapper objectMapper  = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();
    // Create AddressInformationDTO for billing and shipping addresses
    private final AddressInformationDTO billingAddress = AddressInformationDTO.builder()
            .addressInformationId(1L)
            .street("string")
            .city("string")
            .province("string")
            .postalCode("string")
            .country("string")
            .build();

    private final AddressInformationDTO shippingAddress = AddressInformationDTO.builder()
            .addressInformationId(1L)
            .street("string")
            .city("string")
            .province("string")
            .postalCode("string")
            .country("string")
            .build();

    // Create ContractDTO
    private final ContractDTO contractDTO = ContractDTO.builder()
            .contractId(1L)
            .userId(1L)
            .contractStartDate(new Date()) // Replace with actual Date object from JSON
            .contractTerm(1L)
            .ownerExpirationNotice(new Date()) // Replace with actual Date object from JSON
            .specialTerms("string")
            .description("string")
            .accountId(1L)
            .priceBookId(1L)
            .billingAddressId(billingAddress)
            .shippingAddressId(shippingAddress)
            .contactId(1L)
            .customerSignedTitle("string")
            .customerSignedDate(new Date()) // Replace with actual Date object from JSON
            .companyId(1L)
            .companySignedDate(new Date()) // Replace with actual Date object from JSON
            .contractStatus(1L)
            .contractNumber("0000001")
            .build();
    // Update a ContractDTO object with valid data
    ContractDTO updateContractDTO = ContractDTO.builder()
            .contractId(1L)
            .userId(1L)
            .contractStartDate(new Date())
            .contractTerm(1L)
            .ownerExpirationNotice(new Date())
            .specialTerms("string")
            .description("string")
            .accountId(1L)
            .priceBookId(1L)
            .billingAddressId(billingAddress)
            .shippingAddressId(shippingAddress)
            .contactId(1L)
            .customerSignedTitle("string")
            .customerSignedDate(new Date())
            .companyId(1L)
            .companySignedDate(new Date())
            .contractStatus(1L)
            .contractNumber("0000001")
            .build();

	private MockMvc mockMvc;

	@Mock
	private ContractService contractService;

	@InjectMocks
	private ContractController contractController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(contractController).build();
	}

	@Test
	void testCreateContractSuccess() throws Exception {

		// Mock the service to return a successful response
		when(contractService.createContract(contractDTO)).thenReturn( contractDTO.getContractId());
        String content = objectWriter.writeValueAsString(contractDTO);
		MockHttpServletRequestBuilder mockRequest = (MockMvcRequestBuilders.post("/api/contract/create-contract-template")
						.contentType(MediaType.valueOf("application/json"))
                        .accept(MediaType.APPLICATION_JSON)
						.content(content)); // Simplified JSON content

        mockMvc.perform(mockRequest)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()));
	}
    @Test
    void testCreateContractWithNullInput() throws Exception {
        ContractDTO contractInvalidValue = ContractDTO.builder()
                .contractId(1L)
                .userId(1L).build();
        String content = objectWriter.writeValueAsString(contractInvalidValue);
        // Perform the POST request with null input
        MockHttpServletRequestBuilder mockRequest =MockMvcRequestBuilders.post("/api/contract/create-contract-template")
                .contentType(MediaType.valueOf("application/json"))
                .accept(MediaType.APPLICATION_JSON)
                .content(content);// Null JSON content
        mockMvc.perform(mockRequest)
                .andExpect(result -> assertFalse(result.getResolvedException() instanceof IllegalArgumentException));
    }
    @Test
    void testCreateContractIncorrectUserIdTypeInput() throws Exception {
        ContractDTO contractInvalidValue = ContractDTO.builder()
                .contractId(1L)
                .userId(1L)
                .build();
        String content = objectWriter.writeValueAsString(contractInvalidValue);
        // Perform the POST request with null input
        MockHttpServletRequestBuilder mockRequest =MockMvcRequestBuilders.post("/api/contract/create-contract-template")
                .contentType(MediaType.valueOf("application/json"))
                .accept(MediaType.APPLICATION_JSON)
                .content(content);// Null JSON content
        mockMvc.perform(mockRequest)
                .andExpect(result -> assertFalse(result.getResolvedException() instanceof IllegalArgumentException));
    }
    @Test
    void testCreateContractWithWrongUrl() throws Exception {
        when(contractService.createContract(contractDTO)).thenReturn( contractDTO.getContractId());
        String content = objectWriter.writeValueAsString(contractDTO);
        // Perform the POST request to a wrong URL
        mockMvc.perform(MockMvcRequestBuilders.post("/api/contract/wrong-url")
                        .contentType(MediaType.valueOf("application/json"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)) // Valid JSON content
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateContractWithInvalidInput() throws Exception {
        ContractDTO contractInvalidValue = ContractDTO.builder()
                .contractId(1L)
                .userId(1L).build();
        String content = objectWriter.writeValueAsString(contractInvalidValue);
        // Perform the POST request with null input
        MockHttpServletRequestBuilder mockRequest =MockMvcRequestBuilders.post("/api/contract/create-contract-template")
                .contentType(MediaType.valueOf("application/json"))
                .accept(MediaType.APPLICATION_JSON)
                .content("{ \"contractId\":  \"string\" }");// Invalid JSON content
        mockMvc.perform(mockRequest)
                .andExpect(result -> assertFalse(result.getResolvedException() instanceof IllegalArgumentException));
    }

    @Test
    void testUpdateContractSuccess() throws Exception {
        // Mock the service to return a successful response
        when(contractService.updateContract(any(ContractDTO.class), eq(1L))).thenReturn(1L);

        // Convert the ContractDTO object to JSON
        String content = objectWriter.writeValueAsString(updateContractDTO);

        // Perform the PATCH request
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/contract/update-contract-template/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void testUpdateContractWithNullData() throws Exception {
        // Create a ContractDTO object with null or invalid data
        ContractDTO updateContractDTO = ContractDTO.builder()
                .contractId(null)  // or any other field that should trigger a validation error
                .userId(null)
                .build();

        // Assuming the service will throw an IllegalArgumentException when data is invalid
        when(contractService.updateContract(eq(updateContractDTO), eq(1L)))
                .thenThrow(new IllegalArgumentException("Invalid contract data"));

        // Convert the ContractDTO object to JSON
        String content = objectWriter.writeValueAsString(updateContractDTO);

        // Perform the PATCH request and expect a bad request due to invalid data
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/contract/update-contract-template/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())  // Expecting a 400 Bad Request
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException));
    }
    @Test
    void testUpdateContractWithInvalidInput() throws Exception {

        String content = objectWriter.writeValueAsString(updateContractDTO);
        // Perform the Update request with null input
        MockHttpServletRequestBuilder mockRequest =MockMvcRequestBuilders.post("/api/contract/update-contract-templates/1")
                .contentType(MediaType.valueOf("application/json"))
                .accept(MediaType.APPLICATION_JSON)
                .content(content);// Invalid JSON content
        mockMvc.perform(mockRequest)
                .andExpect(result -> assertFalse(result.getResolvedException() instanceof IllegalArgumentException));
    }

    @Test
    void testDeleteContractSuccess() throws Exception {
        // Mock the service to perform a successful deletion
        when(contractService.deleteContract(1L)).thenReturn(true);

        // Perform the DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/contract/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetListContractSuccess() {
        int currentPage = 0;
        int perPage = 10;
        when(contractService.getListContract(currentPage, perPage)).thenReturn(null);

        ResponseData<?> response = (ResponseData<?>) contractController.getListContract(currentPage, perPage);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(1, response.getCode());
        assertEquals(null, response.getData());
    }


}
