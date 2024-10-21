package fpt.capstone.iReport.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ContactDTO {
    private Long contactId ;
    private Long accountId ;
    private String userId ;
    private String firstName ;
    private String lastName ;
    private String middleName ;
    private Long report_to ;
    private String title ;
    private String email ;
    private String phone;
    private String department ;
    private String mobile ;
    private String fax ;
    private String createdBy ;
    private LocalDateTime createDate;
    private LocalDateTime editDate ;
    private String editBy ;
    private Integer isDeleted ;
}