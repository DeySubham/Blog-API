package com.example.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.example.blog.entities.User;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.UserDto;
import com.example.blog.repositories.UserRepo;
import com.example.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto user) {
		User savedUser = userRepo.save(dtoToUser(user));
		return userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updatedUser = userRepo.save(user);
		return userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		return userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user ->this.userToDto(user)).collect(Collectors.toList());

		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		userRepo.delete(user);
	}

	private User dtoToUser(UserDto userDto) {
		return modelMapper.map(userDto, User.class);
	}
	
	private UserDto userToDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}
}
