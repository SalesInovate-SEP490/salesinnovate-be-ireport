package fpt.capstone.iReport.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportDTO {
    private Long reportId ;
    private String userId ;
    private String fileId ;
    private String fileName ;
}
