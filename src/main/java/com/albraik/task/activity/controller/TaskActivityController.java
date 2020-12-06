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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albraik.task.activity.dto.TaskActivityDTO;
import com.albraik.task.activity.dto.TaskActivityRequestDTO;
import com.albraik.task.activity.dto.TaskReplyRequestDTO;
import com.albraik.task.activity.model.TaskActivityEntity;
import com.albraik.task.activity.service.TaskActivityService;
import com.albraik.task.user.model.UserEntity;
import com.albraik.task.user.service.UserService;

@RestController
@RequestMapping("/api")
public class TaskActivityController {

	@Autowired
	private TaskActivityService taskActivityService;

	@Autowired
	private UserService userService;

	@GetMapping("/project/task")
	public ResponseEntity<List<TaskActivityDTO>> getMyTaskActivity(
			@RequestHeader("authorization") String authorization) {
		UserEntity userEntity = userService.getUserDetails(authorization);
		List<TaskActivityDTO> taskActivityList = taskActivityService.getMyTaskActivity(userEntity);
		if (taskActivityList.isEmpty())
			return new ResponseEntity<>(taskActivityList, HttpStatus.NO_CONTENT);
		return ResponseEntity.ok(taskActivityList);
	}

	@PostMapping("/project/task")
	public ResponseEntity<TaskActivityDTO> createTask(@RequestHeader("authorization") String authorization,
			@RequestBody TaskActivityRequestDTO taskActivityRequestDTO) {
		UserEntity userEntity = userService.getUserDetails(authorization);
		TaskActivityDTO taskActivityEntity = taskActivityService.createTaskActivity(userEntity, taskActivityRequestDTO);
		return new ResponseEntity<TaskActivityDTO>(taskActivityEntity, HttpStatus.CREATED);
	}

	@PostMapping("/project/task/{taskId}/reply")
	public ResponseEntity<TaskActivityDTO> createTaskReply(@RequestHeader("authorization") String authorization,
			@PathVariable("taskId") Long taskId, @RequestBody TaskReplyRequestDTO taskReplyRequestDto) {
		UserEntity userEntity = userService.getUserDetails(authorization);
		TaskActivityDTO taskActivityEntity = taskActivityService.createTaskReply(userEntity, taskId,
				taskReplyRequestDto);
		return new ResponseEntity<TaskActivityDTO>(taskActivityEntity, HttpStatus.CREATED);
	}

	@PutMapping("/project/task/{taskId}/reply/{replyId}")
	public ResponseEntity<TaskActivityDTO> updateTaskReply(@RequestHeader("authorization") String authorization,
			@PathVariable("taskId") Long taskId, @PathVariable("replyId") Long replyId,
			@RequestBody TaskReplyRequestDTO taskReplyRequestDto) {
		UserEntity userEntity = userService.getUserDetails(authorization);
		TaskActivityDTO taskActivityEntity = taskActivityService.updateTaskReply(userEntity, taskId, replyId,
				taskReplyRequestDto);
		return new ResponseEntity<TaskActivityDTO>(taskActivityEntity, HttpStatus.OK);
	}

	@GetMapping("/project/{projectId}/task")
	public ResponseEntity<List<TaskActivityDTO>> getProjectTaskActivity(
			@RequestHeader("authorization") String authorization, @PathVariable("projectId") Integer projectId) {
		UserEntity userEntity = userService.getUserDetails(authorization);
		List<TaskActivityDTO> taskActivityList = taskActivityService.getProjectTaskActivity(userEntity, projectId);
		if (taskActivityList.isEmpty())
			return new ResponseEntity<>(taskActivityList, HttpStatus.NO_CONTENT);
		return ResponseEntity.ok(taskActivityList);
	}

}
