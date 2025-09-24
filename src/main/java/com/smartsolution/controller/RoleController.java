package com.smartsolution.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartsolution.model.Role;
import com.smartsolution.service.RoleService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/roles")
@RequiredArgsConstructor
@Validated
public class RoleController {

	private final RoleService roleService;

	@Data
	static class RoleRequest {
		@NotBlank(message = "Role name must not be blank")
		private String name;
	}

	@PostMapping("/create")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Role> createRole(@Valid @RequestBody RoleRequest req) {
		Role created = roleService.createRole(req.getName().toUpperCase());
		return ResponseEntity.ok(created);
	}

	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<String>> listRoles() {
		List<String> names = roleService.getAllRoles().stream().map(Role::getName).collect(Collectors.toList());
		return ResponseEntity.ok(names);
	}
}
