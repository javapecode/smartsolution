package com.smartsolution.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smartsolution.dto.UserDto;
import com.smartsolution.model.Role;
import com.smartsolution.model.User;
import com.smartsolution.repository.RoleRepository;
import com.smartsolution.repository.UserRepository;
import com.smartsolution.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Value("${file.upload-dir}")
	private String uploadDir;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto userDto, MultipartFile profileImagePath) {
		try {
			// Save image file
			String fileName = System.currentTimeMillis() + "_" + profileImagePath.getOriginalFilename();
			Path uploadPath = Paths.get(uploadDir);
			System.out.println("-------------" + uploadDir);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			Path filePath = uploadPath.resolve(fileName);
			Files.copy(profileImagePath.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			// Create user entity
			User user = new User();
			user.setName(userDto.getName());
			user.setUsername(userDto.getUsername());
			user.setPassword(userDto.getPassword()); // ⚠️ Use password encoder in production
			user.setProfileImagePath(filePath.toString());
			user.setCreatedBy("system");
			user.setUpdatedBy("system");

			// Set roles if provided
			if (userDto.getRoleIds() != null && !userDto.getRoleIds().isEmpty()) {
				Set<Role> roles = new HashSet<>();
				for (Long roleId : userDto.getRoleIds()) {
					Role role = roleRepository.findById(roleId)
							.orElseThrow(() -> new RuntimeException("Role not found: " + roleId));
					roles.add(role);
				}
				user.setRoles(roles);
			}

			User savedUser = userRepository.save(user);
			return mapToDto(savedUser);

		} catch (IOException e) {
			throw new RuntimeException("Failed to save user or upload image", e);
		}
	}

	public UserDto createUser(UserDto userDto) {
		User user = new User();
		user.setName(userDto.getName());
		user.setUsername(userDto.getUsername());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setCreatedBy("system");
		User savedUser = userRepository.save(user);
		return mapToDto(savedUser);
	}

	private UserDto mapToDto(User user) {
		UserDto dto = new UserDto();
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setUsername(user.getUsername());
		dto.setPassword(user.getPassword());
		dto.setProfileImagePath(user.getProfileImagePath());
		return dto; // Password is not included in DTO
	}
}
