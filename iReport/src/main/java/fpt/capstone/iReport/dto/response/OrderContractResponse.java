package fpt.capstone.iReport.dto.response;

import fpt.capstone.iReport.model.AddressInformation;
import fpt.capstone.iReport.model.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderContractResponse {
    private Long orderId;
    private String accountName;
    private Date orderStartDate;
    private OrderStatus orderStatus;
    private String companyId;
    private String description;
    private Long contactId;
    private String contractNumber;
    private String orderContractNumber;
    private Double orderAmount;
}
