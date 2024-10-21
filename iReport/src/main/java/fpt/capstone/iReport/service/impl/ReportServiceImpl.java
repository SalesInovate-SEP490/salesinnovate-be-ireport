package fpt.capstone.iReport.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import fpt.capstone.iReport.dto.Convert;
import fpt.capstone.iReport.dto.request.*;
import fpt.capstone.iReport.dto.response.ReportField;
import fpt.capstone.iReport.dto.response.ReportResponse;
import fpt.capstone.iReport.model.report.Report;
import fpt.capstone.iReport.repository.ReportRepository;
import fpt.capstone.iReport.repository.account.AccountRepository;
import fpt.capstone.iReport.repository.leads.LeadsRepository;
import fpt.capstone.iReport.repository.oppotunity.OpportunityRepository;
import fpt.capstone.iReport.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_KEY_PATH = getPathToGoodleCredentials();
    private LeadsRepository leadsRepository;
    private OpportunityRepository opportunityRepository;
    private AccountRepository accountRepository;
    private ReportRepository reportRepository;
    private ObjectMapper objectMapper;
    private Convert converter;

    public MultipartFile generateReport() {
        // Fetch data from repositories

        List<LeadDTO> leadsData = converter.ConvertLeadsToDTOs(leadsRepository.findAll());
        List<OpportunityDTO> opportunityData =
                converter.ConvertListOpportunitiesToListDTOs(opportunityRepository.findAll());
        List<AccountDTO> accountData = converter.ConvertAccountsToDTOs(accountRepository.findAll());


        ReportResponse report = ReportResponse.builder()
                .leadField(createLeadField())
                .leadsReport(leadsData)
                .accountField(createAccountField())
                .accountReprot(accountData)
                .opportunityField(createOpportunityField())
                .opportunityReport(opportunityData)
                .toggleChart(true)
                .typeChart("bar")
                .reportType("Account|Opportunity|Leads")
                .build();

        try {
            String jsonReport = objectMapper.writeValueAsString(report);
            InputStream inputStream = new ByteArrayInputStream(jsonReport.getBytes());
            return new MockMultipartFile("report.json", "report.json",
                    MediaType.APPLICATION_JSON_VALUE, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static List<Object> getFieldsFromEntities(List<Object> entities) {
        List<Object> fields = new ArrayList<>();
        for (Object entity : entities) {
            Field[] declaredFields = entity.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                try {
                    String key = field.getName();
                    String label = String.valueOf(field.get(entity));
                    fields.add(new ReportField(key, label));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return fields;
    }
        private com.google.api.services.drive.model.File saveFileTODrive(File file, String fileName) throws GeneralSecurityException, IOException {
        String folderId = "16PcoQcOUwOQyoZg55yrPWNWBx4DX1Qfx";
        Drive drive = createDriveService();
        com.google.api.services.drive.model.File fileMetaData =
                new com.google.api.services.drive.model.File();
        fileMetaData.setName(fileName);
        fileMetaData.setParents(Collections.singletonList(folderId));
        FileContent mediaContent = new FileContent("application/json", file);
        com.google.api.services.drive.model.File uploadFile =
                drive.files().create(fileMetaData,mediaContent).setFields("id").execute();
        String fileUrl = "https://drive.google.com/uc?export=view&id="+uploadFile.getId();
        log.info("File URL: "+fileUrl);
        return uploadFile;
    }

    public ReportDTO uploadFile(String userId) {
        try {
            List<Report> reports = reportRepository.findAll();
            if(!reports.stream().anyMatch(report -> report.getUserId().equals(userId))) {
                String fileName = "ReportForUserId" + userId;
                MultipartFile multipartFile = generateReport();
                if (multipartFile == null) {
                    throw new RuntimeException("Failed to generate report");
                }

                File file = convertMultipartFileToFile(multipartFile);

                com.google.api.services.drive.model.File uploadFile = saveFileTODrive(file, fileName);

                ReportDTO reportDTO = converter.SaveDataFileToDTO(fileName, uploadFile.getId(), userId);
                Report report = converter.ReportDTOToEntity(reportDTO);
                reportRepository.save(report);
                return reportDTO;
            }
            else {
                throw new RuntimeException("there are exist report in system");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("upload fail");
        }
    }

    @Override
    public Resource getFileFromDrive(String userId) {
        try {
            Report report = reportRepository.findByUserId(userId);

            Drive drive = createDriveService();
            com.google.api.services.drive.model.File file =
                    drive.files().get(report.getFileId()).execute();
            String fileName = file.getName();
            java.io.File downloadedFile = new java.io.File(fileName);
            OutputStream outputStream = new FileOutputStream(downloadedFile);
            drive.files().get(report.getFileId()).executeMediaAndDownloadTo(outputStream);
            outputStream.flush();
            outputStream.close();
            return new FileSystemResource(downloadedFile);
        } catch (Exception e) {
            log.error("Error downloading file from Drive: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String getFileJsonFromDrive(String userId) {
        try {
            Report report = reportRepository.findByUserId(userId);
            if (report == null) {
                throw new RuntimeException("Report not found for userId: " + userId);
            }
            Drive drive = createDriveService();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            drive.files().get(report.getFileId()).executeMediaAndDownloadTo(outputStream);

            String fileContent = outputStream.toString("UTF-8");

            outputStream.close();

            return fileContent;

        } catch (Exception e) {
            log.error("Error downloading file from Drive: " + e.getMessage(), e);
            return null;
        }
    }

    public static String getPathToGoodleCredentials(){
        String currentDirectory = System.getProperty("user.dir");
        Path fiPath = Paths.get(currentDirectory,"cred.json");
        return fiPath.toString();

    }

    public Drive createDriveService() throws GeneralSecurityException, IOException{
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .build();
    }
    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }
    public  Map<String, String> createAccountField(){
        Map<String, String> reportData = new HashMap<>();
        reportData.put("accountId", "Account Id");
        reportData.put("accountName", "Account Name");
        reportData.put("userId", "User Id");
        reportData.put("parentAccountId", "Parent Account Id");
        reportData.put("industryId", "Industry Id");
        reportData.put("accountTypeId", "Account Type Id");
        reportData.put("website", "Website");
        reportData.put("phone", "Phone");
        reportData.put("description", "Description");
        reportData.put("noEmployee", "No Employee");
        reportData.put("createdBy", "Created By");
        reportData.put("createDate", "Create Date");
        reportData.put("editDate", "Edit Date");
        reportData.put("editBy", "Edit By");
        reportData.put("isDeleted", "Is Deleted");
        return reportData;
    }
    public Map<String, String> createOpportunityField() {
        Map<String, String> reportData = new HashMap<>();
        reportData.put("opportunityId", "Opportunity Id");
        reportData.put("opportunityName", "Opportunity Name");
        reportData.put("accountId", "Account Id");
        reportData.put("userId", "User Id");
        reportData.put("probability", "Probability");
        reportData.put("nextStep", "Next Step");
        reportData.put("amount", "Amount");
        reportData.put("closeDate", "Close Date");
        reportData.put("primaryCampaignSourceId", "Primary Campaign Source Id");
        reportData.put("description", "Description");
        reportData.put("forecast", "Forecast");
        reportData.put("stage", "Stage");
        reportData.put("type", "Type");
        reportData.put("lastModifiedBy", "Last Modified By");
        reportData.put("leadSource", "Lead Source");
        reportData.put("createDate", "Create Date");
        reportData.put("editDate", "Edit Date");
        reportData.put("createBy", "Create By");
        reportData.put("isDeleted", "Is Deleted");
        return reportData;
    }
    public Map<String, String> createLeadField( ) {
        Map<String, String> reportData = new HashMap<>();
        reportData.put("leadId", "Lead Id");
        reportData.put("userId", "User Id");
        reportData.put("leadSource", "Lead Source");
        reportData.put("industryId", "Industry Id");
        reportData.put("leadStatus", "Lead Status");
        reportData.put("leadRating", "Lead Rating");
        reportData.put("leadSalution", "Lead Salution");
        reportData.put("firstName", "First Name");
        reportData.put("lastName", "Last Name");
        reportData.put("middleName", "Middle Name");
        reportData.put("gender", "Gender");
        reportData.put("title", "Title");
        reportData.put("email", "Email");
        reportData.put("phone", "Phone");
        reportData.put("website", "Website");
        reportData.put("company", "Company");
        reportData.put("noEmployee", "No Employee");
        reportData.put("createdBy", "Created By");
        reportData.put("createDate", "Create Date");
        reportData.put("editDate", "Edit Date");
        reportData.put("editBy", "Edit By");
        reportData.put("isDelete", "Is Delete");
        return reportData;
    }

}
