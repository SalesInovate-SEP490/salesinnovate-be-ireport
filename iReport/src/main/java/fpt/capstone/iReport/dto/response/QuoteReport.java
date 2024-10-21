package fpt.capstone.iReport.dto.response;

import lombok.*;

import java.util.Date;
import java.util.List;
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuoteReport {

    private String prepareBy;
    private String email;
    private String shipToName;
    private String shipToNameStreet;
    private String shipToNameCity;
    private String shipToNamePostalCode;
    private String shipToNameCountry;
    private String billToName;
    private String billToNameStreet;
    private String billToNameCity;
    private String billToNamePostalCode;
    private String billToNameCountry;
    private Date createDate;
    private String quoteNumber;
    private double subTotal;
    private double discount;
    private double totalPrice;
    private double grandTotal;
    private List<QuoteProductResponse> quoteProductResponses;
}
