package fpt.capstone.iReport.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ListPriceBookeProductReponse {
    private Long productId;
    private String productName;
    private String productCode;
    private Double listPrice;
    private String productPriceBookCurrencyName;
    private String productDescription;
    private String productFamily;

}


