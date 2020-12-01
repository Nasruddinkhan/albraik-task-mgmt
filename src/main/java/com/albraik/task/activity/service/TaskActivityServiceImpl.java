package com.albraik.task.activity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albraik.task.activity.dto.TaskActivityDTO;
import com.albraik.task.activity.dto.TaskActivityRequestDTO;
import com.albraik.task.activity.dto.TaskReplyRequestDTO;
import com.albraik.task.activity.model.TaskActivityEntity;
import com.albraik.task.activity.model.TaskPriority;
import com.albraik.task.activity.model.TaskStatus;
import com.albraik.task.activity.model.TaskType;
import com.albraik.task.activity.repository.TaskActivityRepo;
import com.albraik.task.exception.ResourceGoneException;
import com.albraik.task.exception.ResourceNotFoundException;
import com.albraik.task.exception.UnauthorizedAccessException;
import com.albraik.task.user.model.UserEntity;
import com.albraik.task.user.service.UserService;
import com.albraik.task.util.ObjectUtilMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

@Service
@Transactional
public class TaskActivityServiceImpl implements TaskActivityService {

	@Autowired
	private TaskActivityRepo taskActivityRepo;

	@Value("${task.reply.edit.durationInMillis}")
	private Long REPLY_EDIT_DURATION;

	@Override
	public List<TaskActivityEntity> getAllTaskActivity() {
		List<TaskActivityEntity> taskActivityList = taskActivityRepo.findAll();
		return taskActivityList;
	}

	@Override
	public TaskActivityEntity createTaskActivity(UserEntity userEntity, TaskActivityRequestDTO taskActivityRequest) {
		TaskActivityEntity taskActivityEntity = ObjectUtilMapper.map(taskActivityRequest, TaskActivityEntity.class);
		taskActivityEntity.setAssignerId(userEntity.getId());
		taskActivityEntity.setAssignerName(userEntity.getFirstName());
		taskActivityEntity.setType(TaskType.TASK);
		taskActivityEntity.setStatus(TaskStatus.IN_PROGRESS);
		long currentTime = System.currentTimeMillis();
		taskActivityEntity.setCreatedTime(currentTime);
		taskActivityEntity.setUpdatedTime(currentTime);
		taskActivityEntity.setIsDeleted(false);
		taskActivityEntity = taskActivityRepo.save(taskActivityEntity);
		return taskActivityEntity;
	}

	@Override
	public TaskActivityEntity createTaskReply(UserEntity userEntity, Long taskId,
			TaskReplyRequestDTO taskReplyRequestDto) {
		TaskActivityEntity taskActivity = taskActivityRepo.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("No such task found"));
		if (!taskActivity.getAssigneeId().equals(userEntity.getId()))
			throw new UnauthorizedAccessException("You are not authorized to reply to this task");
		TaskActivityEntity taskReply = ObjectUtilMapper.map(taskReplyRequestDto, TaskActivityEntity.class);
		taskReply.setProjectId(taskActivity.getProjectId());
		taskReply.setProjectName(taskActivity.getProjectName());
		taskReply.setAssigneeId(taskActivity.getAssignerId());
		taskReply.setAssigneeName(taskActivity.getAssignerName());
		taskReply.setAssignerId(userEntity.getId());
		taskReply.setAssignerName(userEntity.getFirstName());
		taskReply.setType(TaskType.REPLY);
		long currentTime = System.currentTimeMillis();
		taskReply.setCreatedTime(currentTime);
		taskReply.setUpdatedTime(currentTime);
		taskReply.setIsDeleted(false);

		Long dueDate = taskActivity.getDueDate();
		if (currentTime > dueDate) {
			taskActivity.setStatus(TaskStatus.LATE_COMPLETED);
			taskReply.setStatus(TaskStatus.LATE_COMPLETED);
		} else {
			taskActivity.setStatus(TaskStatus.COMPLETED);
			taskReply.setStatus(TaskStatus.COMPLETED);
		}
		taskActivity.setUpdatedTime(currentTime);
		taskReply.setTaskActivity(taskActivity);

		taskReply = taskActivityRepo.save(taskReply);
		taskActivityRepo.save(taskActivity);
		return taskReply;
	}

	@Override
	public TaskActivityEntity updateTaskReply(UserEntity userEntity, Long taskId, Long replyId,
			TaskReplyRequestDTO taskReplyRequestDto) {
		TaskActivityEntity taskActivity = taskActivityRepo.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("No such task found"));
		TaskActivityEntity replyActivity = taskActivityRepo.findById(replyId)
				.orElseThrow(() -> new ResourceNotFoundException("No such reply found"));
		if (!replyActivity.getTaskActivity().getId().equals(taskActivity.getId()))
			throw new ResourceNotFoundException("No such reply found");
		if (!taskActivity.getAssigneeId().equals(userEntity.getId()))
			throw new UnauthorizedAccessException("You are not authorized to update reply of this task");
		if (!replyActivity.getAssignerId().equals(userEntity.getId()))
			throw new UnauthorizedAccessException("You are not authorized to update this reply");
		Long taskCreationTime = taskActivity.getCreatedTime();
		Long currentTime = System.currentTimeMillis();
		Long timeDiff = currentTime - taskCreationTime;
		if (timeDiff > REPLY_EDIT_DURATION)
			throw new ResourceGoneException("Task update time expired");

		ObjectUtilMapper.map(taskReplyRequestDto, replyActivity);
		replyActivity.setUpdatedTime(currentTime);
		taskActivity.setUpdatedTime(currentTime);

		Long dueDate = taskActivity.getDueDate();
		if (currentTime > dueDate) {
			taskActivity.setStatus(TaskStatus.LATE_COMPLETED);
			replyActivity.setStatus(TaskStatus.LATE_COMPLETED);
		} else {
			taskActivity.setStatus(TaskStatus.COMPLETED);
			replyActivity.setStatus(TaskStatus.COMPLETED);
		}
		replyActivity.setTaskActivity(taskActivity);

		replyActivity = taskActivityRepo.save(replyActivity);
		taskActivityRepo.save(taskActivity);
		return replyActivity;
	}

	@Override
	public List<TaskActivityEntity> getMyTaskActivity(UserEntity userEntity) {
		return taskActivityRepo.findByAssigneeIdOrAssignerId(userEntity.getId(), TaskType.TASK);
	}
	
	/*
	 * public List<TaskActivityDTO>
	 * getTaskActivityDtoFromTaskActivityEntity(List<TaskActivityEntity>
	 * taskActivityList) { List<TaskActivityDTO> output = new ArrayList<>();
	 * if(taskActivityList == null) return output;
	 * taskActivityList.forEach(taskActivity -> { TaskActivityDTO taskDTO =
	 * ObjectUtilMapper.map(taskActivity, TaskActivityDTO.class); }); return output;
	 * }
	 */

}
