package fpt.capstone.iReport.dto.request;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@Getter
@Setter
public class AccountDTO {
    private Long accountId;
    private String accountName;
    private String userId;
    private Long parentAccountId ;
    private String industry;
    private String accountType;
    private String website;
    private String phone;
    private String description;
    private Integer noEmployee;
    private String createdBy ;
    private LocalDateTime createDate ;
    private LocalDateTime editDate;
    private String editBy ;
    private Integer isDeleted ;

}
