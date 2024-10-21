package fpt.capstone.iReport.dto.request;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class QuoteProductDTO {
    private Long quoteOpportunityId;
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
