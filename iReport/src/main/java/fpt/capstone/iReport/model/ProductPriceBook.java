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
@Table(name = "product_price_book")
public class ProductPriceBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_price_book_id")
    private Long productPriceBookId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "price_book_id")
    private Long priceBookId;

    @Column(name = "list_price")
    private Double listPrice;

    @Column(name = "use_standard_price")
    private Integer useStandardPrice;

    @Column(name = "created_by")
    private String createBy;

    @Column(name = "edit_by")
    private String editBy;

    @Column(name = "edit_Date")
    private Date editDate;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_family")
    private ProductFamily productFamily;
}
