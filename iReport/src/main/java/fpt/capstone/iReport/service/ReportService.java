package fpt.capstone.iReport.service;

import fpt.capstone.iReport.dto.request.ReportDTO;
import fpt.capstone.iReport.dto.response.PageResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

public interface ReportService {
    MultipartFile generateReport();
    ReportDTO uploadFile(String userId);
    Resource getFileFromDrive(String userId);
    String getFileJsonFromDrive(String userId);
}
