package fpt.capstone.iReport.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contract_file")
public class ContractFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_file_id")
    private Long contractFileId;

    @Column(name = "file_id")
    private String fileId;

    @Column(name = "file_name", length = 255)
    private String fileName;

    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @Column(name = "contract_id")
    private Long contractId;

    @Transient // This annotation indicates that this field is not persistent
    private String contentType;


    // Add a method to determine content type based on file extension
    public String determineContentType() {
        if (fileName != null && fileName.contains(".")) {
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            switch (extension) {
                case "txt":
                    return "text/plain";
                case "pdf":
                    return "application/pdf";
                case "xls":
                    return "application/vnd.ms-excel";
                case "xlsx":
                    return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                case "doc":
                    return "application/msword";
                case "docx":
                    return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                case "zip":
                    return "application/zip";
                case "jpg":
                case "jpeg":
                    return "image/jpeg";
                case "png":
                    return "image/png";
                // Add more cases for other file types as needed
                default:
                    return "application/octet-stream";
            }
        }
        return "application/octet-stream";
    }
}