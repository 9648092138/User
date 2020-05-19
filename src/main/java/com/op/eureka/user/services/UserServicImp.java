package com.op.eureka.user.services;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.op.eureka.user.data.UserEntity;
import com.op.eureka.user.repository.UserRepository;
import com.op.eureka.user.shared.UserDTO;

@Service
public class UserServicImp implements UserService{
	
  UserRepository userRepository;
  
  @Autowired
	public UserServicImp(UserRepository userRepository) {
	
	this.userRepository = userRepository;
}

	@Override
	public UserDTO createUser(UserDTO userdetail) {
		
    userdetail.setUserId(UUID.randomUUID().toString());
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    UserEntity userEntity = modelMapper.map(userdetail,UserEntity.class);
    userEntity.setEncryptedPassword("test2");
    userRepository.save(userEntity);
		return null;
	}

}
