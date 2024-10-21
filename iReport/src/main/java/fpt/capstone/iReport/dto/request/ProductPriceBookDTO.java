package fpt.capstone.iReport.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class ProductPriceBookDTO {
    private String priceBookName;
    private Long priceBookId;
}
