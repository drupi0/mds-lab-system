package com.gnoxmacroscode.mdsportalapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gnoxmacroscode.mdsportalapi.model.Staff;

import java.util.UUID;

@Repository
public interface StaffRepository extends JpaRepository<Staff, UUID> {

}
