package com.albraik.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.albraik.task.activity.model.TaskActivityEntity;
import com.albraik.task.cache.ObjectCache;

@EnableEurekaClient
@EnableFeignClients(basePackages = { "com.albraik.task.user.service" })
@EnableScheduling
@SpringBootApplication
public class AlbraikTaskMgmtApplication implements ApplicationListener<ApplicationStartedEvent> {

	@Autowired
	@Qualifier("updatableTaskCache")
	private ObjectCache<Long, TaskActivityEntity> updatableTaskCache;

	public static void main(String[] args) {
		SpringApplication.run(AlbraikTaskMgmtApplication.class, args);
	}

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		System.out.println("Loading cache...");
		loadCaches();
	}

	private void loadCaches() {
		updatableTaskCache.load();
		System.out.println("UPDATABLE_TASK_CACHE: SIZE - " + updatableTaskCache.getCacheData().size());
	}

}
