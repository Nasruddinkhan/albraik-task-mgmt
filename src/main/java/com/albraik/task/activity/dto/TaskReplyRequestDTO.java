package com.albraik.task.activity.dto;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.albraik.task.activity.model.TaskPriority;
import com.albraik.task.activity.model.TaskStatus;
import com.albraik.task.activity.model.TaskType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskReplyRequestDTO {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("description")
	private String description;

	@JsonProperty("task_id")
	private Long taskId;

	@JsonProperty("is_hidden")
	private Boolean isHidden;

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

	public Boolean getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
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
		hash = 13 * hash + Objects.hashCode(this.taskId);
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
		final TaskReplyRequestDTO taskReplyRequestDto = (TaskReplyRequestDTO) obj;
		return taskReplyRequestDto.id == this.id;
	}

}
