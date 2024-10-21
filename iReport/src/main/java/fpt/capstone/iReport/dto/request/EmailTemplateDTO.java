package fpt.capstone.iReport.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailTemplateDTO {
    private Long id;
    private String sendTo;
    private String mailSubject;
    private String htmlContent;
    private int isDeleted;
}
