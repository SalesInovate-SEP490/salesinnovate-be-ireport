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
@Table(name="log_call_leads")
public class LogCallLeads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_call_id")
    private Long logCallId;
    @Column(name = "subject")
    private String subject;
    @Column(name = "comment")
    private String comment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="lead_id", nullable=false)
    private Leads lead;
}
