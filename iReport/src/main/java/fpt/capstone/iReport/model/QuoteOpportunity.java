//package fpt.capstone.iReport.model;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.Date;
//
//@Entity
//@Builder
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "quote_opportinity")
//public class QuoteOpportunity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "quote_opportinity_id")
//    private Long id;
//
//    @Column(name = "quote_number", length = 50)
//    private String quoteNumber;
//
//    @Column(name = "quote_name", length = 50)
//    private String quoteName;
//
//    @Column(name = "expiration_date")
//    @Temporal(TemporalType.DATE)
//    private Date expirationDate;
//
//    @Column(name = "status_quote_opportinity_id")
//    private Long statusQuoteOpportunityId;
//
//    @Column(name = "description", length = 100)
//    private String description;
//
//    @Column(name = "opportunity_id")
//    private Long opportunityId;
//
//    @Column(name = "account_name")
//    private String accountName;
//
//    @Column(name = "discount")
//    private Double discount;
//
//    @Column(name = "tax")
//    private Double tax;
//
//    @Column(name = "shipping_handling")
//    private Double shippingHandling;
//
//    @Column(name = "grand_total")
//    private Double grandTotal;
//
//    @Column(name = "contact_id")
//    private Long contactId;
//
//    @Column(name = "email")
//    private String email;
//
//    @Column(name = "phone")
//    private String phone;
//
//    @Column(name = "fax")
//    private String fax;
//
//    @Column(name = "billing_name")
//    private String billingName;
//
//    @Column(name = "shipping_name")
//    private String shippingName;
//
//    @Column(name = "opportunity_name")
//    private String opportunityName;
//
//    @Column(name = "is_delete")
//    private int isDelete;
//
//    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true )
//    @JoinColumn(name = "billing_address_information")
//    private AddressInformation billingAddressInformation;
//
//    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "shipping_nformation_id")
//    private AddressInformation shippingInformationId;
//}