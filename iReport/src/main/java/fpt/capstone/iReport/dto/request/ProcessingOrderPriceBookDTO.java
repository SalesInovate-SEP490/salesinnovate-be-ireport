package fpt.capstone.iReport.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class ProcessingOrderPriceBookDTO {
    private Long processingOrderPriceBookId;
    private Long priceBookeId;
    private String priceBookeName;
    private Long orderId;
}
