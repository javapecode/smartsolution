package com.smartsolution.service;

import java.util.List;

import com.smartsolution.model.Role;

public interface RoleService {
	Role createRole(String roleName);

	List<Role> getAllRoles();
}