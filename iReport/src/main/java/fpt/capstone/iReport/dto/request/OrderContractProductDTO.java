package fpt.capstone.iReport.dto.request;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class OrderContractProductDTO {
    private Long orderContractProductId;
    private Long orderId;
    private Long productId;
    private String productName;
    private int quantity;
    private Double unitPrice;
    private Double totalPrice;
    private Double listPrice;
    private String createBy;
    private String description;
}
