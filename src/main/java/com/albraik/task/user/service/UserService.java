package com.albraik.task.user.service;

import com.albraik.task.user.model.UserEntity;

public interface UserService {

	UserEntity getUserDetails(String authorization);

}
