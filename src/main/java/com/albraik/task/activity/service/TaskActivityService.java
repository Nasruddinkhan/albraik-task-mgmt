package com.albraik.task.activity.service;

import java.util.List;

import com.albraik.task.activity.dto.TaskActivityRequestDTO;
import com.albraik.task.activity.dto.TaskReplyRequestDTO;
import com.albraik.task.activity.model.TaskActivityEntity;
import com.albraik.task.user.model.UserEntity;

public interface TaskActivityService {

	List<TaskActivityEntity> getAllTaskActivity();

	List<TaskActivityEntity> getMyTaskActivity(UserEntity userEntity);

	TaskActivityEntity createTaskActivity(UserEntity userEntity, TaskActivityRequestDTO taskActivityRequest);

	TaskActivityEntity createTaskReply(UserEntity userEntity, Long taskId, TaskReplyRequestDTO taskReplyRequestDto);

	TaskActivityEntity updateTaskReply(UserEntity userEntity, Long taskId, Long replyId,
			TaskReplyRequestDTO taskReplyRequestDto);
}
