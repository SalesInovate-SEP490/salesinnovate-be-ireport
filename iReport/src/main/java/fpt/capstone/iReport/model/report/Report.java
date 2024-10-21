package fpt.capstone.iReport.model.report;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId ;
    @Column(name = "user_id")
    private String userId ;
    @Column(name = "file_id")
    private String fileId ;
    @Column(name = "file_name")
    private String fileName ;
}
