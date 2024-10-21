package fpt.capstone.iReport.model.opportunities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="opportunity_contact")
public class OpportunityContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opportunity_contact_id")
    private Long opportunityContactId;
    @Column(name = "opportunity_id")
    private Long opportunityId;
    @Column(name = "opportunity_contact_role_id")
    private Long opportunityContactRoleId;
    @Column(name = "contact_id")
    private Long contactId ;
    @Column(name = "influence")
    private Float influence;
    @Column(name = "revenue_share")
    private Float revenueShare;
}
