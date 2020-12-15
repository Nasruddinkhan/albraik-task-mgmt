package com.albraik.task.activity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albraik.task.activity.dto.TaskActivityDTO;
import com.albraik.task.activity.dto.TaskActivityRequestDTO;
import com.albraik.task.activity.dto.TaskReplyRequestDTO;
import com.albraik.task.activity.model.TaskActivityEntity;
import com.albraik.task.activity.model.TaskStatus;
import com.albraik.task.activity.model.TaskType;
import com.albraik.task.activity.repository.TaskActivityRepo;
import com.albraik.task.cache.ObjectCache;
import com.albraik.task.exception.ResourceGoneException;
import com.albraik.task.exception.ResourceNotFoundException;
import com.albraik.task.exception.UnauthorizedAccessException;
import com.albraik.task.user.model.UserEntity;
import com.albraik.task.util.ObjectUtilMapper;

@Service
@Transactional
public class TaskActivityServiceImpl implements TaskActivityService {

	@Autowired
	private TaskActivityRepo taskActivityRepo;

	@Autowired
	@Qualifier("updatableTaskCache")
	private ObjectCache<Long, TaskActivityEntity> updatableTaskCache;

	@Value("${task.reply.edit.durationInMillis}")
	private Long REPLY_EDIT_DURATION;

	@Override
	public List<TaskActivityDTO> getAllTaskActivity() {
		List<TaskActivityEntity> taskActivityList = taskActivityRepo.findAll();
		return getTaskActivityDtoFromTaskActivityEntity(taskActivityList);
	}

	@Override
	public TaskActivityDTO createTaskActivity(UserEntity userEntity, TaskActivityRequestDTO taskActivityRequest) {
		TaskActivityEntity taskActivityEntity = ObjectUtilMapper.map(taskActivityRequest, TaskActivityEntity.class);
		taskActivityEntity.setAssignerId(userEntity.getId());
		taskActivityEntity.setAssignerName(userEntity.getFirstName());
		taskActivityEntity.setType(TaskType.TASK);
		taskActivityEntity.setStatus(TaskStatus.IN_PROGRESS);
		long currentTime = System.currentTimeMillis();
		taskActivityEntity.setCreatedTime(currentTime);
		taskActivityEntity.setUpdatedTime(currentTime);
		taskActivityEntity.setIsDeleted(false);
		if (taskActivityEntity.getIsHidden() == null)
			taskActivityEntity.setIsHidden(false);
		taskActivityEntity = taskActivityRepo.save(taskActivityEntity);
		return getTaskActivityDtoFromTaskActivityEntity(taskActivityEntity);
	}

	@Override
	public TaskActivityDTO createTaskReply(UserEntity userEntity, Long taskId,
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

		// save to db
		taskReply = taskActivityRepo.save(taskReply);
		taskActivityRepo.save(taskActivity);
		// save to cache
		updatableTaskCache.put(taskActivity.getId(), taskActivity);
		return getTaskActivityDtoFromTaskActivityEntity(taskReply);
	}

	@Override
	public TaskActivityDTO updateTaskReply(UserEntity userEntity, Long taskId, Long replyId,
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
		Long replyCreationTime = replyActivity.getCreatedTime();
		Long currentTime = System.currentTimeMillis();
		Long timeDiff = currentTime - replyCreationTime;
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

		// save to db
		replyActivity = taskActivityRepo.save(replyActivity);
		taskActivityRepo.save(taskActivity);
		// save to cache
		updatableTaskCache.put(taskActivity.getId(), taskActivity);
		return getTaskActivityDtoFromTaskActivityEntity(replyActivity);
	}

	@Override
	public List<TaskActivityDTO> getMyTaskActivity(UserEntity userEntity) {
		List<TaskActivityEntity> taskActivityList = taskActivityRepo.findByAssigneeIdOrAssignerId(userEntity.getId(),
				TaskType.TASK);
		return getTaskActivityDtoFromTaskActivityEntity(taskActivityList);
	}

	@Override
	public List<TaskActivityDTO> getProjectTaskActivity(UserEntity userEntity, Integer projectId) {
		List<TaskActivityEntity> taskActivityList = taskActivityRepo
				.findByProjectIdAndTypeAndIsDeletedFalseOrderByUpdatedTimeDesc(projectId, TaskType.TASK);
		List<TaskActivityEntity> filteredTaskList = filterTaskList(userEntity, taskActivityList);
		return getTaskActivityDtoFromTaskActivityEntity(filteredTaskList);
	}

	public List<TaskActivityEntity> filterTaskList(UserEntity userEntity, List<TaskActivityEntity> taskList) {
		List<TaskActivityEntity> output = new ArrayList<>();
		taskList.forEach(taskActivityEntity -> {
			if (taskActivityEntity.getIsHidden()) {
				Integer assignerId = taskActivityEntity.getAssignerId();
				Integer assigneeId = taskActivityEntity.getAssigneeId();
				if (assigneeId.equals(userEntity.getId()) || assignerId.equals(userEntity.getId()))
					output.add(taskActivityEntity);
			} else
				output.add(taskActivityEntity);
		});
		return output;
	}

	@Override
	public List<TaskActivityDTO> getTaskAssignedByMe(UserEntity userEntity) {
		List<TaskActivityEntity> taskActivityList = taskActivityRepo.getTaskAssignedByMe(userEntity.getId());
		return getTaskActivityDtoFromTaskActivityEntity(taskActivityList);
	}

	@Override
	public List<TaskActivityDTO> getTaskAssignedToMe(UserEntity userEntity) {
		List<TaskActivityEntity> taskActivityList = taskActivityRepo.getTaskAssignedToMe(userEntity.getId());
		return getTaskActivityDtoFromTaskActivityEntity(taskActivityList);
	}

	public List<TaskActivityDTO> getTaskActivityDtoFromTaskActivityEntity(List<TaskActivityEntity> taskActivityList) {
		List<TaskActivityDTO> output = new ArrayList<>();
		if (taskActivityList == null)
			return output;
		taskActivityList.forEach(taskActivity -> {
			output.add(getTaskActivityDtoFromTaskActivityEntity(taskActivity));
		});
		return output;
	}

	public TaskActivityDTO getTaskActivityDtoFromTaskActivityEntity(TaskActivityEntity taskActivity) {
		TaskActivityDTO taskDTO = ObjectUtilMapper.map(taskActivity, TaskActivityDTO.class);
		if (taskActivity.getTaskActivity() != null)
			taskDTO.setTaskId(taskActivity.getTaskActivity().getId());
		if (taskActivity.getTaskReplyActivity() != null)
			taskDTO.setTaskReply(getTaskActivityDtoFromTaskActivityEntity(taskActivity.getTaskReplyActivity()));
		if (taskActivity.getAttachmentList() != null)
			taskDTO.setAttachmentList(taskActivity.getAttachmentList());
		return taskDTO;
	}

}
