package com.op.eureka.user.services;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.op.eureka.user.data.UserEntity;
import com.op.eureka.user.repository.UserRepository;
import com.op.eureka.user.shared.UserDTO;

import antlr.collections.List;

@Service
public class UserServicImp implements UserService {

	UserRepository userRepository;
	BCryptPasswordEncoder bcryptPasswordEncoder;

	@Autowired
	public UserServicImp(UserRepository userRepository, BCryptPasswordEncoder bcryptPasswordEncoder) {

		this.userRepository = userRepository;
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
	}

	@Override
	public UserDTO createUser(UserDTO userdetail) {

		userdetail.setUserId(UUID.randomUUID().toString());
		userdetail.setEncryptedPassword(bcryptPasswordEncoder.encode(userdetail.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = modelMapper.map(userdetail, UserEntity.class);
	
		userRepository.save(userEntity);
		UserDTO returnValue = modelMapper.map(userEntity, UserDTO.class);
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	UserEntity userEntity	=userRepository.findByEmail(username);
	if(userEntity == null) throw new UsernameNotFoundException(username);
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),true,true,true,true,new ArrayList<>());
	}

	@Override
	public UserDTO getUserDetailsByEmail(String email) {
		UserEntity userEntity	=userRepository.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		return new ModelMapper().map(userEntity, UserDTO.class);
	}

}
