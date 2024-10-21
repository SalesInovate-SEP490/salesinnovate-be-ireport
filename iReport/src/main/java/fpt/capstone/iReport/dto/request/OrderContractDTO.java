package fpt.capstone.iReport.dto.request;

import fpt.capstone.iReport.model.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
public class OrderContractDTO {
    private Long orderId;
    private Long accountId;
    private Date orderStartDate;
    private Long orderStatus;
    private String companyId;
    private String description;
    private AddressInformationDTO BillingInformation;
    private AddressInformationDTO ShippingInformation;
    private Long contactId;
    private String contractNumber;
    private String orderNumber;

}
