package fpt.capstone.iReport.controller;

import fpt.capstone.iReport.dto.response.ResponseData;
import fpt.capstone.iReport.dto.response.ResponseError;
import fpt.capstone.iReport.service.impl.RecycleBinServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/recycle-bin")
public class RecycleBinController {
    private final RecycleBinServiceImpl recycleBinService;
    @GetMapping("/get-delete-leads-list")
    public ResponseData<?> GetDeleteLeads(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                    @RequestParam(defaultValue = "20", required = false) int pageSize) {
        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String userId = authentication.getName();
            return new ResponseData<>(1, HttpStatus.OK.value(),
                    recycleBinService.getAllDeleteLeads(pageNo, pageSize));
        } catch (Exception e) {
            return new ResponseError(0, HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @GetMapping("/get-delete-account-list")
    public ResponseData<?> GetDeleteAccount(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                    @RequestParam(defaultValue = "20", required = false) int pageSize) {
        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String userId = authentication.getName();
            return new ResponseData<>(1, HttpStatus.OK.value(),
                    recycleBinService.getAllDeleteAccount(pageNo, pageSize));
        } catch (Exception e) {
            return new ResponseError(0, HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @GetMapping("/get-delete-opportunity-list")
    public ResponseData<?> GetDeleteOpportunity(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                    @RequestParam(defaultValue = "20", required = false) int pageSize) {
        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String userId = authentication.getName();
            return new ResponseData<>(1, HttpStatus.OK.value(),
                    recycleBinService.getAllDeleteOpportunity(pageNo, pageSize));
        } catch (Exception e) {
            return new ResponseError(0, HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @GetMapping("/get-delete-contact-list")
    public ResponseData<?> GetDeleteContact(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                @RequestParam(defaultValue = "20", required = false) int pageSize) {
        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String userId = authentication.getName();
            return new ResponseData<>(1, HttpStatus.OK.value(),
                    recycleBinService.getAllDeleteContact(pageNo, pageSize));
        } catch (Exception e) {
            return new ResponseError(0, HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @GetMapping("/get-delete-emailTemplate-list")
    public ResponseData<?> GetDeleteEmailTemplate(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                @RequestParam(defaultValue = "20", required = false) int pageSize) {
        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String userId = authentication.getName();
            return new ResponseData<>(1, HttpStatus.OK.value(),
                    recycleBinService.getAllDeleteEmailTemplate(pageNo, pageSize));
        } catch (Exception e) {
            return new ResponseError(0, HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @DeleteMapping("/delete-leads/{id}")
    public ResponseData<?> deleteLead(@PathVariable(name = "id") Long id) {
        return recycleBinService.DeleteLeads(id) ?
                new ResponseData<>(HttpStatus.OK.value(), "Delete Leads success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }

    @DeleteMapping("/delete-account/{id}")
    public ResponseData<?> deleteAccount(@PathVariable(name = "id") Long id) {
        return recycleBinService.DeleteAccount(id) ?
                new ResponseData<>(HttpStatus.OK.value(), "Delete Account success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }

    @DeleteMapping("/delete-opportunity/{id}")
    public ResponseData<?> deleteOpportunity(@PathVariable(name = "id") Long id) {
        return recycleBinService.DeleteOpportunity(id) ?
                new ResponseData<>(HttpStatus.OK.value(), "Delete Opportunity success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }

    @DeleteMapping("/delete-contact/{id}")
    public ResponseData<?> deleteContact(@PathVariable(name = "id") Long id) {
        return recycleBinService.DeleteContact(id) ?
                new ResponseData<>(HttpStatus.OK.value(), "Delete Contact success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }

    @DeleteMapping("/delete-emailTemplate/{id}")
    public ResponseData<?> deleteEmailTemplate(@PathVariable(name = "id") Long id) {
        return recycleBinService.DeleteEmailTemplate(id) ?
                new ResponseData<>(HttpStatus.OK.value(), "Delete EmailTemplate success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }

    @PutMapping("/restore-lead/{id}")
    public ResponseData<?> RestoreLeads(@PathVariable(name = "id") Long id) {
        return recycleBinService.RestoreLeads(id) ?
                new ResponseData<>(HttpStatus.OK.value(), "restore Lead success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }

    @PutMapping("/restore-account/{id}")
    public ResponseData<?> RestoreAccount(@PathVariable(name = "id") Long id) {
        return recycleBinService.RestoreAccount(id) ?
                new ResponseData<>(HttpStatus.OK.value(), "restore Account success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }

    @PutMapping("/restore-opportunity/{id}")
    public ResponseData<?> RestoreOpportunity(@PathVariable(name = "id") Long id) {
        return recycleBinService.RestoreOpportunity(id) ?
                new ResponseData<>(HttpStatus.OK.value(), "restore Opportunity success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }

    @PutMapping("/restore-emailtemplate/{id}")
    public ResponseData<?> RestoreEmailtemplate(@PathVariable(name = "id") Long id) {
        return recycleBinService.RestoreEmailTemplate(id) ?
                new ResponseData<>(HttpStatus.OK.value(), "restore Emailtemplate success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }

    @PutMapping("/restore-contact/{id}")
    public ResponseData<?> RestoreContact(@PathVariable(name = "id") Long id) {
        return recycleBinService.RestoreContact(id) ?
                new ResponseData<>(HttpStatus.OK.value(), "restore Contact success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }


    @PutMapping("/restore-list-lead")
    public ResponseData<?> RestoreListLeads(@RequestBody List<Long> listLeadsId) {
        return recycleBinService.RestoreListLeads(listLeadsId) ?
                new ResponseData<>(HttpStatus.OK.value(), "restore List Lead success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }

    @PutMapping("/restore-list-account")
    public ResponseData<?> RestoreListAccount(@RequestBody List<Long> listAccount) {
        return recycleBinService.RestoreListAccount(listAccount) ?
                new ResponseData<>(HttpStatus.OK.value(), "restore List Account success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }

    @PutMapping("/restore-list-opportunity")
    public ResponseData<?> RestoreListOpportunity(@RequestBody List<Long> listOpportunity) {
        return recycleBinService.RestoreListOpportunity(listOpportunity) ?
                new ResponseData<>(HttpStatus.OK.value(), "restore List Opportunity success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }

    @PutMapping("/restore-list-emailtemplate")
    public ResponseData<?> RestoreListEmailtemplate(@RequestBody List<Long> listEmailTemplate) {
        return recycleBinService.RestoreListEmailTemplate(listEmailTemplate) ?
                new ResponseData<>(HttpStatus.OK.value(), "restore List Emailtemplate success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }

    @PutMapping("/restore-list-contact")
    public ResponseData<?>  RestoreListContact(@RequestBody List<Long> listContact) {
        return recycleBinService.RestoreListContact(listContact) ?
                new ResponseData<>(HttpStatus.OK.value(), "restore List Contact success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }

    @DeleteMapping("/delete-list-lead")
    public ResponseData<?> DeleteListLeads(@RequestBody List<Long> listLeadsId) {
        return recycleBinService.DeleteListLeads(listLeadsId) ?
                new ResponseData<>(HttpStatus.OK.value(), "Delete List Lead success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }

    @DeleteMapping("/delete-list-account")
    public ResponseData<?> DeleteListAccount(@RequestBody List<Long> listAccount) {
        return recycleBinService.DeletelistAccount(listAccount) ?
                new ResponseData<>(HttpStatus.OK.value(), "Delete List Account success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }

    @DeleteMapping("/delete-list-opportunity")
    public ResponseData<?> DeleteListOpportunity(@RequestBody List<Long> listOpportunity) {
        return recycleBinService.DeletelistOpportunity(listOpportunity) ?
                new ResponseData<>(HttpStatus.OK.value(), "Delete List Opportunity success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }

    @DeleteMapping("/delete-list-emailtemplate")
    public ResponseData<?> DeleteListEmailtemplate(@RequestBody List<Long> listEmailTemplate) {
        return recycleBinService.DeletelistEmailTemplate(listEmailTemplate) ?
                new ResponseData<>(HttpStatus.OK.value(), "Delete List Emailtemplate success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }

    @DeleteMapping("/delete-list-contact")
    public ResponseData<?> DeleteListContact(@RequestBody List<Long> listContact) {
        return recycleBinService.DeletelistContactt(listContact) ?
                new ResponseData<>(HttpStatus.OK.value(), "Delete List Contact success", 1) :
                new ResponseError(0, HttpStatus.BAD_REQUEST.value(), "Delete Leads fail");

    }


}
