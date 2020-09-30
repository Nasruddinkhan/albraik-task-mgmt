package com.albraik.task.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albraik.task.project.model.ProjectTypeEntity;
import com.albraik.task.project.repository.ProjectTypeRepo;

@Service
@Transactional
public class ProjectTypeServiceImpl implements ProjectTypeService {

	@Autowired
	private ProjectTypeRepo projectTypeRepo;

	
	@Override
	public List<ProjectTypeEntity> getAllProjectType() {
		List<ProjectTypeEntity> projectTypes = projectTypeRepo.findByIsActiveTrueOrderByOrderAsc();
		return projectTypes;
	}


}
