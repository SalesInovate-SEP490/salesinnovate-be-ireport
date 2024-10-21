package fpt.capstone.iReport.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long contractId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "contract_start_date")
    private Date contractStartDate;

    @Column(name = "contract_term")
    private Long contractTerm;

    @Column(name = "owner_expiration_noitice")
    private Date ownerExpirationNotice;

    @Column(name = "scpecial_terms", length = 200)
    private String specialTerms;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "price_book_id")
    private Long priceBookId;

    @Column(name = "contact_id")
    private Long contactId;

    @Column(name = "customer_signed_title", length = 100)
    private String customerSignedTitle;

    @Column(name = "customer_signed_date")
    private Date customerSignedDate;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "company_signed_date")
    private Date companySignedDate;

    @Column(name = "is_delete")
    private Integer isDelete;

    @Column(name = "delete_date")
    private Date deleteDate;

    @Column(name = "contract_number")
    private String contractNumber;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true )
    @JoinColumn(name = "billing_address_id")
    private AddressInformation billingInformation;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "shipping_address_id")
    private AddressInformation shippingInformation;

    @ManyToOne
    @JoinColumn(name = "contract_status_id")
    private ContractStatus contractStatus;
}
