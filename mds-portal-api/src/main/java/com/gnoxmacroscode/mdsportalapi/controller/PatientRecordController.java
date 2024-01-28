package com.gnoxmacroscode.mdsportalapi.controller;

import com.gnoxmacroscode.mdsportalapi.model.Patient;
import com.gnoxmacroscode.mdsportalapi.model.PatientRecord;
import com.gnoxmacroscode.mdsportalapi.interfaces.PatientRecordPartial;
import com.gnoxmacroscode.mdsportalapi.repository.PatientRecordRepository;
import com.gnoxmacroscode.mdsportalapi.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1")
public class PatientRecordController {
    @Value("${encryption.seed}")
    private String seed;

    @Autowired
    private PatientRecordRepository recordRepo;

    @Autowired
    private PatientRepository patientRepo;

    @GetMapping("/record")
    public Page<PatientRecordPartial> getAllRecords(@RequestParam Integer pageNumber,
                                                    @RequestParam Integer pageSize, @RequestParam String sortKeys,
                                                    @RequestParam String sortBy){

        if(sortKeys.isEmpty()) {
            sortKeys = "receivedDateTime,collectionDateTime";
        }

        sortKeys = sortKeys.replaceAll("\\breceivedDateTime\\b", "receivedDateTimeLong");
        sortKeys = sortKeys.replaceAll("\\bcollectionDateTime\\b", "collectionDateTimeLong");

        System.out.println(sortKeys);

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize,
                Sort.by(sortBy.startsWith("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortKeys.split(",")));

        return this.recordRepo.getAllRecords(pageRequest);
    }

    @GetMapping("/record/search")
    public Page<PatientRecordPartial> getRecordQuery(@RequestParam Integer pageNumber,
                                                     @RequestParam Integer pageSize, @RequestParam String query,
                                                     @RequestParam String sortKeys,
                                                     @RequestParam String sortBy){
        if(sortKeys.isEmpty()) {
            sortKeys = "receivedDateTime,collectionDateTime";
        }


        sortKeys = sortKeys.replaceAll("\\breceivedDateTime\\b", "receivedDateTimeLong");
        sortKeys = sortKeys.replaceAll("\\bcollectionDateTime\\b", "collectionDateTimeLong");

        System.out.println(sortKeys);

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize,
                Sort.by(sortBy.startsWith("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
                        sortKeys.split(",")));

        try {
            UUID searchUUID = UUID.fromString(query);
            return this.recordRepo.findByFormId(searchUUID, pageRequest);
        } catch (Exception e) {
            return this.recordRepo.findByRecordAttributes(query, pageRequest);
        }
    }

    @GetMapping("/record/{formId}")
    public PatientRecord getRecord(@PathVariable String formId) {
        return this.recordRepo.findById(UUID.fromString(formId)).orElse(null);
    }

    @PostMapping("/record")
    public PatientRecord saveRecord(@RequestBody PatientRecord patientRecord) throws Exception {

        Optional<Patient> existPatient = Optional.ofNullable(this.patientRepo.
                findPatientByNameAndDateOfBirth(patientRecord.getPatient().getName(),
                        patientRecord.getPatient().getDateOfBirth()));

        if(existPatient.isEmpty()) {
            this.patientRepo.save(patientRecord.getPatient());
        } else {
            patientRecord.setPatient(existPatient.get());
        }

        return this.recordRepo.save(patientRecord);
    }

    @PreAuthorize("hasAnyAuthority('super_admin', 'admin')")
    @DeleteMapping("/record")
    public PatientRecord deleteRecord(@RequestBody Map<String, String> formId) throws Exception {
        UUID formUUID = UUID.fromString(formId.get("formId"));
        Optional<PatientRecord> recordToDelete = this.recordRepo.findById(formUUID);

        if(recordToDelete.isEmpty()) {
            throw new Exception("Record with " + formId + " not found");
        }

        this.recordRepo.delete(recordToDelete.get());

        return recordToDelete.get();
    }


    @GetMapping("/patient")
    public List<Patient> findPatient(@RequestParam String query) {
        return this.patientRepo.findPatient(query);
    }
}
