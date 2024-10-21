//package fpt.capstone.iReport.model;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.Date;
//
//@Entity
//@Table(name = "quote_opportunity_product")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class QuoteOpportunityProduct {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "quote_product_id")
//    private Long quoteProductId;
//
//    @Column(name = "quote_id")
//    private Long quoteId;
//
//    @Column(name = "product_id")
//    private Long productId;
//
//    @Column(name = "product_name", length = 255)
//    private String productName;
//
//    @Column(name = "quantity")
//    private int quantity;
//
//    @Column(name = "unit_price")
//    private Double unitPrice;
//
//    @Column(name = "total_price")
//    private Double totalPrice;
//
//    @Column(name = "list_price")
//    private Double listPrice;
//
//    @Column(name = "create_by", length = 255)
//    private String createBy;
//
//    @Column(name = "description", length = 255)
//    private String description;
//}