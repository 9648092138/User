package com.op.eureka.user.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.op.eureka.user.shared.UserDTO;

public interface UserService extends UserDetailsService{
	public UserDTO createUser(UserDTO userdetail);
	UserDTO getUserDetailsByEmail(String email);

}
