package fpt.capstone.iReport.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "processing_order_price_book")
public class ProcessingOrderPriceBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "processing_order_price_book_id")
    private Long processingOrderPriceBookId;

    @Column(name = "price_book_id")
    private Long priceBookId;

    @Column(name = "price_book_name")
    private String priceBookName;

    @Column(name = "order_id")
    private Long orderId;
}
