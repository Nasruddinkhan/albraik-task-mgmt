package com.albraik.task.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.albraik.task.activity.model.TaskActivityEntity;
import com.albraik.task.activity.repository.TaskActivityRepo;

@Component("updatableTaskCache")
public class UpdatableTaskCache implements ObjectCache<Long, TaskActivityEntity> {

	private Map<Long, TaskActivityEntity> updatableTaskMap = new HashMap<Long, TaskActivityEntity>();

	@Autowired
	private TaskActivityRepo taskActivityRepo;

	@Override
	public void load() {
		updatableTaskMap.clear();
		List<TaskActivityEntity> taskList = taskActivityRepo.getUpdatableTaskList();
		taskList.forEach(task -> {
			updatableTaskMap.put(task.getId(), task);
		});
	}

	@Override
	public Map<Long, TaskActivityEntity> getCacheData() {
		return updatableTaskMap;
	}

	@Override
	public TaskActivityEntity get(Long key) {
		return updatableTaskMap.get(key);
	}

	@Override
	public void put(Long key, TaskActivityEntity value) {
		updatableTaskMap.put(key, value);
	}

	@Override
	public void remove(Long key) {
		updatableTaskMap.remove(key);
	}

}
