package fpt.capstone.iReport.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contract_status")
public class ContractStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_status_id")
    private Long contractStatusId;

    @Column(name = "contract_status_name", length = 50)
    private String contractStatusName;
}
