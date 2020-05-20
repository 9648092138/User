package com.op.eureka.user.controller;


import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.op.eureka.user.data.UserEntity;
import com.op.eureka.user.requestmodel.UserRequest;
import com.op.eureka.user.response.CreateuserResponse;
import com.op.eureka.user.services.UserService;
import com.op.eureka.user.shared.UserDTO;

@RestController
@RequestMapping("/users")
public class UserController {
	
	//========login api url ====> http://localhost:8081/users/users/login
	@Autowired
	private Environment env;
	@Autowired
	UserService userService;

	@GetMapping("/status/check")
	public String getUserDetail() {
		return "user Detail"+" working on port " +env.getProperty("local.server.port");
		
	}
	
	@PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<CreateuserResponse>createUser(@Valid @RequestBody UserRequest userRequest) {
		ModelMapper modelMapper = new ModelMapper();
		System.out.println(" "+env.getProperty("local.server.port"));
	    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	   
		UserDTO userDTO = modelMapper.map(userRequest,UserDTO.class);
		CreateuserResponse res = new CreateuserResponse();
		UserDTO createuser =	userService.createUser(userDTO);
		CreateuserResponse returnvalue  =modelMapper.map(createuser,CreateuserResponse.class);
		return  ResponseEntity.status(HttpStatus.CREATED).body(returnvalue);
		
	}
}
