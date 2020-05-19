package com.op.eureka.user.repository;

import org.springframework.data.repository.CrudRepository;

import com.op.eureka.user.data.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

}
