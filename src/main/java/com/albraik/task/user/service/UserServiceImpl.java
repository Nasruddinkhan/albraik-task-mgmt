package com.albraik.task.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albraik.task.user.model.UserEntity;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserFeignService userFeignService;

	
	@Override
	public UserEntity getUserDetails(String authorization) {
		
		return userFeignService.getUserDetails(authorization);
	}


}
