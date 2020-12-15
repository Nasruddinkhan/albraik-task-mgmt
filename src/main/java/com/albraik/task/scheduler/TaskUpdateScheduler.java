package com.albraik.task.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.albraik.task.activity.model.TaskActivityEntity;
import com.albraik.task.activity.model.TaskStatus;
import com.albraik.task.activity.repository.TaskActivityRepo;
import com.albraik.task.cache.ObjectCache;

@Component
public class TaskUpdateScheduler {

	@Autowired
	@Qualifier("updatableTaskCache")
	private ObjectCache<Long, TaskActivityEntity> updatableTaskCache;

	@Autowired
	private TaskActivityRepo taskActivityRepo;

	@Value("${task.reply.edit.durationInMillis}")
	private Long REPLY_EDIT_DURATION;

	@Scheduled(fixedRate = 60000)
	public void runEveryMinute() {
		try {

			checkAndUpdateFinishedTasks();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkAndUpdateFinishedTasks() {
		List<Long> taskIdToBeRemovedFromCache = new ArrayList<>();
		List<TaskActivityEntity> taskToBeUpdated = new ArrayList<>();
		Long currentTime = System.currentTimeMillis();
		updatableTaskCache.getCacheData().values().forEach(task -> {
			TaskActivityEntity replyEntity = task.getTaskReplyActivity();
			Long firstReplyTime = replyEntity.getCreatedTime();
			Long timeDiff = currentTime - firstReplyTime;
			// mark task status as finished if reply edit duration ends
			if (timeDiff > REPLY_EDIT_DURATION) {
				// update status
				task.setStatus(TaskStatus.FINISHED);
				replyEntity.setStatus(TaskStatus.FINISHED);
				// update time
				task.setUpdatedTime(currentTime);
				replyEntity.setUpdatedTime(currentTime);
				// add to list for updation
				taskToBeUpdated.add(task);
				taskToBeUpdated.add(replyEntity);
				// add to list for deletion from cache
				taskIdToBeRemovedFromCache.add(task.getId());
			}
		});
		// save updated tasks in db
		if (!taskToBeUpdated.isEmpty())
			taskActivityRepo.saveAll(taskToBeUpdated);

		// remove finished tasks from cache
		taskIdToBeRemovedFromCache.forEach(taskId -> {
			updatableTaskCache.remove(taskId);
		});
		System.out.println(new Date() + " - " + this.getClass().getSimpleName() + " - Task Ids marked as Finished: "
				+ taskIdToBeRemovedFromCache.toString());
	}

}
