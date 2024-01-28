package com.gnoxmacroscode.mdsportalapi.controller;

import com.gnoxmacroscode.mdsportalapi.model.Staff;
import com.gnoxmacroscode.mdsportalapi.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1")
public class StaffController {

	@Autowired
	private StaffRepository staffRepo;

	@GetMapping("/staff")
	public List<Staff> getAllStaff(){
		return this.staffRepo.findAll();
	}

	@PreAuthorize("hasAnyAuthority('super_admin', 'admin')")
	@PostMapping("/staff")
	public Staff saveStaff(@RequestBody Staff staff) {
		return this.staffRepo.save(staff);
	}

	@PreAuthorize("hasAnyAuthority('super_admin', 'admin')")
	@DeleteMapping("/staff")
	public Staff deleteStaff(@RequestBody Staff staff) {
			Optional<Staff> staffFind = this.staffRepo.findById(staff.getId());
			staffFind.ifPresent(value -> this.staffRepo.delete((value)));
			return staff;
	}
}
