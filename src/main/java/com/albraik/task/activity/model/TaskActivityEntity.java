package com.albraik.task.activity.model;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "task_activity")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskActivityEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	private Long id;

	@JsonProperty("description")
	private String description;

	@JsonProperty("project_id")
	private Integer projectId;

	@JsonProperty("project_name")
	private String projectName;

	@JsonProperty("assignee_id")
	private Integer assigneeId;

	@JsonProperty("assignee_name")
	private String assigneeName;

	@JsonProperty("assigner_id")
	private Integer assignerId;

	@JsonProperty("assigner_name")
	private String assignerName;

	@JsonProperty("due_date")
	private Long dueDate;

	@JsonProperty("priority")
	private TaskPriority priority;

	@JsonProperty("status")
	private TaskStatus status;

	@JsonProperty("type")
	private TaskType type;

	@OneToOne(optional = true)
	@JoinColumn(name = "task_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonProperty("task_activity")
	private TaskActivityEntity taskActivity;

	@JsonProperty("created_time")
	private Long createdTime;

	@JsonProperty("updated_time")
	private Long updatedTime;

	@JsonProperty("is_hidden")
	private Boolean isHidden;

	@JsonProperty("is_deleted")
	private Boolean isDeleted;

	@OneToOne(mappedBy = "taskActivity")
	private TaskActivityEntity taskReplyActivity;

	@OneToMany(mappedBy = "taskActivity")
	private List<TaskAttachmentEntity> attachmentList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Integer assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public Integer getAssignerId() {
		return assignerId;
	}

	public void setAssignerId(Integer assignerId) {
		this.assignerId = assignerId;
	}

	public String getAssignerName() {
		return assignerName;
	}

	public void setAssignerName(String assignerName) {
		this.assignerName = assignerName;
	}

	public Long getDueDate() {
		return dueDate;
	}

	public void setDueDate(Long dueDate) {
		this.dueDate = dueDate;
	}

	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public TaskType getType() {
		return type;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

	public TaskActivityEntity getTaskActivity() {
		return taskActivity;
	}

	public void setTaskActivity(TaskActivityEntity taskActivity) {
		this.taskActivity = taskActivity;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public Long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Boolean getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public TaskActivityEntity getTaskReplyActivity() {
		return taskReplyActivity;
	}

	public void setTaskReplyActivity(TaskActivityEntity taskReplyActivity) {
		this.taskReplyActivity = taskReplyActivity;
	}

	public List<TaskAttachmentEntity> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<TaskAttachmentEntity> attachmentList) {
		this.attachmentList = attachmentList;
	}

	@Override
	public String toString() {
		ObjectMapper Obj = new ObjectMapper();
		try {
			// return JSON String
			return Obj.writeValueAsString(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.getClass().getName();
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 13 * hash + Objects.hashCode(this.id);
		hash = 13 * hash + Objects.hashCode(this.projectId);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TaskActivityEntity taskActivityEntity = (TaskActivityEntity) obj;
		return taskActivityEntity.id == this.id;
	}

}
