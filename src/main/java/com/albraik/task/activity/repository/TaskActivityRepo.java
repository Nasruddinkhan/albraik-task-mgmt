package com.albraik.task.activity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.albraik.task.activity.model.TaskActivityEntity;
import com.albraik.task.activity.model.TaskType;

@Repository
public interface TaskActivityRepo extends JpaRepository<TaskActivityEntity, Long> {

	@Query("SELECT t " + "FROM TaskActivityEntity t " + "WHERE (t.assigneeId = :userId " + "OR t.assignerId = :userId) "
			+ "AND t.type = :type AND t.isDeleted = false ORDER BY t.updatedTime DESC")
	List<TaskActivityEntity> findByAssigneeIdOrAssignerId(@Param("userId") Integer userId,
			@Param("type") TaskType type);

	List<TaskActivityEntity> findByProjectIdAndTypeAndIsDeletedFalseOrderByUpdatedTimeDesc(Integer projectId,
			TaskType type);

	@Query("SELECT t " + "FROM TaskActivityEntity t " + "WHERE t.type = 'TASK' "
			+ "AND (t.status = 'COMPLETED' OR t.status = 'LATE_COMPLETED') " + "AND t.isDeleted = false")
	List<TaskActivityEntity> getUpdatableTaskList();

	@Query("SELECT t " + "FROM TaskActivityEntity t " + "WHERE t.assigneeId = :userId "
			+ "AND t.type = 'TASK' AND t.status != 'FINISHED' AND t.isDeleted = false ORDER BY t.dueDate")
	List<TaskActivityEntity> getTaskAssignedToMe(@Param("userId") Integer userId);

	@Query("SELECT t " + "FROM TaskActivityEntity t " + "WHERE t.assignerId = :userId "
			+ "AND t.type = 'TASK' AND t.isDeleted = false ORDER BY t.updatedTime")
	List<TaskActivityEntity> getTaskAssignedByMe(@Param("userId") Integer userId);

}
