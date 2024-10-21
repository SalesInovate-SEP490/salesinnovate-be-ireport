package fpt.capstone.iReport.dto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "processing_quote_price_book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessingQuotePriceBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "processing_quote_price_book_id")
    private Long id;

    @Column(name = "price_book_id")
    private Long priceBookId;

    @Column(name = "price_book_name", length = 50)
    private String priceBookName;

    @Column(name = "quote_id")
    private Long quoteId;
}
