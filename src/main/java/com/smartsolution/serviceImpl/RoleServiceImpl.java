package com.smartsolution.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartsolution.model.Role;
import com.smartsolution.repository.RoleRepository;
import com.smartsolution.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role createRole(String roleName) {
		
		Optional<Role> existing = roleRepository.findByName(roleName);
		if (existing.isPresent()) {
			throw new IllegalArgumentException("Role already exists");
		}
		Role role = new Role();
		role.setName(roleName);
		return roleRepository.save(role);
	}
	
	

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

}
