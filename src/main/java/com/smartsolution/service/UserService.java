package com.smartsolution.service;

import com.smartsolution.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
  
	UserDto createUser(UserDto userDto, MultipartFile profileImagePath);

	
}