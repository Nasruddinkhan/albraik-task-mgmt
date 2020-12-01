package com.albraik.task.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.albraik.task.user.model.UserEntity;

@FeignClient(name = "infra", path = "/infra")
public interface UserFeignService {
	
	@GetMapping("/api/user/internal")
	UserEntity getUserDetails(@RequestHeader("Authorization") String authorization);
}
