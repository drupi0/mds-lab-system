package com.gnoxmacroscode.mdsportalapi.repository;

import com.gnoxmacroscode.mdsportalapi.model.PatientRecord;
import com.gnoxmacroscode.mdsportalapi.interfaces.PatientRecordPartial;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.UUID;

@Repository
public interface PatientRecordRepository extends JpaRepository<PatientRecord, UUID>{

    @Transactional
    @Query(value = "SELECT pR FROM PatientRecord pR WHERE " +
            "lower(pR.patient.name) like concat('%', lower(:searchString),'%') " +
            "OR lower(pR.specNo) = lower(:searchString)  " +
            "OR lower(pR.ordered) like concat('%', lower(:searchString),'%') " +
            "OR lower(pR.specimen) like concat('%', lower(:searchString),'%') " +
            "OR pR.receivedDateTime like concat('%', lower(:searchString),'%') " +
            "OR pR.collectionDateTime like concat('%', lower(:searchString),'%')"
    )
    Page<PatientRecordPartial> findByRecordAttributes(@Param("searchString") String searchString, Pageable pageable);

    @Transactional
    @Query(value = "SELECT pR FROM PatientRecord pR WHERE pR.id = :uuid")
    Page<PatientRecordPartial> findByFormId(@Param("uuid") UUID uuid, Pageable pageable);

    @Transactional
    @Query(value = "SELECT pR FROM PatientRecord pR")
    Page<PatientRecordPartial> getAllRecords(Pageable pageable);
}