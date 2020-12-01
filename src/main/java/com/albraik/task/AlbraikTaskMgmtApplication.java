package com.albraik.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.albraik.task.user.service"})
@SpringBootApplication
public class AlbraikTaskMgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlbraikTaskMgmtApplication.class, args);
	}

}
