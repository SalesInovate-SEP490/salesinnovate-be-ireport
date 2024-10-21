package fpt.capstone.iReport.model.leads;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="lead_salution")
public class LeadSalution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lead_salution_id")
    private Long leadSalutionId ;
    @Column(name = "lead_salution_name")
    private String leadSalutionName;
}
