package com.taskflow.service.impl;

import com.taskflow.dto.User.CreateUserRequest;
import com.taskflow.dto.User.UpdateUserRequest;
import com.taskflow.dto.User.UserResponse;
import com.taskflow.entity.Role;
import com.taskflow.entity.User;
import com.taskflow.enums.RoleType;
import com.taskflow.enums.UserStatus;
import com.taskflow.repository.RoleRepository;
import com.taskflow.repository.UserRepository;
import com.taskflow.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public UserResponse createUser(CreateUserRequest request) {
		// Basic validation
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new IllegalArgumentException("Email already in use");
		}
		if (userRepository.existsByEmployeeId(request.getEmployeeId())) {
			throw new IllegalArgumentException("Employee ID already in use");
		}

		// Resolve role
		RoleType roleType;
		try {
			roleType = RoleType.valueOf(request.getRole().toUpperCase());
		} catch (Exception ex) {
			throw new IllegalArgumentException("Invalid role: " + request.getRole());
		}

		Role role = roleRepository.findByName(roleType)
				.orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleType));

		// Build user entity
		User user = new User();
		user.setEmployeeId(request.getEmployeeId());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(role);
		user.setStatus(UserStatus.ACTIVE);

		User saved = userRepository.save(user);

		// Don't return password in response
		return new UserResponse(
				saved.getEmployeeId(),
				saved.getFirstName(),
				saved.getLastName(),
				saved.getEmail(),
				null,
				saved.getRole() != null ? saved.getRole().getName().name() : null
		);
	}

	@Override
	public UserResponse getUserByEmployeeId(String employeeId) {
		Optional<User> user = userRepository.findByEmployeeId(employeeId);
		if(user.isPresent()) {
			User u = user.get();
			return new UserResponse(
					u.getEmployeeId(),
					u.getFirstName(),
					u.getLastName(),
					u.getEmail(),
					null,
					u.getRole() != null ? u.getRole().getName().name() : null
			);
		}
		throw new EntityNotFoundException("User not found");
	}

	@Override
	public List<UserResponse> getAllUsers() {

		List<User> users = userRepository.findAll();

		return users.stream().map(user -> new UserResponse(
				user.getEmployeeId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				null,
				user.getRole() != null ? user.getRole().getName().name() : null
		)).collect(Collectors.toList());
	}

	@Override
	public UserResponse updateUserByEmployeeId(String employeeId, CreateUserRequest request) {
		Optional<User> userOpt = userRepository.findByEmployeeId(employeeId);
		if (!userOpt.isPresent()) {
			throw new EntityNotFoundException("User not found");
		}
		User user = userOpt.get();

		// Update user fields
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());

		// Encode new password if provided
		if (request.getPassword() != null && !request.getPassword().isEmpty()) {
			user.setPassword(passwordEncoder.encode(request.getPassword()));
		}

		User saved = userRepository.save(user);

		return new UserResponse(
				saved.getEmployeeId(),
				saved.getFirstName(),
				saved.getLastName(),
				saved.getEmail(),
				null,
				saved.getRole() != null ? saved.getRole().getName().name() : null
		);
	}

	@Override
	public UserResponse updateUserPartial(String employeeId, UpdateUserRequest request) {
		Optional<User> userOpt = userRepository.findByEmployeeId(employeeId);
		if (!userOpt.isPresent()) {
			throw new EntityNotFoundException("User not found");
		}
		User user = userOpt.get();

		// Update user fields if provided
		if (request.getEmployeeId() != null && !request.getEmployeeId().isEmpty()) {
			if (!request.getEmployeeId().equals(user.getEmployeeId()) && userRepository.existsByEmployeeId(request.getEmployeeId())) {
				throw new IllegalArgumentException("Employee ID already in use");
			}
			user.setEmployeeId(request.getEmployeeId());
		}

		if (request.getFirstName() != null && !request.getFirstName().isEmpty()) {
			user.setFirstName(request.getFirstName());
		}
		if (request.getLastName() != null && !request.getLastName().isEmpty()) {
			user.setLastName(request.getLastName());
		}
		if (request.getEmail() != null && !request.getEmail().isEmpty()) {
			user.setEmail(request.getEmail());
		}
		if (request.getPassword() != null && !request.getPassword().isEmpty()) {
			user.setPassword(passwordEncoder.encode(request.getPassword()));
		}
		if (request.getRole() != null && !request.getRole().isEmpty()) {
			Role role = roleRepository.findByName(RoleType.valueOf(request.getRole()))
					.orElseThrow(() -> new IllegalArgumentException("Role not found: " + request.getRole()));
			user.setRole(role);
		}

		User saved = userRepository.save(user);

		return new UserResponse(
				saved.getEmployeeId(),
				saved.getFirstName(),
				saved.getLastName(),
				saved.getEmail(),
				null,
				saved.getRole() != null ? saved.getRole().getName().name() : null
		);
	}

	@Override
	public void deleteUser(String employeeId) {

		Optional<User> userOpt = userRepository.findByEmployeeId(employeeId);
		if (!userOpt.isPresent()) {
			throw new EntityNotFoundException("User not found");
		}
		userRepository.delete(userOpt.get());
	}
}
