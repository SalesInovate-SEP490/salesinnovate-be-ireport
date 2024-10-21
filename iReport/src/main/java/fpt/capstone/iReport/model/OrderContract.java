package fpt.capstone.iReport.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_contract")
public class OrderContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "order_start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderStartDate;

    @ManyToOne
    @JoinColumn(name = "order_status_id", referencedColumnName = "order_status_id")
    private OrderStatus orderStatus;

    @Column(name = "company_id", length = 200)
    private String companyId;

    @Column(name = "description", length = 200)
    private String description;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true )
    @JoinColumn(name = "billing_address_id")
    private AddressInformation BillingInformation;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "shipping_address_id")
    private AddressInformation ShippingInformation;

    @Column(name = "contact_id")
    private Long contactId;

    @Column(name = "contract_number")
    private String contractNumber;

    @Column(name = "is_delete")
    private Integer isDelete;

    @Column(name = "delete_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteDate;

    @Column(name = "oder_contract_numbercontract_number")
    private String orderContractNumber;

}