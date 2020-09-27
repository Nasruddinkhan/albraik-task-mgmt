package com.albraik.task.subject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albraik.task.subject.model.SubjectTypeEntity;
import com.albraik.task.subject.repository.SubjectTypeRepo;

@Service
@Transactional
public class SubjectTypeServiceImpl implements SubjectTypeService {

	@Autowired
	private SubjectTypeRepo subjectTypeRepo;

	
	@Override
	public List<SubjectTypeEntity> getAllSubjectType() {
		List<SubjectTypeEntity> subjectTypes = subjectTypeRepo.findByIsActiveTrueOrderByOrderAsc();
		return subjectTypes;
	}


}
