    package fpt.capstone.iReport.controller;

    import fpt.capstone.iReport.dto.request.ReportDTO;
    import fpt.capstone.iReport.dto.response.ResponseData;
    import fpt.capstone.iReport.dto.response.ResponseError;
    import fpt.capstone.iReport.model.report.Report;
    import fpt.capstone.iReport.service.ReportService;
    import lombok.AllArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.core.io.Resource;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.File;
    import java.util.Date;

    @Slf4j
    @RestController
    @AllArgsConstructor
    @RequestMapping("/api/report")
    public class ReportController {
        private final ReportService reportService;
        @PostMapping("create-report-file/upload")
        public ResponseData<?> GenerateReport(
                @RequestParam(value  = "userId") String userId)
        {
            try{
                ReportDTO reportDTO = reportService.uploadFile(userId);
                return new ResponseData<>(HttpStatus.OK.value(),"Upload file success", reportDTO,1);
            } catch (Exception e) {
                return new ResponseError(0,
                        HttpStatus.BAD_REQUEST.value(), "Upload file failed");
            }
        }
        @GetMapping("get-string-file/{userId}")
        public ResponseData<?> downloadFileStringJsonFromDrive(
                @PathVariable String userId
        ) {
            String file = reportService.getFileJsonFromDrive(userId);
            return new ResponseData<>(HttpStatus.OK.value(),"Get String file success", file,1);
        }
        @GetMapping("get-file-report/{userId}")
        public ResponseEntity<Resource> downloadFileFromDrive(@PathVariable String userId) {
            Resource resource = reportService.getFileFromDrive(userId);

            if (resource != null && resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }
