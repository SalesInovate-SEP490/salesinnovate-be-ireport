package fpt.capstone.iReport.dto.request;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class QuoteOpportunityProductDTO {
    private Long quoteProductId;
    private Long quoteId;
    private Long productId;
    private String productName;
    private int quantity;
    private Double unitPrice;
    private Double totalPrice;
    private Double listPrice;
    private String createBy;
    private String description;
}
