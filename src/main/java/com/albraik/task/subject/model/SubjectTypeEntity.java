package com.albraik.task.subject.model;

import java.io.IOException;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "subject_type")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubjectTypeEntity {

	@Id
	@JsonProperty("id")
	private String id;

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("english_name")
	private String englishName;

	@JsonProperty("is_active")
	private Boolean isActive;
	
	@JsonProperty("order")
	private Integer order;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty("english_name")
	public String getEnglishName() {
		return englishName;
	}

	@JsonProperty("english_name")
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}


	@JsonProperty("is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	@JsonProperty("is_active")
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Override
	public String toString() {
		ObjectMapper Obj = new ObjectMapper();
		try {
			// return JSON String
			return Obj.writeValueAsString(this);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return this.getClass().getName();
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.order);
        return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if (getClass() != obj.getClass()) {
            return false;
        }
		final SubjectTypeEntity subjectTypeObj = (SubjectTypeEntity) obj;
		return subjectTypeObj.id == this.id;
	}

}
