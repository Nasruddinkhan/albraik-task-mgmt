package com.albraik.task.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albraik.task.project.model.ProjectTypeEntity;
import com.albraik.task.project.service.ProjectTypeService;

@RestController
@RequestMapping("/api/project/type")
public class ProjectTypeController {

	@Autowired
	private ProjectTypeService projectTypeService;


	@GetMapping
	public ResponseEntity<List<ProjectTypeEntity>> getAllProjectTypes() {
		List<ProjectTypeEntity> projectTypes = projectTypeService.getAllProjectType();
		if (projectTypes.isEmpty())
			return new ResponseEntity<>(projectTypes, HttpStatus.NO_CONTENT);
		return ResponseEntity.ok(projectTypes);
	}

}
