package com.albraik.task.subject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.albraik.task.subject.model.SubjectTypeEntity;

@Repository
public interface SubjectTypeRepo extends JpaRepository<SubjectTypeEntity, String>{
	
	List<SubjectTypeEntity> findByIsActiveTrueOrderByOrderAsc();

}
