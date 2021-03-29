package com.albraik.task.activity.service;

import java.util.List;

import com.albraik.task.activity.dto.TaskActivityDTO;
import com.albraik.task.activity.dto.TaskActivityRequestDTO;
import com.albraik.task.activity.dto.TaskReplyRequestDTO;
import com.albraik.task.activity.model.TaskActivityEntity;
import com.albraik.task.user.model.UserEntity;

public interface TaskActivityService {

	List<TaskActivityDTO> getAllTaskActivity();

	List<TaskActivityDTO> getMyTaskActivity(UserEntity userEntity);

	List<TaskActivityDTO> getProjectTaskActivity(UserEntity userEntity, Integer projectId);

	TaskActivityDTO createTaskActivity(UserEntity userEntity, TaskActivityRequestDTO taskActivityRequest);

	TaskActivityDTO createTaskReply(UserEntity userEntity, Long taskId, TaskReplyRequestDTO taskReplyRequestDto);

	TaskActivityDTO updateTaskReply(UserEntity userEntity, Long taskId, Long replyId,
			TaskReplyRequestDTO taskReplyRequestDto);

	List<TaskActivityDTO> getTaskAssignedToMe(UserEntity userEntity);

	List<TaskActivityDTO> getTaskAssignedByMe(UserEntity userEntity);
}
