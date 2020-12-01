package com.albraik.task.activity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albraik.task.activity.dto.TaskActivityRequestDTO;
import com.albraik.task.activity.dto.TaskReplyRequestDTO;
import com.albraik.task.activity.model.TaskActivityEntity;
import com.albraik.task.activity.service.TaskActivityService;
import com.albraik.task.user.model.UserEntity;
import com.albraik.task.user.service.UserService;

@RestController
@RequestMapping("/api/project/task")
public class TaskActivityController {

	@Autowired
	private TaskActivityService taskActivityService;

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<TaskActivityEntity>> getMyTaskActivity(@RequestHeader("authorization") String authorization) {
		UserEntity userEntity = userService.getUserDetails(authorization);
		List<TaskActivityEntity> taskActivityList = taskActivityService.getMyTaskActivity(userEntity);
		if (taskActivityList.isEmpty())
			return new ResponseEntity<>(taskActivityList, HttpStatus.NO_CONTENT);
		return ResponseEntity.ok(taskActivityList);
	}

	@PostMapping
	public ResponseEntity<TaskActivityEntity> createTask(@RequestHeader("authorization") String authorization,
			TaskActivityRequestDTO taskActivityRequestDTO) {
		UserEntity userEntity = userService.getUserDetails(authorization);
		TaskActivityEntity taskActivityEntity = taskActivityService.createTaskActivity(userEntity,
				taskActivityRequestDTO);
		return new ResponseEntity<TaskActivityEntity>(taskActivityEntity, HttpStatus.CREATED);
	}

	@PostMapping("/{taskId}/reply")
	public ResponseEntity<TaskActivityEntity> createTaskReply(@RequestHeader("authorization") String authorization,
			@PathVariable("taskId") Long taskId, TaskReplyRequestDTO taskReplyRequestDto) {
		UserEntity userEntity = userService.getUserDetails(authorization);
		TaskActivityEntity taskActivityEntity = taskActivityService.createTaskReply(userEntity, taskId,
				taskReplyRequestDto);
		return new ResponseEntity<TaskActivityEntity>(taskActivityEntity, HttpStatus.CREATED);
	}

	@PutMapping("/{taskId}/reply/{replyId}")
	public ResponseEntity<TaskActivityEntity> updateTaskReply(@RequestHeader("authorization") String authorization,
			@PathVariable("taskId") Long taskId, @PathVariable("replyId") Long replyId,
			TaskReplyRequestDTO taskReplyRequestDto) {
		UserEntity userEntity = userService.getUserDetails(authorization);
		TaskActivityEntity taskActivityEntity = taskActivityService.updateTaskReply(userEntity, taskId, replyId,
				taskReplyRequestDto);
		return new ResponseEntity<TaskActivityEntity>(taskActivityEntity, HttpStatus.OK);
	}

}
