package fpt.capstone.iReport.model;

import fpt.capstone.iReport.model.leads.LogCallLeads;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_contract_product")
public class OrderContractProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_contract_product_id")
    private Long orderContractProductId;

    @Column(name = "order_contract_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "list_price")
    private Double listPrice;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "description")
    private String description;

    @Column(name = "is_delete")
    private int isDelete;
}