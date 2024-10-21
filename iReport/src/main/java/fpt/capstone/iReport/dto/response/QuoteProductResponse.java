package fpt.capstone.iReport.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class QuoteProductResponse {
    private String productName;
    private int quantity;
    private Double unitPrice;
    private Double totalPrice2;
    private Double listPrice;
}
