package com.gnoxmacroscode.mdsportalapi.repository;

import com.gnoxmacroscode.mdsportalapi.model.PatientRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gnoxmacroscode.mdsportalapi.model.Patient;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{
    @Query(value = "SELECT p FROM Patient p WHERE lower(p.name) like concat('%', :searchString,'%')")
    List<Patient> findPatient(@Param("searchString") String searchString);

    @Query(value = "SELECT p FROM Patient p WHERE p.name = :name AND p.dateOfBirth = :dateOfBirth")
    Patient findPatientByNameAndDateOfBirth(@Param("name") String name, @Param("dateOfBirth") String dateOfBirth);
}
