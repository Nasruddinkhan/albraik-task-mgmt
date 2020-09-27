package com.albraik.task.subject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albraik.task.subject.model.SubjectTypeEntity;
import com.albraik.task.subject.service.SubjectTypeService;

@RestController
@RequestMapping("/api/subject/type")
public class SubjectTypeController {

	@Autowired
	private SubjectTypeService subjectTypeService;


	@GetMapping
	public ResponseEntity<List<SubjectTypeEntity>> getAllSubjectTypes() {
		List<SubjectTypeEntity> subjectTypes = subjectTypeService.getAllSubjectType();
		if (subjectTypes.isEmpty())
			return new ResponseEntity<>(subjectTypes, HttpStatus.NO_CONTENT);
		return ResponseEntity.ok(subjectTypes);
	}

}
