package com.albraik.task.activity.dto;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.albraik.task.activity.model.TaskAttachmentEntity;
import com.albraik.task.activity.model.TaskPriority;
import com.albraik.task.activity.model.TaskStatus;
import com.albraik.task.activity.model.TaskType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskActivityDTO {

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

	@JsonProperty("task_id")
	private Long taskId;

	@JsonProperty("created_time")
	private Long createdTime;

	@JsonProperty("updated_time")
	private Long updatedTime;

	@JsonProperty("is_hidden")
	private Boolean isHidden;

	@JsonProperty("task_reply")
	private TaskActivityDTO taskReply;

	@JsonProperty("attachment_list")
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
		final TaskActivityDTO taskActivityDTO = (TaskActivityDTO) obj;
		return taskActivityDTO.id == this.id;
	}

}
